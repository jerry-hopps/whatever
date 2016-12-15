#!/bin/bash

branch=$1
if [ x$1 == x ]; then
  branch='master'
fi

echo $branch
cd ~/tomcat-instances/
9090/stop-9090.sh
#8080/stop-8080.sh

cd /data/whatever
mvn clean
git stash
git fetch
git checkout $branch
git reset --hard origin/$branch
git stash apply
mvn package
#~/tomcat-instances/9090/start-8080.sh
~/tomcat-instances/9090/start-9090.sh