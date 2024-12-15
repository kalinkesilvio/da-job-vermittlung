package at.htl.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static io.restassured.RestAssured.given;

import java.util.Map;

@ApplicationScoped
public class AccessTokenProvider {

    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    String authServerUrl;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    protected String getAccessToken(String username, String password) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(Map.of(
                        "username", username,
                        "password", password,
                        "grant_type", "password",
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "scope", "openid"
                ))
                .post(authServerUrl + "/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }

}
