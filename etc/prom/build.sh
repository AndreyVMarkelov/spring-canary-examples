#!/bin/bash

docker build -t my-prom .
docker run -p 9090:9090 --net="host" -P my-prom
