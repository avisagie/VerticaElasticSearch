# Plan

## Some reading
	1. Understand Vertica's ideas about schemas and projections.
	1. Git and github. Pull requests. Forks.
	
## Dev environment
	1. Fork this repository on github. 

## Dev VM
	1. Get (Vertica up and running in
       Vagrant)[https://github.com/avisagie/VerticaVagrant/blob/master/README.md].
	1. Use that project as a starting point to make a vagrant setup in
       this project that also includes ElasticSearch in the same
       VM. You'll also need to add java.
	
	
## Data
    2. Get ye some tweets from the free (twitter
       spitzer)[https://dev.twitter.com/streaming/reference/get/statuses/sample]. They
       have structured data like usernames, timestamps, reply to, user
       id and more.
	3. Load it into Vertica.  Set up a table for the data, don't load
       the text content of the tweet into Vertica. Make the (tweet
       id)[https://dev.twitter.com/overview/api/twitter-ids-json-and-snowflake]
       the primary key.
    1. Load the tweet content and the tweet id into ElasticSearch.




# Organising the project

A suggested directory structure has already been created.

