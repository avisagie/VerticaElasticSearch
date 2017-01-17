package com.proj.udx;

//import vertica SDK
import com.vertica.sdk.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


public class VerticaWithElasticSearchFactory extends ScalarFunctionFactory {

	@Override
	public ScalarFunction createScalarFunction(ServerInterface arg0) {
		return new VerticaWithElasticSearch();
	}

	@Override
	public void getPrototype(ServerInterface serverInterface, ColumnTypes columnTypes, ColumnTypes returnType) {
		
		columnTypes.addVarbinary();
		columnTypes.addVarchar();
		returnType.addVarchar();
		
	}
	
	@Override
	public void getReturnType(ServerInterface srvInterface, SizedColumnTypes argTypes, SizedColumnTypes returnType)
			throws UdfException {
		
		returnType.addVarchar(1000);
	}
	
	
	
	
	
	/**
	 * Created the ScalarFunction as an inner class of its ScalarFunctionFactory class
	 * @author User
	 *
	 */


	
	private static final Random rnd = new Random();
	private static final long processId = rnd.nextLong();
	private static final AtomicLong idCounter  = new AtomicLong();
			

	public class VerticaWithElasticSearch extends ScalarFunction {
		
		private final long instanceId = idCounter.incrementAndGet();
		
		long likes = 0;
		HashMap<String, String> tweets = new HashMap<>(); //Map for tweets and their ids
		
		
		
		

		@Override
		public void processBlock(ServerInterface arg0, BlockReader input, BlockWriter output)
				throws UdfException, DestroyInvocation {
			
			
			Settings settings = Settings.builder()
					.put("cluster.name", "tweets-cluster").build();
			System.out.println("Before transport client");
			Client transportClient = null;
			
			try {
				transportClient = extracted(settings)
						.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.33.10"), 9300));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Search for tweets in elastic search
			SearchResponse response = transportClient.prepareSearch("twitterdata")
					.setTypes("tweets")
					.setQuery(QueryBuilders.termQuery("tweet", input.getString(1)))
					.get();
			
			//Iterate through the results and get the id with its tweet
			SearchHit[] searchhits = response.getHits().getHits();
			for(SearchHit hit : searchhits){
				String id = hit.getId();
				String tweet = hit.getSource().get("tweet").toString();
				tweets.put(id, tweet);
			
				//output.setString( id + "----" + tweet);
			}
					
			
			transportClient.close();
			
		
			
			do {
				String vertica_id = input.getString(0).toString();
				if(tweets.containsKey(vertica_id)) {
					output.setString(tweets.get(vertica_id));
				}
				else {
					output.setString(null);
				}
				output.next();
				
				
			} while(input.next());
			
	
			
			
		}
		



		private PreBuiltTransportClient extracted(Settings settings) {
			return new PreBuiltTransportClient(settings);
		}
		
		
		
		
		
		
	}
	
	

}



