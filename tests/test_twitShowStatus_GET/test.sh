#!/bin/sh
source ../utils.sh

SERVER='http://localhost:8080/com.thoughtress.jsp'
ENDPOINT='MyEndpoint'
METHOD='TwitterRequest'
URL="${SERVER}/${ENDPOINT}/${METHOD}"

testGet $URL data