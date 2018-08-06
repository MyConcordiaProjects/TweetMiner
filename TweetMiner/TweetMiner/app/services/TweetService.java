package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.userProfile;
import play.libs.Json;
import twitter4j.*;

import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Nikhil Vijayan
 *
 */
public class TweetService {

	/**
	 * This method returns a twitter instance after authentication
	 * 
	 * @author Nikhil Vijayan
	 * @return Twitter instance
	 */
	public static Twitter getAuthorization() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("iuh1dBIa8bXvOjBSRLpIF7e40")
				.setOAuthConsumerSecret("GAupJH5iFkWycp9r72dN44Tvd0pO14Tkoi4WSsoMt8dSN4GB3E")
				.setOAuthAccessToken("972273228046569473-4GTgsikGCKHXse3RxzTwqSUk23cEhe8")
				.setOAuthAccessTokenSecret("xctHGp1WG295EARuD7uWKuWJuAI9hgPxmDI0IxmK0ZtAI");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;

	}

	/**
	 * This method queries the tweets, limits them to 10 and returns a future
	 * containing a JSON object of tweets
	 * 
	 * @author Nikhil Vijayan
	 * @param keyword
	 * @return future containing the tweets
	 * @throws TwitterException
	 */
	public static CompletionStage<ArrayNode> getTweets(String keyword) throws TwitterException {
		CompletableFuture<ArrayNode> tweetFuture = new CompletableFuture<>();
		Twitter twitterfactory = getAuthorization();
		Query query = new Query(keyword);
		// Limiting the tweets to 10
		query.setCount(10);
		QueryResult queryResult = twitterfactory.search(query);
		List<Status> status = queryResult.getTweets();

		// adds each tweet to a JSON array object
		ArrayNode JSONstatus = Json.newArray();
		status.forEach((tweet) -> {
			try {
			ObjectNode JSONNode = Json.newObject();
			JSONNode.put("tweet", tweet.getText());
			JSONNode.put("userName", tweet.getUser().getName());
			JSONNode.put("displayName", tweet.getUser().getScreenName());
			HashtagEntity[] hashTags = tweet.getHashtagEntities();
            for (HashtagEntity hashtag : hashTags) {
                 JSONNode.put("hashTag", hashtag.getText());
                }
			// getting the geolocation of the tweets
			if(tweet.getGeoLocation() != null) {
                JSONNode.put("place",tweet.getPlace().getName().toString());
                JSONNode.put("latitude", tweet.getGeoLocation().getLatitude());
                JSONNode.put("longitude", tweet.getGeoLocation().getLongitude());
            }
            JSONstatus.add(JSONNode);
     } catch (Exception e) {
         System.out.println(e);
     }
		});
		tweetFuture.complete(JSONstatus);
		return tweetFuture;
	}

	/**
	 * @author Rakshitha
	 * @param keyword
	 * @return future containing distinct word count in descending order
	 * @throws TwitterException
	 */
	public static CompletionStage<ArrayNode> getWordCount(String keyword) throws TwitterException {
		CompletableFuture<ArrayNode> tweetFuture = new CompletableFuture<>();
		Twitter twitterfactory = getAuthorization();
		Query query = new Query(keyword);
		// Limiting the tweets to 100
		query.setCount(100);
		QueryResult queryResult = twitterfactory.search(query);
		List<Status> tweetList = queryResult.getTweets();
		List<String> allWords = tweetList.stream().map(Status::getText).map(word -> word.split(" "))
				.flatMap(Arrays::stream).collect(Collectors.toList());

		Map<String, Long> unsortMap = allWords.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Map<String, Long> uniqueWords = unsortMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		ArrayNode JSONWords = Json.newArray();
		uniqueWords.forEach((word, count) -> {
			ObjectNode JSONNode = Json.newObject();
			JSONNode.put("word", word);
			JSONNode.put("count", count);
			JSONWords.add(JSONNode);
		});

		tweetFuture.complete(JSONWords);
		return tweetFuture;

	}

	/**
	 * @author Nikhil Vijayan
	 * @param keyword  search keyword
	 * @return future containing the emoticon
	 * @throws TwitterException
	 */
	public static CompletableFuture<ArrayNode> getSentiment(String keyword) throws TwitterException {
		CompletableFuture<ArrayNode> tweetEmojiFuture = new CompletableFuture<>();
		Twitter twitterfactory = getAuthorization();
		Query query = new Query(keyword);
		// Limiting the tweets to 100
		query.setCount(100);
		QueryResult queryResult = twitterfactory.search(query);
		List<Status> tweetList = queryResult.getTweets();
		List<String> tweetStringList = new ArrayList<>();
		tweetList.forEach((tweet) -> tweetStringList.add(tweet.getText()));
		String happyEmoticon = ":-)";
		long happyCount = tweetStringList.stream().filter(a -> a.contains(happyEmoticon)).count();
		String sadEmoticon = ":-(";
		long sadCount = tweetStringList.stream().filter(a -> a.contains(sadEmoticon)).count();
		String neutralEmoticon = ":-|";
		long neutralCount = tweetStringList.stream().filter(a -> a.contains(neutralEmoticon)).count();
		String emotion = happyCount > sadCount ? ((happyCount > neutralCount || happyCount > 70) ? ":-)" : ":-|")
				: (sadCount > neutralCount || sadCount > 70) ? ":-(" : ":-|";
		ArrayNode JSONEmotion = Json.newArray();
		ObjectNode JSONNode = Json.newObject();
		JSONNode.put("emoji", emotion);
		JSONEmotion.add(JSONNode);
		tweetEmojiFuture.complete(JSONEmotion);
		return tweetEmojiFuture;
	}
	
	/**
    * Gets user information
    * @author Gurpreet Lotey
    * @param username of type string
    * @return future
    * @throws TwitterException
    */
   public static CompletionStage<userProfile> getUserProfileInformation(String username) throws TwitterException {
       CompletableFuture<userProfile> future = new CompletableFuture<>();
       Twitter twitter = getAuthorization();
       User user = twitter.showUser(username);        
       userProfile userProfile = new userProfile();
       userProfile.setName(user.getName());
       userProfile.setUserProfileName(user.getScreenName());
       userProfile.setUrl(user.getURL());
       userProfile.setDescription(user.getDescription());
       userProfile.setLocation(user.getLocation());
       Paging p = new Paging();
       int limit=10;
       p.setCount(limit);
       List<String> list = new ArrayList<String> ();
       List<Status> timeline = twitter.getUserTimeline(user.getScreenName(),p);    
       timeline.forEach((info) -> {list.add(info.getText()); });
       userProfile.setTimeline(list);
       future.complete(userProfile);
       return future;
   }

   /**
   * This method queries the tweets by location, limits them to 10 and returns a future containing a JSON object of tweets
   * @author Sohila Kaur
   * @param  latitude
   * @param  longitude
   * @return future containing the tweets from the same location as a tweet from the search result
   * @throws TwitterException
   */
   
   public static CompletionStage<List<String>> getTweetsByLocation(double latitude, double longitude) throws TwitterException {
           CompletableFuture<List<String>> future = new CompletableFuture<>();
           double radius = 5;
           Twitter twitter = getAuthorization();
           Query query = new Query().geoCode(new GeoLocation(latitude, longitude),radius, "mi");
           query.setCount(10);
           QueryResult result = twitter.search(query);
           List<Status> tweets = result.getTweets();
           List<String> tweetsByLocation = new ArrayList<>();
           tweets.forEach((tweet) -> {
               tweetsByLocation.add(tweet.getText());
           });
           future.complete(tweetsByLocation);
           return future;
       }
   
   /**
   * @author Nayana Raj
   * @param keyword
   * @return
   * @throws TwitterException
   */
  public static CompletionStage<ArrayNode> getHashtags(String keyword) throws TwitterException {

      CompletableFuture<ArrayNode> tweetFuture = new CompletableFuture<>();
      Twitter twitterfactory = getAuthorization();
      Query query = new Query(keyword);
      //Limiting the tweets to 10
      query.setCount(10);
      QueryResult queryResult = twitterfactory.search(query);
      List<Status> status = queryResult.getTweets();
      
      ArrayNode JSONstatus = Json.newArray();
     status.forEach((tweet) -> {
         ObjectNode JSONNode = Json.newObject();
         JSONNode.put("text", tweet.getText());
         JSONNode.put("name", tweet.getUser().getName());
         JSONNode.put("screenName", tweet.getUser().getScreenName());
       
        JSONstatus.add(JSONNode);
     });        
 tweetFuture.complete(JSONstatus);
 return tweetFuture;
      
  }
}
