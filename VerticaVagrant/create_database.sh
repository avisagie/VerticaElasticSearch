#!/bin/bash

set -e

. config.sh

admintools -t create_db -d ${DATABASENAME} \
	   --password=${DATABASEPASSWORD} \
	   --data_path=/verticadata/data \
	   --catalog_path=/verticadata/catalog \
	   --hosts=127.0.0.1

# this also starts the database
