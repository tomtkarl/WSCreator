#!/bin/sh
source ../utils.sh

SERVER='http://localhost:8080/com.thoughtress.jsp'
ENDPOINT='MyEndpoint'
URL="${SERVER}/${ENDPOINT}/"
#testPost URL datafile ContentType
testPost $URL "data" "notacontenttype"