package at.htl.boundary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.CoreMatchers.is;

import static org.assertj.core.api.Assertions.*;
@QuarkusTest
class ApplicantResourceTest {

    @Test
    void create() {
    }

    @Test
    void testGetByIdEndpoint() {

    given()
            .pathParam("id", "14")
            .when()
            .get("/applicant/{id}")
            .then()
            .log().body()
            .statusCode(200)
            .body("firstName", is("Ludwig"),
                    "jobField", is("Koch"));

    }
}