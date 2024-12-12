docker run \
 -p 3550:3550 \
 --name keycloak_da \
 -e KEYCLOAK_ADMIN=admin \
 -e KEYCLOAK_ADMIN_PASSWORD=admin \
 -e DB_VENDOR=postresql \
 -e DB_USERNAME=app \
 -e DB_PASSWORD=app \
 -v ${PWD}/db-keycloak/db:/var/lib/keycloak/data \
 quay.io/keycloak/keycloak:25.0.4 start-dev \
 --hostname-strict=false
 $SHELL