package controllers;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.inject.Singleton;

import actors.SentimentActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;
import services.TweetService;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import static akka.pattern.Patterns.ask;


/**
* This is a controller that renders the emotion of the tweets
* @author Nikhil Vijayan
*
*/

@Singleton
public class SentimentController extends Controller {
    
	final ActorRef sentimentActor;
	@Inject 
	public SentimentController(ActorSystem system) {
		sentimentActor = system.actorOf(SentimentActor.getProps());
	}
    /**
     * @author Nikhil Vijayan
     * @param keyword search keyword
     * @return JSON containing the emoticon
     * @throws TwitterException
     */
    public CompletionStage<Result> getSentiment(String keyword) throws TwitterException
    {
       // return TweetService.getSentiment(keyword).thenApplyAsync(emoji -> ok(emoji));
    	return FutureConverters.toJava(
    			ask(sentimentActor, new SentimentActor.SentimentProtocol(keyword), 5000))
    			.thenApply(response -> ok((String)response));
    			}

    	
    

}