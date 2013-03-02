#!/bin/sh
#source ./utils.sh
TESTFILE="test.sh"
TESTDIR=`pwd`
OUTFILE="out"
GREEN='\033[32m'
RED='\033[31m'
NO_COLOUR='\033[0m'
function colour_print(){
	echo -e "$1$2${NO_COLOUR}"
}

function run_test(){
	#echo "Run test $1"
	cd $1
	./${TESTFILE} > ${OUTFILE}
	DIGEST=`openssl dgst -sha1 -hex out | awk '{print $2}'`
	if [ -e answer ]
	then
		ANSWER_DIGEST=`openssl dgst -sha1 -hex answer | awk '{print $2}'`
	else
		colour_print ${RED} "$1: Answer file does not exist!"
		exit -1
	fi
	if [ ${DIGEST} == ${ANSWER_DIGEST} ]
	then
		colour_print ${GREEN} "OK!"
	else 
		colour_print ${RED} "FAIL: $1"
	fi
	rm ${OUTFILE}
	cd ${TESTDIR};
}


for test in `find . -name 'test_*' -type d`;
do 
run_test ${test}
done
