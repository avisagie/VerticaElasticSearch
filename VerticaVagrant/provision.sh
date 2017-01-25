#!/bin/bash

set -e

cd /vagrant
. config.sh

# Check if the files are all here, so it can fail sooner rather than
# later.
echo Checking dependencies . . . 
sha1sum $VERTICA_RPM

# yum cache
sudo sed -i 's/keepcache=.*/keepcache=1/' /etc/yum.conf

(
    if [ -f /vagrant/rpmcache.tar.gz ]
    then
	cd /
	sudo tar -xzf /vagrant/rpmcache.tar.gz
    fi
)

# install prerequisites
sudo yum install -y chrony gcc gcc-c++ openssh which dialog gdb sysstat dstat mcelog unzip lsof git

# capture yum downloads again
tar -czf /vagrant/rpmcache-tmp.tar.gz /var/cache/yum && mv /vagrant/rpmcache-tmp.tar.gz /vagrant/rpmcache.tar.gz

# Start chrony
sudo systemctl start chronyd.service

# Vertica data space
sudo /vagrant/data_volume.sh

# Vertica prerequisite settings
sudo sed -i s/SELINUX=.*/SELINUX=permissive/ /etc/selinux/config
sudo /vagrant/io_scheduler.sh
sudo /vagrant/swap.sh

# vertica itself
sudo rpm -ivh ${VERTICA_RPM}
sudo /opt/vertica/sbin/install_vertica --hosts localhost --dba-user-password dbadmin --data-dir /verticadata/ --license CE --accept-eula

# Uncomment this if you want the vertica console gui.  You can also
# run it manually later. Since this is a single node its use is
# limited.
#setup_vertica_console.sh

su - dbadmin -c '(cd /vagrant; ./create_database.sh)'
