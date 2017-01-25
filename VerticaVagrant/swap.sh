#!/bin/bash

dd if=/dev/zero of=/swapfile bs=1M count=1200
chmod 600 /swapfile
mkswap /swapfile
swapon /swapfile

chmod +x /etc/rc.d/rc.local
echo swapon /swapfile >> /etc/rc.d/rc.local
