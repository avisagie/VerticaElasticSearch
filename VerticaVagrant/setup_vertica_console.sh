#!/bin/bash

set -e

. config.sh

# Automatically start vertica agent. In 7.2.3 its systemd packaging
# for centos 7 is still a mess, so we have our own service file
cp /vagrant/vertica_agent.service /etc/systemd/system
systemctl daemon-reload
systemctl enable vertica_agent.service
systemctl start vertica_agent.service

# install console
sudo rpm -Uvh $VERTICA_CONSOLE_RPM
