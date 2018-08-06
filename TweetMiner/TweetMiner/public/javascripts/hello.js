;(function(window) {
	'use strict';
	
	var searchKeyword = document.getElementById("searchkeyword");
	var tweetlist = [];
		
	jQuery('#search-form').submit(function (searchfunc) {
		searchfunc.preventDefault();
        if( searchKeyword.value != '' ){
        	var tweets = "";
        	tweets += "<h2>Top 10 Tweets:</h2>"
        	jQuery.get("http://localhost:9000/tweetminer/" + searchKeyword.value).done(function (allTweets) {
        		tweetlist.concat(allTweets);
        		allTweets.forEach(function (eachTweet) {
        			tweets += "<p>=> ";
        			if(eachTweet.place && eachTweet.latitude && eachTweet.longitude) {
                        tweets += "<a target='_blank' href='http://localhost:9000/userProfile/"+eachTweet.displayName+"'> ("+eachTweet.userName+")</a>" 
                        + "<a target='_blank' href='http://localhost:9000/location/" +eachTweet.latitude 
                        + "/"+eachTweet.longitude+"'> ("+eachTweet.place+")</a>" +"Tweet - "+eachTweet.tweet;
                 } else {
                     tweets += "<a target='_blank' href='http://localhost:9000/userProfile/"+eachTweet.displayName
                     +"'>("+eachTweet.userName + ")</a>" + ":" + eachTweet.tweet + "<a target='_blank' href='http://localhost:9000/hashtag/" + eachTweet.hashTag + "'>#"
                +eachTweet.hashTag + "</a>";
                 }
        			tweets += "</p>";
        			});
        		tweets += "<hr>";
                jQuery(".display-tweets").append(tweets);
               // emojifunc();
               });
        }
        
        var ht="";
                 jQuery.get("http://localhost:9000/hashtag/" +eachTweet.hashTag).done(function (allHashtags) {
                     hashlist.concat(allHashtags);
                     allHashtags.forEach(function (eachHS) {
                         ht += "<p>=> ";
                         ht += "<b>" + eachTweet.screenName+ " ("+ eachTweet.name+ ") :</b> "
                       + eachTweet.text + "<a target='_blank' href='http://localhost:9000/hashtag/" + eachTweet.hashTag
                       "'>" + eachTweet.hashTag + "</a>";
                       ht += "</p>";
               
              });
                     ht += "<hr>";
                     jQuery(".hash-links").append(ht);
                     });
	});
	
	var wordList = [];
	
	jQuery('#wordcount-form').submit(function (wordCount) {
		
		wordCount.preventDefault();
		var word = "";
		
        if( searchKeyword.value != '' ){
        	jQuery.get("http://localhost:9000/wordstatistics/" + searchKeyword.value)
        		.done(function (wordDisplay) {
        			wordList.concat(wordDisplay);
        			word += "<h2>Here's the Word Statistics:</h2>";
        			wordDisplay.forEach(function (eachWord) {
        				word += "<p> * ";
        				word += eachWord.word + " " + eachWord.count + "<br></p>";
        			});
        		    jQuery(".display-words").append(word);
        		});
        }
	});
	
	var emojiList = [];    
    jQuery('#emotion-form').submit(function (emojifunc) {
        emojifunc.preventDefault();
        var emoji = "";
       if( searchKeyword.value != '' ){
                  jQuery.get("http://localhost:9000/emotion/" + searchKeyword.value)
                  .done(function (displayEmotion) {
               emojiList.concat(displayEmotion);
               displayEmotion.forEach(function (eachEmoji) {
                   emoji += "<h3> Average tweet emotion is ";
                   emoji += eachEmoji.emoji;
                   emoji += "</h3>";
               });
               document.getElementById("display_sentiment").innerHTML = emoji;
           });
                  
             
       }
    });
})(window);
        