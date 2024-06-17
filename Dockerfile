FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/bookstore-0.0.1-SNAPSHOT.jar /bookstore/bookstore.jar

EXPOSE 8080

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

# Run the entrypoint script
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]