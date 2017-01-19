package twitterfetcher;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
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
	
	
	private static String oAuthConsumerKey = "ijjoyRAuDUM7GyiEkwxYYaktW";
	private static String oAuthConsumerSecret = "i3ehIeW4wPpA7x85kh2dArTm312vFFtaByPPR7W7JdC9a6yDHr";
	private static String oAuthAccessToken = "804667201374527488-Tf236qfLpVyZbPptLUUkIpOJPeauece";
	private static String oAuthAccessTokenSecret = "i53qMCCHRzK6MwYilDb6ErISvYFrXJO7AvTRayr0ajA8O";

	
	//This aint permanent
	private static final String FILENAME1 = "C:\\Users\\User\\Desktop\\Vastech_Internship\\VerticaElasticSearch\\doc\\tweets.txt";
	private static final String FILENAME2 = "C:\\Users\\User\\Desktop\\Vastech_Internship\\VerticaElasticSearch\\doc\\elasticsearch.txt";
	
	
	private static BlockingQueue<ElasticsearchData> elasticsearchData = new ArrayBlockingQueue<ElasticsearchData>(100);
	private static BlockingQueue<VerticaData> verticaData = new ArrayBlockingQueue<VerticaData>(100);
	
	private static int counter = 0;
	


	
	
	
	
	
	
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
		
		TwitterStream twitterStream = config();
	
				
		StatusListener listener = new StatusListener(){

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
				
			}

			@Override
			public void onStatus(Status status) {
				
				//System.out.println(status.getId() + " yadi-yada" + status.getText());
				
				
				try {
					elasticsearchData.put(new ElasticsearchData(status.getId(), status.getText()));
					verticaData.put(new VerticaData(status.getId(), status.getCreatedAt(), status.getFavoriteCount(),
							status.getAccessLevel(), status.getInReplyToStatusId(), status.getInReplyToUserId(),
							status.getLang(), status.getSource(), status.isFavorited(), status.isPossiblySensitive(),
							status.isRetweet(), status.isRetweetedByMe(), status.isTruncated()));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				counter++;
				System.out.println("Counter " + counter);
				
					
				
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Status deletion notice " + statusDeletionNotice.getStatusId());

				
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice " + numberOfLimitedStatuses);
				
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId: " + userId + "upToStatusId:" + upToStatusId);
				
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning " + warning);
				
			}
			
		};
		
		//TwitterStream twitterStream = config();
		twitterStream.addListener(listener);
		
		twitterStream.sample();
		if(counter == 100) {
			twitterStream.shutdown();
		}
					
				
		
	}
	
	private static void writeTweetsToFile() throws InterruptedException, IOException {
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILENAME1));
		BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME2));
		
		
		
		try {
			
			while(true) {
				/**
				 * Take data from the elasticsearch data queue and write
				 * it to a file
				 */
				
				ElasticsearchData data = elasticsearchData.take();
				bw.write(data.getId() + "----" + "\""+data.getTweet()+"\"");
				bw.newLine();
				System.out.println(data.getId() + "---------" +"\""+data.getTweet()+"\"" );
				
				/**
				 * Take data from the verticadata queue and write it
				 * to a file
				 */
				VerticaData  data2 = verticaData.take();
				bufferedWriter.write(data2.getId() + "|" + data2.getCreateAt() + "|" + data2.getFavouriteCount() + "|" + data2.getGetAccessLevel()
				+ "|" + data2.getInReplyToStatusId() + "|" + data2.getInReplyToUserId() + "|" + data2.getLang() + "|" + data2.getSource() + "|" + data2.isFavourited()
				+ "|" + data2.isPossiblySensitive() + "|" + data2.isRetweet() + "|" + data2.isRetweetedByMe() + "|" + data2.isTruncated());
				
				bufferedWriter.newLine();
				
				
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bw.close();
			bufferedWriter.close();
		}
		
		
		
	
		
		
			
		
	}
	
	
	
	public static void main(String[] args) throws TwitterException, InterruptedException {
		
		//getTweets();
		
		Thread getDataThread = new Thread(new Runnable(){
			public void run() {
				getTweets();
			}
		});
		
		Thread processDataThread = new Thread(new Runnable(){
			public void run() {
				try {
					writeTweetsToFile();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		getDataThread.start();
		processDataThread.start();
		
		getDataThread.join();
		processDataThread.join();
		

			
	}
	
	

}
