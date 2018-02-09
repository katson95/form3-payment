#!/bin/bash
mongod --fork --logpath /var/log/mongo.log
mongo --eval "db = db.getSiblingDB('f3-payment-db'); db.createCollection('PAYMENT');"
tail -f /var/log/mongo.log
