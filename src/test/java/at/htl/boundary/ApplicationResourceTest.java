package at.htl.boundary;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(ApplicationResource.class)
class ApplicationResourceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getByJobOffer() {

        Long jobOfferTestId = 12L;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", jobOfferTestId)
                .when()
                .get("/getByJobOfferId/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body("size()", is(2));
    }
}