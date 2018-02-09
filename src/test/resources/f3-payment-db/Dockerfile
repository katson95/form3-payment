FROM mongo:3.2.10

#create DB directory
RUN mkdir -p /data/db

EXPOSE 27017

COPY /config/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]