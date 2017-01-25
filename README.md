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
```
git clone https://github.com/Nillihc/VerticaElasticSearch.git
```

Change into VerticaVagrant directory
```
cd VerticaVagrant
```

Create and SSH into the vagrant machine
```
vagrant up
vagrant ssh
```
Configure Elasticsearch server settings in file `/etc/elasticsearch/elasticsearch.yml` by uncommenting `cluster.name`, `node.name`
and `network.host` and replacing their default values with the following values.
```
cluster.name: tweets-cluster
node.name: tweets-node
network.host:  192.168.33.10
```

In the vm, after `vagrant ssh`, you typically want to do things as
dbadmin. You can, by

```
sudo su - dbadmin
```

Run vsql (the Vertica commandline client) and when prompted for a password enter `mydatA6asepa55w0rd`

## Running
Before running the project, navigate to `/twitter-tools/src/main/java/twitterfetcher` and in
the class `FetchTweets` enter your OAuth access Tokens in the following fields

```java
private static String oAuthConsumerKey = "YOUR-CONSUMER-KEY-GOES-HERE";
private static String oAuthConsumerSecret = "YOUR-CONSUMER-SECRET-GOES-HERE";
private static String oAuthAccessToken = "YOUR-ACCESS-TOKEN-GOES-HERE";
private static String oAuthAccessTokenSecret = "YOUR-ACCESS-TOKEN-SECRET-GOES-HERE";
```

In the Root directory `VerticaElasticSearch`

1. Run ./gradlew twitter-tools:run to stream the tweets from twitter, kill the task if you happy with the number of tweets
2. Execute ./gradlew vagrant:run to write data to elasticsearch and vertica
3. ./gradlew vertica-elasticsearch-udx:fatJar - creates a UDSF with all dependencies
4. ./gradlew vertica-elasticsearch-udx:scpJar - safe copy UDSF to the vagrant machine


## Deployment

