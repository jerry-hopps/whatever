#!/bin/bash

branch=$1
if [ x$1 == x ]; then
  branch='master'
fi

echo $branch
sudo service tomcat7 stop
mvn clean
git stash
git fetch
git reset --hard origin/$0
git stash apply
mvn package
sudo service tomcat7 start