package controller;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import controllers.SentimentController;
import controllers.WordStatisticsController;
import play.mvc.Result;
import twitter4j.TwitterException;

public class WordStatisticsControllerTest {

	 /**
     *@author Rakhshitha Ramachandra
     * @throws TwitterException, ExecutionException, TwitterException
     */
	
	@Test
	public void getWordCountTest() throws InterruptedException, ExecutionException, TwitterException {
		WordStatisticsController wordController = new WordStatisticsController();
		CompletionStage<Result> wordResult = wordController.wordcount("happy");
		assertNotNull(wordResult);
	}
}
