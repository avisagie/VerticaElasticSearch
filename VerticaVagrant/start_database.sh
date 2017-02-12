#!/bin/bash

set -e

. config.sh

admintools -t start_db -d ${DATABASENAME} \
	   --password=${DATABASEPASSWORD}
