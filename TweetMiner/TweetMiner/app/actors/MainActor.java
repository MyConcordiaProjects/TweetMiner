package actors;

import services.TweetService;
import akka.actor.AbstractActor;
import akka.actor.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import twitter4j.*;
import twitter4j.Status;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActor extends AbstractActor {

	private final ActorRef actorRef;
	private final Twitter twitter = TweetService.getAuthorization();

	public static Props props(ActorRef actorRef) {
		return Props.create(MainActor.class, actorRef);
	}

	public MainActor(ActorRef actorRef) {
		this.actorRef = actorRef;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, message -> {
			Runnable task = new Runnable() {
				@Override
				public void run() {
					Query query = new Query(message);
					query.setCount(10);
					QueryResult tweetSet = null;
					try {
						tweetSet = twitter.search(query);
					} catch (TwitterException twitterException) {
						twitterException.printStackTrace();
					}
					List<Status> tweets = tweetSet.getTweets();
					ArrayNode tweetJSON = Json.newArray();

					tweetJSON = setTweetJSON(tweets, message);
					actorRef.tell(tweetJSON.toString(), self());
				}
			};
			ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
		}).build();
	}

	public static ArrayNode setTweetJSON(List<Status> tweets, String message) {
		ArrayNode tweetJSON = Json.newArray();
		ObjectNode JSONNode = Json.newObject();
		tweets.forEach((tweet) -> {

			JSONNode.put("tweet", tweet.getText());
			JSONNode.put("userName", tweet.getUser().getName());
			JSONNode.put("displayName", tweet.getUser().getScreenName());
			JSONNode.put("message", message);

			if (tweet.getGeoLocation() != null) {
				JSONNode.put("place", tweet.getPlace().getName().toString());
				JSONNode.put("latitude", tweet.getGeoLocation().getLatitude());
				JSONNode.put("longitude", tweet.getGeoLocation().getLongitude());
			}

			tweetJSON.add(JSONNode);
		});
		return tweetJSON;

	}
}
