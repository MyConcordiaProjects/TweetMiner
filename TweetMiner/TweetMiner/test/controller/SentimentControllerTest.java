package controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.fasterxml.jackson.databind.node.ArrayNode;

import controllers.SentimentController;
import play.mvc.Result;
import twitter4j.TwitterException;

public class SentimentControllerTest {
	
	 /**
     *@author Nikhil Vijayan
     * @throws TwitterException, ExecutionException, TwitterException
     */
	
	@Test
	public void getSentimentTest() throws InterruptedException, ExecutionException, TwitterException {
		SentimentController sentimentController = new SentimentController();
		CompletionStage<Result> sentimentResult = sentimentController.getSentiment("happy");
		assertNotNull(sentimentResult);
	}
}

