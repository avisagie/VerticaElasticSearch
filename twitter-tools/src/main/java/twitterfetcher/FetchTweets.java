package twitterfetcher;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


public class FetchTweets {
	
	
	private static String oAuthConsumerKey = "YOUR-CONSUMER-KEY-GOES-HERE";
	private static String oAuthConsumerSecret = "YOUR-CONSUMER-SECRET-GOES-HERE";
	private static String oAuthAccessToken = "YOUR-ACCESS-TOKEN-GOES-HERE";
	private static String oAuthAccessTokenSecret = "YOUR-ACCESS-TOKEN-SECRET-GOES-HERE";
	
	//This aint permanent
	private static final String FILENAME1 = "C:\\Users\\User\\Desktop\\Vastech_Internship\\VerticaElasticSearch\\doc\\tweets.txt";
	private static final String FILENAME2 = "C:\\Users\\User\\Desktop\\Vastech_Internship\\VerticaElasticSearch\\doc\\elasticsearch.txt";
	
	
	
	
	private static TwitterStream config() {
		
		
		ConfigurationBuilder configBuilder = new ConfigurationBuilder();
		configBuilder.setDebugEnabled(true)
		.setOAuthConsumerKey(oAuthConsumerKey)
		.setOAuthConsumerSecret(oAuthConsumerSecret)
		.setOAuthAccessToken(oAuthAccessToken)
		.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
		

		return new TwitterStreamFactory(configBuilder.build()).getInstance();
		
		
	}
	
	
	
	
	
	
	private static void getTweets(){
		
		
		
		StatusListener listener = new StatusListener(){

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
				
			}

			@Override
			public void onStatus(Status status) {
				//System.out.println(status.getId() + " " + status.getText());
				try {
					writeTweetsToFile(status);
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		TwitterStream twitterStream = config();
		twitterStream.addListener(listener);
		
		twitterStream.sample();
					
				
		
	}
	
	private static void writeTweetsToFile(Status status) throws InterruptedException, IOException {
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILENAME1));
		BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME2));
		
		while(true){
			bw.write(status.getId() + "----" + status.getText());
			bw.newLine();
			System.out.println((status.getId() + "|" + status.getText()));
			
			Tweet tweet = new Tweet();
			tweet.setCreate_at(status.getCreatedAt());
			tweet.setCurrentUserRetweetid(status.getCurrentUserRetweetId());
			tweet.setFavouriteCount(status.getFavoriteCount());
			tweet.setFavourited(status.isFavorited());
			//tweet.setGeoLocation(status.getGeoLocation().toString());
			tweet.setGetAccessLevel(status.getAccessLevel());
			tweet.setId(status.getId());
			tweet.setInReplyToScreenName(status.getInReplyToScreenName());
			tweet.setInReplyToStatusId(status.getInReplyToStatusId());
			tweet.setInReplyToUserId(status.getInReplyToUserId());
			tweet.setLang(status.getLang());
			//tweet.setPlace(status.getPlace().toString());
			tweet.setPossiblySensitive(status.isPossiblySensitive());
			//tweet.setQuotedStatus(status.getQuotedStatus().toString());
			tweet.setQuotedStatusId(status.getQuotedStatusId());
			//tweet.setRateLimitStatus(status.getRateLimitStatus().toString());
			tweet.setRetweet(status.isRetweet());
			tweet.setRetweetedByMe(status.isRetweetedByMe());
			tweet.setSource(status.getSource().toString());
			tweet.setTruncated(status.isTruncated());
			
			
			bufferedWriter.write(tweet.getId() + "|" + tweet.getCreate_at() + "|" + tweet.getFavouriteCount() + "|" + tweet.getGetAccessLevel()
			+ "|" + tweet.getInReplyToStatusId() + "|" + tweet.getInReplyToUserId() + "|" + tweet.getLang() + "|" + tweet.getSource() + "|" + tweet.isFavourited()
			+ "|" + tweet.isPossiblySensitive() + "|" + tweet.isRetweet() + "|" + tweet.isRetweetedByMe() + "|" + tweet.isTruncated());
			
			bufferedWriter.newLine();
			
			
			
		}
		
		/**
		
		try () {
			
			while(true) {
				
				Tweet tweet = new Tweet();
				tweet.setCreate_at(status.getCreatedAt());
				tweet.setCurrentUserRetweetid(status.getCurrentUserRetweetId());
				tweet.setFavouriteCount(status.getFavoriteCount());
				tweet.setFavourited(status.isFavorited());
				//tweet.setGeoLocation(status.getGeoLocation().toString());
				tweet.setGetAccessLevel(status.getAccessLevel());
				tweet.setId(status.getId());
				tweet.setInReplyToScreenName(status.getInReplyToScreenName());
				tweet.setInReplyToStatusId(status.getInReplyToStatusId());
				tweet.setInReplyToUserId(status.getInReplyToUserId());
				tweet.setLang(status.getLang());
				//tweet.setPlace(status.getPlace().toString());
				tweet.setPossiblySensitive(status.isPossiblySensitive());
				//tweet.setQuotedStatus(status.getQuotedStatus().toString());
				tweet.setQuotedStatusId(status.getQuotedStatusId());
				//tweet.setRateLimitStatus(status.getRateLimitStatus().toString());
				tweet.setRetweet(status.isRetweet());
				tweet.setRetweetedByMe(status.isRetweetedByMe());
				tweet.setSource(status.getSource().toString());
				tweet.setTruncated(status.isTruncated());
				
				
				bufferedWriter1.write(tweet.getId() + "|" + tweet.getCreate_at() + "|" + tweet.getFavouriteCount() + "|" + tweet.getGetAccessLevel()
				+ "|" + tweet.getInReplyToStatusId() + "|" + tweet.getInReplyToUserId() + "|" + tweet.getLang() + "|" + tweet.getSource() + "|" + tweet.isFavourited()
				+ "|" + tweet.isPossiblySensitive() + "|" + tweet.isRetweet() + "|" + tweet.isRetweetedByMe() + "|" + tweet.isTruncated());
				
				bufferedWriter.newLine();
				
				
				System.out.println(tweet.getId() + "|" + tweet.getCreate_at() + "|" + tweet.getFavouriteCount() + "|" + tweet.getGetAccessLevel() + "|" + tweet.getInReplyToScreenName()
				+ "|" + tweet.getInReplyToStatusId() + "|" + tweet.getInReplyToUserId() + "|" + tweet.getLang() + "|" + tweet.getSource() + "|" + tweet.isFavourited()
				+ "|" + tweet.isPossiblySensitive() + "|" + tweet.isRetweet() + "|" + tweet.isRetweetedByMe() + "|" + tweet.isTruncated() + "\n"); 
				
				
				
				
			}
			
			
		} catch ( IOException e) {
			e.printStackTrace();
		}
		*/
		
	
		
	}
	
	
	
	public static void main(String[] args) throws TwitterException, InterruptedException {
		
		getTweets();

			
	}
	
	

}
