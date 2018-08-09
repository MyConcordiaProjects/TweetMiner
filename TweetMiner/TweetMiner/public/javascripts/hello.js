;
(function(window) {
	// 'use strict';
	document.documentElement.className = 'js';

	var searchKeyword = document.getElementById("searchkeyword");
	var tweetlist = [];

	jQuery('#search-form')
			.submit(
					function(searchfunc) {
						searchfunc.preventDefault();
						if (searchKeyword.value != '') {
							var heading = "";
							heading += "<h2>Recent tweets related to <i>"
									+ searchKeyword.value + "</i></h2>"
							/*
							 * jQuery.get("http://localhost:9000/tweetminer/" +
							 * searchKeyword.value).done(function (allTweets) {
							 * tweetlist.concat(allTweets);
							 * allTweets.forEach(function (eachTweet) { tweets += "<p>=> ";
							 * if(eachTweet.place && eachTweet.latitude &&
							 * eachTweet.longitude) { tweets += "<a
							 * target='_blank'
							 * href='http://localhost:9000/userProfile/"+eachTweet.displayName+"'>
							 * ("+eachTweet.userName+")</a>" + "<a
							 * target='_blank'
							 * href='http://localhost:9000/location/"
							 * +eachTweet.latitude + "/"+eachTweet.longitude+"'>
							 * ("+eachTweet.place+")</a>" +"Tweet -
							 * "+eachTweet.tweet; } else { tweets += "<a
							 * target='_blank'
							 * href='http://localhost:9000/userProfile/"+eachTweet.displayName
							 * +"'>("+eachTweet.userName + ")</a>" + ":" +
							 * eachTweet.tweet + "<a target='_blank'
							 * href='http://localhost:9000/hashtag/" +
							 * eachTweet.hashTag + "'>#" +eachTweet.hashTag + "</a>"; }
							 * tweets += "</p>"; }); tweets += "<hr>";
							 */

							jQuery(".display-tweets").append(heading);

							// Open web socket connection
							var websocket = new WebSocket(
									'ws://localhost:9000/reactivePage');

							websocket.onopen = function(open) {
								websocket.send(searchKeyword.value);
							};

							websocket.onmessage = function(message) {
								var tweetsJSON = JSON.parse(message.data);
								var tweets = "";
								tweetsJSON
										.forEach(function(eachTweet) {

											tweets += "<p>=> ";
											if (eachTweet.place
													&& eachTweet.latitude
													&& eachTweet.longitude) {
												tweets += "<a target='_blank' href='http://localhost:9000/userProfile/"
														+ eachTweet.displayName
														+ "'> ("
														+ eachTweet.userName
														+ ")</a>"
														+ "<a target='_blank' href='http://localhost:9000/location/"
														+ eachTweet.latitude
														+ "/"
														+ eachTweet.longitude
														+ "'> ("
														+ eachTweet.place
														+ ")</a>"
														+ "Tweet - "
														+ eachTweet.tweet;
											} else {
												tweets += "<a target='_blank' href='http://localhost:9000/userProfile/"
														+ eachTweet.displayName
														+ "'>("
														+ eachTweet.userName
														+ ")</a>"
														+ ":"
														+ eachTweet.tweet
														+ "<a target='_blank' href='http://localhost:9000/hashtag/"
														+ eachTweet.hashTag
														+ "'>#"
														+ eachTweet.hashTag
														+ "</a>";
											}
											tweets += "</p>";
										});
								tweets += "<hr>";
								jQuery(".display-tweets").append(tweets);
							};

							websocket.onclose = function(msg) {
								// Logic for closed connection
								console.log('Websocket connection closed');
							};
							websocket.error = function(err) {
								// Write errors to console
								console.log(err);
							}
						}
						;
					});

	var wordList = [];
	// word statistics function
	jQuery('#wordcount-form').submit(
			function(wordCount) {

				wordCount.preventDefault();
				var word = "";

				if (searchKeyword.value != '') {
					jQuery.get(
							"http://localhost:9000/wordstatistics/"
									+ searchKeyword.value).done(
							function(words) {
								//window.alert(words);
								word += "<h2>Here's the Word Statistics:</h2>";
								word += "<p> ";
								word += words + "<br></p>";
								jQuery(".display-words").append(word);
								});
								
							}
				
			});

	jQuery('#emotion-form')
    .submit(
            function(emojifunc) {
                emojifunc.preventDefault();
                var emoji = "";
                if (searchKeyword.value != '') {
                    jQuery.get("http://localhost:9000/emotion/" + searchKeyword.value)
                            .done(
                                    function(displayEmotion) {
                                        //window.alert(displayEmotion)
                                        emoji += "<h3> Average tweet emotion is ";
                                        emoji += displayEmotion;
                                        emoji += "</h3>";
                                        document.getElementById("display_sentiment").innerHTML = emoji;
                                    });
                    
                }

            });
})(window);
