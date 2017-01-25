#!/bin/bash

mkfs.ext4 -L verticadata -O 64bit -F /dev/sdb
mkdir /verticadata
echo "LABEL=verticadata /verticadata                       ext4     defaults        0 0" >> /etc/fstab
mount -a
