package twitterfetcher;

import java.util.Date;;

public class VerticaData {
	
	private long id;
	private Date createdAt;
	private int getAccessLevel;
	private long currentUserRetweetid;
	private int favouriteCount;
	//private String geoLocation;
	//private String inReplyToScreenName;
	private long inReplyToStatusId;
	private long inReplyToUserId;
	private String lang;
	//private String place;
	private String quotedStatus;
	private long quotedStatusId;
	private String rateLimitStatus;
	private String source;
	private boolean isFavourited;
	private boolean isRetweet;
	private boolean isPossiblySensitive;
	private boolean isRetweetedByMe;
	private boolean isTruncated;
	
	
	public VerticaData(long id, Date createdAt, int favouriteCount, int getAccessLevel,
			long inReplyToStatusId, long inReplyToUserId, String language, String source, boolean isFavourited,
			boolean isPossiblySensitive, boolean isRetweet, boolean isRetweetByMe, boolean isTruncated) {
		
		this.id = id;
		this.createdAt = createdAt;
		this.favouriteCount = favouriteCount;
		this.getAccessLevel = getAccessLevel;
		this.inReplyToStatusId = inReplyToStatusId;
		this.inReplyToUserId = inReplyToUserId;
		this.lang = language;
		this.source = source;
		this.isFavourited = isFavourited;
		this.isPossiblySensitive = isPossiblySensitive;
		this.isRetweet = isRetweet;
		this.isRetweetedByMe = isRetweetByMe;
		this.isTruncated = isTruncated;
		
		
		
		
				
				
		
	}
	
	
	public long getId() {
		return id;
	}
	
	public Date getCreateAt() {
		return createdAt;
	}
	
	public int getGetAccessLevel() {
		return getAccessLevel;
	}
	
	public long getCurrentUserRetweetid() {
		return currentUserRetweetid;
	}
	
	public int getFavouriteCount() {
		return favouriteCount;
	}
	


	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}
	
	public long getInReplyToUserId() {
		return inReplyToUserId;
	}
	
	public String getLang() {
		return lang;
	}
	
	
	
	public String getQuotedStatus() {
		return quotedStatus;
	}
	
	public long getQuotedStatusId() {
		return quotedStatusId;
	}
	
	public String getRateLimitStatus() {
		return rateLimitStatus;
	}
	
	public String getSource() {
		return source;
	}
	
	public boolean isFavourited() {
		return isFavourited;
	}
	
	public boolean isRetweet() {
		return isRetweet;
	}
	
	public boolean isPossiblySensitive() {
		return isPossiblySensitive;
	}
	
	public boolean isRetweetedByMe() {
		return isRetweetedByMe;
	}
	
	public boolean isTruncated() {
		return isTruncated;
	}
	
	
	
	
	
	
	
	
	
}
