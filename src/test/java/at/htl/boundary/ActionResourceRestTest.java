package at.htl.boundary;

import at.htl.entity.Action;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(ActionResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActionResourceRestTest extends AccessTokenProvider {

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
    @Order(3)
    void create() {
        Action testAction = new Action();
        testAction.setActionName("high interest");
//        testAction.applicant = applicantRepository.getApplicantById(14L);
//        testAction.company = companyRepository.getCompanyById(2L);
        testAction.setActionDate(LocalDateTime.now());//LocalDateTime.of(2024, 12, 5, 12, 5, 12);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testAction)
                .auth().oauth2(token)
                .when()
                .post("/")
                .then()
                .log().everything()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("actionName", is("high interest"));
    }

    @Test
    @Order(1)
    void getAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .auth().oauth2(token)
                .when()
                .get("/getAll")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @Order(2)
    void findById() {
        given()
                .pathParam("id", "1")
                .auth().oauth2(token)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body("actionName", is("favorable"),
                        "actionDate", is("03-12-2023 03:06 PM"));
    }

    @Test
    @TestTransaction
    @Order(4)
    void delete() {
        Long id = 1L;
        given()
                .pathParam("id", id)
                .auth().oauth2(token)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }
}