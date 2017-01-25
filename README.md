# Extending Vertica with ElasticSearch

The purpose of this project is to extend the functionality of vertica with  ElasticSearch's text search features.
This is accomplished by writing Vertica User Defined Scalar Function in java that queries Elasticsearch when called
in a sql SELECT statement. This is multi project that consists of 4 sub projects
* VerticaVagrant - setup vertica and elasticsearch in vagrant
* twitter-tools - streams a sample of public tweets from twitter-tools
* vagrant - write tweets data into vertica and tweet text into elasticsearch
* vertica-elasticsearch-udx - Defines the vertica UDx

## Getting Started

### Prerequisites
1. [VirtualBox](https://www.virtualbox.org/wiki/Downloads) must be installed.
2. [Vagrant](https://www.vagrantup.com/) must be installed.
2. You must have a twitter account.
3. Get [OAuth access tokens](https://apps.twitter.com/) in order to establish a connection to the Streaming API

### Setting up

Clone this repository
`git clone https://github.com/Nillihc/VerticaElasticSearch.git`

Change into VerticaVagrant directory
`cd VerticaVagrant`

Create and SSH into the vagrant machine
```
vagrant up
vagrant ssh
```



