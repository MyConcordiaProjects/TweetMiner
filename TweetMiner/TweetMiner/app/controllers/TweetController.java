package controllers;

import java.util.concurrent.CompletionStage;

import play.mvc.Controller;
import play.mvc.Result;
import services.TweetService;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * This is a controller class that renders the tweets retrieved from the service to the view.
 * @author Nikhil Vijayan
 *
 */
public class TweetController extends Controller {
	
	/**
	 * This method gets the tweets from the service
	 * whenever there is a call to GET /tweetminer/:keyword
	 * @param keyword search word
	 * @return JSON containing the tweets
	 * @throws TwitterException
	 */
	public CompletionStage<Result> getTweets(String keyword) throws TwitterException
	{
		return TweetService.getTweets(keyword).thenApplyAsync(status -> ok(status));
	}
	

}
