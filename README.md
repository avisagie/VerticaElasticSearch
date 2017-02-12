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

Download [vertica-8.0.0-0.x86_64.RHEL6.rpm](https://my.vertica.com/) and put it inside  `VerticaVagrant`

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

In the Root directory `VerticaElasticSearch` run the following commands, kill the `./gradlew twitter-tools:run` task if you're happy with the number of tweets

```gradle
1. ./gradlew twitter-tools:run
2. ./gradlew vagrant:run
3. ./gradlew vertica-elasticsearch-udx:fatJar
4. ./gradlew vertica-elasticsearch-udx:scpJar 
```


## Deployment
after copying the UDSF jar to the vagrant machine it is ready to be deployed.
In `vsql` run the following statements
```sql
1. ALTER DATABASE mydatabase JavaBinaryForUDx = 'usr/bin/java';
2. CREATE OR REPLACE LIBRARY myLib AS '/home/dbadmin/vertica-elasticsearch-udx-1.0.jar' LANGUAGE 'JAVA';
3. CREATE OR REPLACE FUNCTION verticaWithEs AS LANGUAGE 'JAVA' NAME 'com.proj.udx.VerticaWithElasticSearchFactory' LIBRARY myLib;
```
All set and ready to run your SELECT statements. query `president` can be replaced with any query
```
select * from (SELECT id, verticaWithEs(id USING PARAMETERS query='president') AS text FROM mytable) as joint_result where text is not null;
```

##Built with
* Java - programming language
* [Gradle](https://gradle.org/) - build tool
* [twitter4j](http://twitter4j.org/en/index.html) - used to stream tweets
* [elasticsearch](https://www.elastic.co/)
* [vagrant](https://www.vagrantup.com/)
* [vertica](https://my.vertica.com/)

