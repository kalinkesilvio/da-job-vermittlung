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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(ActionResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActionResourceRestTest extends AccessTokenProvider {

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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }
}