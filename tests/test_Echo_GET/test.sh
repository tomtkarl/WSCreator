#!/bin/sh
source ../utils.sh

SERVER='http://localhost:8080/com.thoughtress.jsp'
ENDPOINT='MyEndpoint'
METHOD='EchoRequest'
URL="${SERVER}/${ENDPOINT}/${METHOD}"

testGet $URL data