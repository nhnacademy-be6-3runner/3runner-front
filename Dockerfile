FROM eclipse-temurin:21-jre

WORKDIR /front

COPY target/front.jar /front/front.jar

EXPOSE 3000 3001

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

#Run the entrypoint script
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]