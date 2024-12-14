#!/usr/bin/env bash

mvn -B package
cp src/main/docker/Dockerfile target/
docker login ghcr.io -u kalinkesilvio -p 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
docker build --tag ghcr.io/kalinkesilvio/da-job-vermittlung/backend:latest ./target
docker push ghcr.io/kalinkesilvio/da-job-vermittlung/backend:latest