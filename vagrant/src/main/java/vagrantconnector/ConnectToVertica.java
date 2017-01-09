package vagrantconnector;

import java.sql.SQLTransientConnectionException;
import java.sql.Statement;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLException;
import java.sql.DriverManager;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.vertica.jdbc.VerticaConnection;
import com.vertica.jdbc.VerticaCopyStream;



public class ConnectToVertica {
	
	
	
	public ConnectToVertica() throws UnknownHostException {
		
		
	}
	
	
	private static void indexDocument( TransportClient client, String id, String tweet) throws IOException {
		
		XContentBuilder json = jsonBuilder()
				.startObject()
					.field("tweet", tweet)
				.endObject();
						
	
		IndexResponse response = client.prepareIndex("twitterdata", "tweets", id)
						.setSource(json)
						.get();
		
		System.out.println(response.toString());
						
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException{
		
		
		Properties properties = new Properties();
		properties.put("user", "dbadmin");
		properties.put("password", "mydatA6asepa55w0rd");
		properties.put("loginTimeout", "35");
		properties.put("binaryBatchInsert", "true");
		properties.put("AutoCommit", "false");
		
		/**
		Settings settings = Settings.builder()
				.put("cluster.name", "tweets-cluster").build();
		
		
		TransportClient transportClient = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
			*/	
		
		
		Connection conn;
		try {
			/**
			String currentLine;
			BufferedReader bf = new BufferedReader(
					new FileReader("C:\\Users\\User\\Desktop\\Vastech_Internship\\VerticaElasticSearch\\doc\\elasticsearch.txt"));
			
			while((currentLine = bf.readLine()) != null) {
				
				//System.out.println(currentLine);
				String[] splitString = currentLine.split("----");
				String id = splitString[0];
				String text = splitString[1];
				System.out.println(id + "- split -" + text);
				indexDocument(transportClient, id, text);
			}
			transportClient.close();
			*/
			
			
			
			
			
			conn = DriverManager.getConnection("jdbc:vertica://localhost:5433/mydatabase", properties);
			System.out.println("Connected to the database");
			Statement statement = conn.createStatement();
			
			statement.execute("DROP TABLE IF EXISTS mytable");
			
			/**
			 *  stmt.execute("CREATE TABLE customers (Last_Name char(50), "
                            + "First_Name char(50),Email char(50), "
                            + "Phone_Number char(15))");
			 */
			statement.execute("CREATE TABLE mytable ("
					+ "id VARBINARY(200) PRIMARY KEY,"
					+ "created_at VARCHAR(100),"
					+ "favourite_count INT,"
					+ "access_level INT,"
					+ "in_reply_to_status_id VARBINARY(200),"
					+ "in_reply_to_user_id VARBINARY(200),"
					+ "language VARCHAR(10),"
					+ "source VARCHAR(200),"
					+ "is_favourited BOOLEAN,"
					+ "is_possibly_sensitive BOOLEAN,"
					+ "is_retweet BOOLEAN,"
					+ "is_retweeted_by_me BOOLEAN,"
					+ "is_truncated BOOLEAN )");
			
			String copyQuery = "COPY mytable FROM STDIN " + "DELIMITER '|' DIRECT ENFORCELENGTH";
			
			VerticaCopyStream stream = new VerticaCopyStream((VerticaConnection) conn, copyQuery);
			
			
			stream.start();
			
			String filename = "C:\\Users\\User\\Desktop\\Vastech_Internship\\VerticaElasticSearch\\doc\\tweets.txt";
			
			File inputFile = new File(filename);
			
			FileInputStream inputStream = new FileInputStream(inputFile);
			
			stream.addStream(inputStream);
			
			stream.execute();
			
			List<Long> rejects = stream.getRejects();
			
			int numRejects = rejects.size();
			
		
			
			System.out.println("Number of rows rejected is load " + numRejects);
			
			Iterator<Long> rejit = rejects.iterator();
		
			while(rejit.hasNext()){
				System.out.println("Rejected row " + rejit.next());
			}
			
			long results = stream.finish();
			System.out.println("success-results " + results);
			
			conn.commit();
		
			
			conn.close();
		} catch(SQLTransientConnectionException connException) {
			System.out.print("Network connection issue: " + connException.getMessage());
			System.out.println();
			
			return;
		} catch(SQLInvalidAuthorizationSpecException authException) {
			System.out.print("Could not log into database: " + authException.getMessage());
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	

}
