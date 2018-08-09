package controllers;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.inject.Singleton;

import actors.WordStatisticsActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;
import play.mvc.Controller;
import services.TweetService;
import static akka.pattern.Patterns.ask;
import views.html.*;
import play.libs.Json;

/**
 * This is a controller class that renders a word-level statistics for the
 * tweets and count unique words in descending order.
 * 
 * @author Rakshitha
 *
 */
@Singleton
public class WordStatisticsController extends Controller {

	/**
	 * @param keyword
	 * @return future containing JSON node having count of distinct words
	 * @throws TwitterException
	 *//*
		 * public CompletionStage<Result> wordcount(String keyword) throws
		 * TwitterException{
		 * 
		 * return TweetService.getWordCount(keyword).thenApplyAsync(uniqueWords ->
		 * ok(uniqueWords));
		 * 
		 * }
		 */

	/**
	 * reference to the Actor WordStatisticsActor
	 */
	private final ActorRef wordStatisticsActor;

	/**
	 * @param system
	 */
	@Inject
	public WordStatisticsController(ActorSystem system) {
		wordStatisticsActor = system.actorOf(WordStatisticsActor.getProps());
	}

	/**
	 * @param keyword
	 * @return String containing the count of distinct words in descending order
	 */
	public CompletionStage<Result> wordcount(String keyword) {
		return FutureConverters
				.toJava(ask(wordStatisticsActor, new WordStatisticsActor.WordStatsProtocol(keyword), 5000))
				.thenApply(response -> ok((String) response));
	}

}
