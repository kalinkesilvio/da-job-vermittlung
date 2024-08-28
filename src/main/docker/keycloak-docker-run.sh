docker run --name keycloak_dev -p 8190:8190 \
        -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin \
        quay.io/keycloak/keycloak:25.0.4 \
        start-dev
        $BASH