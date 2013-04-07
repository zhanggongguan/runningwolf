#!/bin/bash
BASEDIR=$(cd "$(dirname "$0")/../"; pwd)
cd $BASEDIR
java -jar wolf-1.0.jar
