package twitterfetcher;

import java.util.Date;;

public class Tweet {
	
	private long id;
	private Date create_at;
	private int getAccessLevel;
	private long currentUserRetweetid;
	private int favouriteCount;
	//private String geoLocation;
	private String inReplyToScreenName;
	private long inReplyToStatusId;
	private long inReplyToUserId;
	private String lang;
	private String place;
	private String quotedStatus;
	private long quotedStatusId;
	private String rateLimitStatus;
	private String source;
	private boolean isFavourited;
	private boolean isRetweet;
	private boolean isPossiblySensitive;
	private boolean isRetweetedByMe;
	private boolean isTruncated;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Date date) {
		this.create_at = date;
	}
	public int getGetAccessLevel() {
		return getAccessLevel;
	}
	public void setGetAccessLevel(int getAccessLevel) {
		this.getAccessLevel = getAccessLevel;
	}
	public long getCurrentUserRetweetid() {
		return currentUserRetweetid;
	}
	public void setCurrentUserRetweetid(long currentUserRetweetid) {
		this.currentUserRetweetid = currentUserRetweetid;
	}
	public int getFavouriteCount() {
		return favouriteCount;
	}
	public void setFavouriteCount(int favouriteCount) {
		this.favouriteCount = favouriteCount;
	}
	/**
	public String getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}
	*/
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}
	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}
	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}
	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}
	public long getInReplyToUserId() {
		return inReplyToUserId;
	}
	public void setInReplyToUserId(long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getQuotedStatus() {
		return quotedStatus;
	}
	public void setQuotedStatus(String quotedStatus) {
		this.quotedStatus = quotedStatus;
	}
	public long getQuotedStatusId() {
		return quotedStatusId;
	}
	public void setQuotedStatusId(long quotedStatusId) {
		this.quotedStatusId = quotedStatusId;
	}
	public String getRateLimitStatus() {
		return rateLimitStatus;
	}
	public void setRateLimitStatus(String rateLimitStatus) {
		this.rateLimitStatus = rateLimitStatus;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isFavourited() {
		return isFavourited;
	}
	public void setFavourited(boolean isFavourited) {
		this.isFavourited = isFavourited;
	}
	public boolean isRetweet() {
		return isRetweet;
	}
	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}
	public boolean isPossiblySensitive() {
		return isPossiblySensitive;
	}
	public void setPossiblySensitive(boolean isPossiblySensitive) {
		this.isPossiblySensitive = isPossiblySensitive;
	}
	public boolean isRetweetedByMe() {
		return isRetweetedByMe;
	}
	public void setRetweetedByMe(boolean isRetweetedByMe) {
		this.isRetweetedByMe = isRetweetedByMe;
	}
	public boolean isTruncated() {
		return isTruncated;
	}
	public void setTruncated(boolean isTruncated) {
		this.isTruncated = isTruncated;
	}
	
	
	
	
	
	
	
	
}
