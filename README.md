# Extending Vertica with ElasticSearch

The purpose of this project is to extend the functionality of vertica with  ElasticSearch's text search features.
This is accomplished by writing Vertica User Defined Scalar Function in java that queries Elasticsearch when called
in a sql SELECT statement. This is multi project that consists of 4 sub projects
*VerticaVagrant - setup vertica and elasticsearch in vagrant
*twitter-tools - streams a sample of public tweets from twitter-tools
*vagrant - write tweets data into vertica and tweet text into elasticsearch


This is an exploratory intern project to extend Vertica with
ElasticSearch's text search features. The basic idea is to write a
[Vertica User Defined Extension](http://bit.ly/2fByQQH) in java that
queries ElasticSearch when you write certain SQL queries.

See [the plan](doc/plan.md).
