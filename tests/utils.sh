#!/bin.sh
function testPost(){
	#echo $1 $2 $3
	curl -X POST \
		  $1 \
		  -d @$2 \
		  -H "Content-Type:$3" \
		  2>/dev/null
}
function testGet(){
	#echo $1 $2
	curl $1 \
	-d @$2 \
	-G \
	2>/dev/null
}