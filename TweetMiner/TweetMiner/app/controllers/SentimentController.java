package controllers;

import java.util.concurrent.CompletionStage;

import play.mvc.Controller;
import play.mvc.Result;
import services.TweetService;
import twitter4j.Twitter;
import twitter4j.TwitterException;


/**
* This is a controller that renders the emotion of the tweets
* @author Nikhil Vijayan
*
*/
public class SentimentController extends Controller {
    
    /**
     * @author Nikhil Vijayan
     * @param keyword search keyword
     * @return JSON containing the emoticon
     * @throws TwitterException
     */
    public CompletionStage<Result> getSentiment(String keyword) throws TwitterException
    {
        return TweetService.getSentiment(keyword).thenApplyAsync(emoji -> ok(emoji));
    }

}