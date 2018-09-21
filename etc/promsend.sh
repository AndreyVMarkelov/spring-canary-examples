#!/bin/bash

for i in `seq 1 100`; do
    curl http://localhost:$1/sample/true
    curl http://localhost:$1/sample/false
    sleep 0.01
done
