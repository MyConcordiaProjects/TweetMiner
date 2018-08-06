package controller;

import play.twirl.api.Content;
import views.html.index;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import controllers.HomeController;
import play.mvc.Result;

public class HomeControllerTest {

	HomeController homeController;
	
	@Test
	public void indexTest() {
		Content html = index.render("Welcome to TweetMiner.");
		assertThat(html.body(), containsString("Welcome to TweetMiner."));
	}
	
}
