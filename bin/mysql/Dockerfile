FROM mysql:5.7.15

MAINTAINER me

ENV MYSQL_DATABASE=f3-payment-db \
    MYSQL_ROOT_PASSWORD=root

ADD schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306