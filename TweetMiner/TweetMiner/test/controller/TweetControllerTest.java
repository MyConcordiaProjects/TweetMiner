package controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import controllers.TweetController;
import play.mvc.Result;
import twitter4j.TwitterException;

public class TweetControllerTest {

	/**
     *@author Rakhshitha Ramachandra
     * @throws TwitterException, ExecutionException, TwitterException
     */
	@Test
	public void getTweetsTest() throws InterruptedException, ExecutionException, TwitterException {
		TweetController tweetController = new TweetController();
		CompletionStage<Result> tweets = tweetController.getTweets("happy");
		assertNotNull(tweets);
		assertTrue(tweets.toCompletableFuture().get().toString().length() > 0);
	}
}
