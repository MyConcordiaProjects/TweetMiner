package controllers;

import java.util.concurrent.CompletionStage;

import services.TweetService;
import twitter4j.TwitterException;
import play.mvc.Controller;
import play.mvc.Result;

public class HashtagController extends Controller {
	
	public CompletionStage<Result> getHashtags(String keyword) throws TwitterException {
		return TweetService.getHashtags(keyword).thenApplyAsync(status -> ok(status));
	}
					

}
