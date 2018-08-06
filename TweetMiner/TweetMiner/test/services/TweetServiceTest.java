package services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.fasterxml.jackson.databind.node.ArrayNode;

import models.userProfile;
import twitter4j.TwitterException;

public class TweetServiceTest {
	
	TweetService tweetService;

	/**
    * Test for getUserProfileInformation
    * @author Gurpreet Lotey
    */
    @Test
       public void testgetUserProfileInformation() throws TwitterException, ExecutionException, InterruptedException {
           CompletionStage<userProfile> result = TweetService.getUserProfileInformation("Nikhil");
           assertTrue(result.toCompletableFuture().get().getUserProfileName() != null);
       }
    
    /**
     * Test for getSentiment
     * @author Nikhil Vijayan
     */
	@Test
	public void sentimentTest() throws TwitterException, InterruptedException, ExecutionException {
		
		CompletionStage<ArrayNode> testEmoji = TweetService.getSentiment("Happy");
		assertTrue(testEmoji.toCompletableFuture().get().size() > 0);
		assertNotNull(testEmoji.toCompletableFuture().get());
	}
	
	
	/**
	    * Test for getWordCount
	    * @author Rakhshitha Ramachandra
	    */
	@Test
	public void getWordCountTest() throws TwitterException, InterruptedException, ExecutionException {
		
		CompletionStage<ArrayNode> wordCount = TweetService.getWordCount("Java");
		assertTrue(wordCount.toCompletableFuture().get().size() > 0);
		assertNotNull(wordCount.toCompletableFuture().get());
	}
	
	
	/**
	    * Test for getTweetsByLocation
	    * @author Sohila Kaur
	    */
	@Test
	public void getLocation() throws TwitterException, InterruptedException, ExecutionException {
		
		CompletionStage<List<String>> location = TweetService.getTweetsByLocation(30.73527778, 76.79111111);
		assertTrue(location.toCompletableFuture().get().size() > 0);
		assertNotNull(location.toCompletableFuture().get());
	}
	
	
}
