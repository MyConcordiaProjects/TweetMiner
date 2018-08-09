package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import services.TweetService;
import twitter4j.TwitterResponse;

/**
 * This actor class recieves the request from controller and sends the response
 * containing the word count
 * 
 * @author Rakshitha
 *
 */
public class WordStatisticsActor extends AbstractActor {

	/**
	 * This method creates the actor reference of WordStatisticsActor
	 * @return
	 */
	public static Props getProps() {
		return Props.create(WordStatisticsActor.class);
	}

	/**
	 * This class is used to set the search keyword value
	 * @author Rakshitha
	 *
	 */
	public static class WordStatsProtocol {
		public final String keyword;

		public WordStatsProtocol(String keyword) {
			this.keyword = keyword;
		}
	}

	/*
	 * (non-Javadoc)
	 * Receives the request and returns the response
	 * @see akka.actor.AbstractActor#createReceive()
	 */
	@Override
	public Receive createReceive() {

		return receiveBuilder().match(WordStatsProtocol.class, variable -> {
			sender().tell(TweetService.getWordCount(variable.keyword), self());
		}).build();
	}

}
