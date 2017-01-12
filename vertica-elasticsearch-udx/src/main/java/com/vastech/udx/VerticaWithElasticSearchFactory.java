package com.vastech.udx;

//import vertica SDK
import com.vertica.sdk.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


public class VerticaWithElasticSearchFactory extends ScalarFunctionFactory {

	@Override
	public ScalarFunction createScalarFunction(ServerInterface arg0) {
		return new VerticaWithElasticSearch();
	}

	@Override
	public void getPrototype(ServerInterface serverInterface, ColumnTypes columnTypes, ColumnTypes returnType) {
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


	
	public class VerticaWithElasticSearch extends ScalarFunction {
		
		
		long likes = 0;
		
		
		
		/**
		QueryBuilders.boolQuery()
		.must(QueryBuilders.termQuery(field_toSearch,field_value))
		.must(QueryBuilders.termQuery("msg",search_term));
		*/

		@Override
		public void processBlock(ServerInterface arg0, BlockReader arg1, BlockWriter arg2)
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
				arg2.setString("error error error eroor");
				e.printStackTrace();
			}
			
			SearchResponse response = transportClient.prepareSearch("twitterdata")
					.setTypes("tweets")
					.setQuery(QueryBuilders.termQuery("tweet", arg1.getString(0).trim()))
					.get();
			
			arg2.setString(response.toString());
			transportClient.close();
			
			

		
			/**
			do {
				long favCount = arg1.getLong(0);
				
				if(favCount == likes){
					arg2.setLong(favCount);
				}
				arg2.next();
			} while(arg1.next());
			*/
			
		}
		/**
		private String getRequest() throws IOException {
			
			URL url = new URL("http://localhost:9200/twitterdata/tweets/12345/") ;
			URLConnection urlConn = url.openConnection();
			BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String line;
			while((line = input.readLine()) != null)
					return line;
			input.close();
			
			return line;
			
		}
		*/



		private PreBuiltTransportClient extracted(Settings settings) {
			return new PreBuiltTransportClient(settings);
		}
		
		
		
		
		
		
	}
	
	

}



