#install java
sudo yum -y install java-1.8.0-openjdk.x86_64

wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.1.1.rpm
sha1sum elasticsearch-5.1.1.rpm
sudo rpm --install elasticsearch-5.1.1.rpm


sudo systemctl daemon-reload
sudo systemctl enable elasticsearch.service
sudo systemctl start elasticsearch.service

