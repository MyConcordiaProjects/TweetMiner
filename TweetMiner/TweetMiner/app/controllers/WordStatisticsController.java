package controllers;

import java.util.concurrent.CompletionStage;

import play.mvc.Result;
import play.mvc.Controller;
import services.TweetService;
import twitter4j.TwitterException;
import views.html.*;

/**
 * This is a controller class that renders a word-level statistics for the tweets and count unique words in descending order.
 * @author Rakshitha Ramachandra
 *
 */
public class WordStatisticsController extends Controller {
	
	/**
	 * @param keyword
	 * @return future containing JSON node having count of distinct words
	 * @throws TwitterException
	 */
	public CompletionStage<Result> wordcount(String keyword) throws TwitterException{
		
		return TweetService.getWordCount(keyword).thenApplyAsync(uniqueWords -> ok(uniqueWords));
		
	}

}
