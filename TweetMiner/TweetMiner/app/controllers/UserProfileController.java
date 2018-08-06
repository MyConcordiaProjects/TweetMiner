package controllers;
import play.mvc.Controller;
import play.mvc.Result;
import twitter4j.TwitterException;
import services.TweetService;
import views.html.*;
import java.util.concurrent.CompletionStage;

/**
 * UserProfileController class controls model and view for user information
 * @version 1.0
 * @author Gurpreet Lotey
 */
public class UserProfileController extends Controller {
	 /**
     * @author Gurpreet Lotey
     * @param  username  
     * @return  result for user information
     * @throws TwitterException 
     */

    public CompletionStage<Result> userProfile(String username) throws TwitterException {
        return TweetService.getUserProfileInformation(username).thenApplyAsync(info -> ok(profile.render(info)));
    }
}
