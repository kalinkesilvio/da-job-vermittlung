package at.htl.boundary;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(ApplicationResource.class)
class ApplicationResourceTest extends AccessTokenProvider {

    private static String token = null;

    @BeforeAll
    public static void setup() {
        token = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(Map.of(
                        "username", "admin",
                        "password", "admin",
                        "grant_type", "password",
                        "client_id", "quarkus-be-job",
                        "client_secret", "yav88kWxD5uxS9VgUFaIqXQRvH4bAoXE",
                        "scope", "openid"
                ))
                .post("https://auth.htl-leonding.ac.at/realms/kalinke/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }

    @Test
    void getByJobOffer() {

        Long jobOfferTestId = 12L;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", jobOfferTestId)
                .auth().oauth2(token)
                .when()
                .get("/getByJobOfferId/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body("size()", is(2));
    }
}