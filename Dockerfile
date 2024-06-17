FROM eclipse-temurin:21-jre

WORKDIR /front

COPY target/front.jar /front/front.jar

EXPOSE 8080 80

# Set up Nginx
COPY nginx.conf /etc/nginx/nginx.conf

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

#Run the entrypoint script
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]