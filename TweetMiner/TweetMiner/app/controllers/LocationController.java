package controllers;

import java.util.concurrent.CompletionStage;
import twitter4j.*;

import play.mvc.Controller;
import play.mvc.Result;
import twitter4j.TwitterException;
import services.TweetService;
import java.util.concurrent.CompletionStage;
import views.html.*;
/**
 * This is a controller class that renders the tweets based on location retrieved from the service to the view.
 * @author Sohila Kaur
 *
 */
public class LocationController extends Controller {
	/**
	 * This method gets the tweets based on location from the service
	 * whenever there is a call to <code>GET</code> request with a path of <code>/location/:latitude/:longitude</code>
	 * @param latitude 
	 * @param longitude
	 * @return JSON containing the tweets from the same location as a tweet from the search result
	 * @throws TwitterException
	 */
	
	public CompletionStage<Result> getTweetsByLocation(String latitude, String longitude) throws TwitterException {
        return TweetService.getTweetsByLocation(Double.parseDouble(latitude), Double.parseDouble(longitude)).thenApplyAsync(tweets -> ok(location.render(tweets)));
    }
}