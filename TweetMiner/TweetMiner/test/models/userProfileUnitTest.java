package models;

import models.userProfile;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

/**
 * @see userProfile
 * @see userProfileUnitTest
 * @author Gurpreet Lotey
 *
 */

public class userProfileUnitTest {
    userProfile user;

    /**
     * Execute before each test method in this class is executed.
     * @author Gurpreet Lotey
     */
    @Before
    public void setup() {
        user = new userProfile();
        user.setName("Nikhil");
        user.setUserProfileName("@nikhil_vijayan4");
        user.setUrl("https://twitter.com/nikhil_vijayan4");
        user.setDescription("This is desc");
        user.setLocation("Canada");   
        List<String> timeline = new ArrayList<String>();
        timeline.add("Test Test");
        user.setTimeline(timeline);
    }

    /**
    * public void userProfileUnitTest annotate with @Test will be executed
    * as a Test case.
    * Checking the screenName 
    * @author Gurpreet Lotey
    */
    @Test
    public void testUserProfile() {
        assertTrue(user.getUserProfileName() == "@nikhil_vijayan4");
    }
}
