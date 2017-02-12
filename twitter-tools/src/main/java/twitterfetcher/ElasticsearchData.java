package twitterfetcher;

public class ElasticsearchData {
	
	private long id;
	private String tweet;
	
	public ElasticsearchData(long id, String tweet) {
		this.id = id;
		this.tweet = tweet;
	}
	
	public long getId() {
		return id;
	}
	
	public String getTweet() {
		return tweet;
	}

}
