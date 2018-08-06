package controller;

import static org.junit.Assert.assertTrue;
import controllers.UserProfileController;
import org.junit.Test;
import play.mvc.Result;
import twitter4j.TwitterException;
import java.util.concurrent.*;

public class userProfileTest {
     
    /**
     * @author Gurpreet Lotey
     */
    @Test
        public void testProfile() throws TwitterException, ExecutionException, InterruptedException {
            CompletionStage<Result> result = new UserProfileController().userProfile("Nikhil");
            CompletableFuture<Result> r = result.toCompletableFuture();
            assertTrue(r.get().toString().length() > 0);
      }
}
