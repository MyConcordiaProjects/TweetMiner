package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import services.TweetService;

/**
 * This actor recieves the emoticon from the Service method and passes it on to the controller.
 * @author Nikhil Vijayan
 *
 */
public class SentimentActor extends AbstractActor {

	/**
	 * Gets a Props object that describes how the Actor object can be created.
	 * @author Nikhil Vijayan
	 * @return Props object
	 */
	public static Props getProps() {
		return Props.create(SentimentActor.class);
		}
	
	public static class SentimentProtocol {
		public final String keyword;
		public SentimentProtocol(String keyword) {
		this.keyword = keyword;
		}
		}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(SentimentProtocol.class, hello -> {
				sender().tell(TweetService.getSentiment(hello.keyword), self());
				})
				.build();

	}

}
