#!/bin/bash
BASEDIR=$(cd "$(dirname "$0")/../"; pwd)
LIB=$BASEDIR/lib/
cp="";
for file in `ls $LIB`
do
  if [ -z $cp ];then
    cp=$file
  else
    cp="$cp:$file"
  fi
done

cd $BASEDIR
java -cp $cp -jar spider-1.0.jar
