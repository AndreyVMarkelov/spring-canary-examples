#!/bin/bash

docker build -t my-haproxy .
docker run -p 8082:8082 --net="host" -P my-haproxy
