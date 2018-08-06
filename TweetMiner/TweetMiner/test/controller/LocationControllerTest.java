package controller;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import controllers.SentimentController;
import controllers.LocationController;
import play.mvc.Result;
import twitter4j.TwitterException;

/**
* This is a controller that tests the LocationController and tests rendering of tweets based on location.
* @author Sohila Kaur
*
*/
public class LocationControllerTest {
	
	 
    /**
     *@author Sohila Kaur
     * @return Not null value
     * @throws TwitterException, ExecutionException, TwitterException
     */

	@Test
	public void getLocation() throws InterruptedException, ExecutionException, TwitterException {
		LocationController location = new LocationController();
		CompletionStage<Result> locresult = location.getTweetsByLocation("30.73527778", "76.79111111");
		assertNotNull(locresult);
	}
}
