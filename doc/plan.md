# Plan

## Some reading
	
1. Understand Vertica's ideas about schemas and projections.
1. Git and github. Pull requests. Forks.
2. [github's markdown](https://guides.github.com/features/mastering-markdown/)
3. Gradle for java builds. Dependencies. Gradle also makes the eclipse
   project for you, making running the stuff very easy for other
   people.
	
## Dev environment
	
1. Fork this repository on github. 

## Dev VM

1.  Get [Vertica up and running in
    Vagrant](https://github.com/avisagie/VerticaVagrant/blob/master/README.md).
1.  Use the above project as a starting point to make a vagrant setup in
	this project that also includes ElasticSearch in the same
	VM. You'll also need to add java.
		
## Data

1. Get ye some tweets from the free [twitter
   spitzer](https://dev.twitter.com/streaming/reference/get/statuses/sample). They
   have structured data like usernames, timestamps, reply to, user id
   and more.
3. Load it into Vertica.  Set up a table for the data, don't load the
   text content of the tweet into Vertica. Make the [tweet
   id](https://dev.twitter.com/overview/api/twitter-ids-json-and-snowflake)
   the primary key. The easiest is probably to do this in loading in java.
1. Load the tweet content and the tweet id into ElasticSearch.
2. Illustrate some


## UDx

1. Basic hello world: make a UDx that has some hardcode data in it
   that lets you write a SELECT statement joining a table with the
   stuff in the UDx. If that even makes any sense.
2. Write a UDx in Java that hits Elastic Search.

Requirements:

1. Build the UDx java project with [gradle](http://www.gradle.org/).
2. Have deployment instructions for deploying it into Vertica.

# Organising the project

A suggested directory structure has already been created. Tweak at
will.

I'd like to be able to run the entire project easily. Steps, probably
something like this:

1. Data
   2. Setup your twitter API credentials in `twitter-tools/`
   3. Run fetcher.py (if you wrote it in python, of course. In that
      case also have all the pip install gumph in the steps too. If
      java, gradle will do it all for you.)
   4. Wait until you have enough data to your liking.
5. Start the vagrant VM. See `vagrant/` for more.
6. Copy the twitter data file to `vagrant/` and run load.sql

Put the steps into README.md.

