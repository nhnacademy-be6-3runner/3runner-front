#!/bin/bash

docker run --rm --name certbot \
  -v "./certbot/conf:/etc/letsencrypt" \
  -v "./certbot/www:/var/www/certbot" \
  certbot/certbot renew

docker exec nginx nginx -s reload