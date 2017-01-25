# Extending Vertica with ElasticSearch

The purpose of this project is to extend the functionality of vertica with  ElasticSearch's text search features.
This is accomplished by writing Vertica User Defined Scalar Function in java that queries Elasticsearch when called
in a sql SELECT statement. This is multi project that consists of 4 sub projects
* VerticaVagrant - setup vertica and elasticsearch in vagrant
* twitter-tools - streams a sample of public tweets from twitter-tools
* vagrant - write tweets data into vertica and tweet text into elasticsearch
* vertica-elasticsearch-udx - Defines the vertica UDx

## Getting Started

