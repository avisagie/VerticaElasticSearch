package twitterfetcher;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import twitter4j.FilterQuery;
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
	
	
	private static final String FILENAME1 = "..//doc//tweets.txt";
	private static final String FILENAME2 = "..//doc//elasticsearch.txt";
	
	
	private static BlockingQueue<ElasticsearchData> elasticsearchData = new ArrayBlockingQueue<ElasticsearchData>(5000);
	private static BlockingQueue<VerticaData> verticaData = new ArrayBlockingQueue<VerticaData>(5000);
	private static TwitterStream twitterStream;
	
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
		
		twitterStream = config();
	
				
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
		twitterStream.addListener(listener);
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track(new String[]{"a"});
		filterQuery.language(new String[]{"en"});
		twitterStream.filter(filterQuery);
					
				
		
	}
	
	private static void writeTweetsToFile() throws InterruptedException, IOException {
		
		BufferedWriter verticaBufferedWriter = new BufferedWriter(new FileWriter(FILENAME1));
		BufferedWriter elasticSearchBufferedWriter = new BufferedWriter(new FileWriter(FILENAME2));
		
		
		
		try {
			
			while(true) {
				/**
				 * Take data from the elasticsearch data queue and write
				 * it to a file
				 */
				
				ElasticsearchData esData = elasticsearchData.take();
				elasticSearchBufferedWriter.write(esData.getId() + "----" +  esData.getTweet().replaceAll("[\n\r]", " "));
				elasticSearchBufferedWriter.newLine();
				System.out.println(esData.getId() + "---------" +"\""+esData.getTweet()+"\"" );
				
				/**
				 * Take data from the verticadata queue and write it
				 * to a file
				 */
				VerticaData  vData = verticaData.take();
		
				verticaBufferedWriter.write(vData.getId() + "|" + vData.getCreateAt() + "|" + vData.getFavouriteCount() + "|" + vData.getGetAccessLevel()
				+ "|" + vData.getInReplyToStatusId() + "|" + vData.getInReplyToUserId() + "|" + vData.getLang() + "|" + vData.getSource() + "|" + vData.isFavourited()
				+ "|" + vData.isPossiblySensitive() + "|" + vData.isRetweet() + "|" + vData.isRetweetedByMe() + "|" + vData.isTruncated());
				
				verticaBufferedWriter.newLine();
				
				
				
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			elasticSearchBufferedWriter.close();
			verticaBufferedWriter.close();
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
