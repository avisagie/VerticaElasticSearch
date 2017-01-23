package com.proj.udx;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.vertica.sdk.ColumnTypes;
import com.vertica.sdk.DestroyInvocation;
import com.vertica.sdk.PartitionReader;
import com.vertica.sdk.PartitionWriter;
import com.vertica.sdk.ServerInterface;
import com.vertica.sdk.SizedColumnTypes;
import com.vertica.sdk.TransformFunction;
import com.vertica.sdk.TransformFunctionFactory;
import com.vertica.sdk.UdfException;

public class UDTFunctionFactory extends TransformFunctionFactory {

	//create an instance of the UDTFunction
	@Override
	public TransformFunction createTransformFunction(ServerInterface arg0) {
		return new UDTFunction();
	}

	 // Sets the number and data types of the input and output columns
	@Override
	public void getPrototype(ServerInterface arg0, ColumnTypes columnTypes, ColumnTypes returnTypes) {
		
		columnTypes.addVarbinary(); //get tweet id
		columnTypes.addVarchar(); // get tweet text
		
		returnTypes.addVarbinary(); // return tweet id
		returnTypes.addVarchar(); // return tweet text
		
	}

	@Override
	public void getReturnType(ServerInterface arg0, SizedColumnTypes inputTypes, SizedColumnTypes outputTypes) throws UdfException {
		
		
		//name the id column returned
		outputTypes.addVarbinary(30, "elasticsearch_id");
		//Set the max width of the  tweet text column returned and the name of the column
		outputTypes.addVarchar(1000, "tweet");
		
		
	}
	
	//Inner class that does the heavy-lifting
	
	public class UDTFunction extends TransformFunction {
		
		
		HashMap<String, String> tweets = new HashMap<>(); //Map for tweets and their ids
		

		@Override
		public void processPartition(ServerInterface arg0, PartitionReader input, PartitionWriter output)
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
					.setQuery(QueryBuilders.termQuery("tweet", input.getString(0)))
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
			/**
			for(String id: tweets.keySet()) {
				//output.setString(0, tweets.get(id));
				output.getStringWriter(0).copy(tweets.get(id));
			}
			*/
			
			
			
	
			
			do {
				String vertica_id = input.getString(0);
				
				//Compare the id from elastic to the ids in vertica
				for(String id: tweets.keySet()) {
					//output.setString(1,tweets.get(id));
					
					if(id == vertica_id) {
						//output.setString(1, tweets.get(id));
						output.getStringWriter(1).copy(tweets.get(id));
						
						output.next();
					}
					
					
				}
				
				
			} while(input.next());
			
		}
		

		private PreBuiltTransportClient extracted(Settings settings) {
			return new PreBuiltTransportClient(settings);
		}
		
		
		
	}
	

}
