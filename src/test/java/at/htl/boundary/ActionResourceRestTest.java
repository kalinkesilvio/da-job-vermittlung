package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.controller.CompanyRepository;
import at.htl.entity.Action;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(ActionResource.class)
class ActionResourceRestTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @TestTransaction
    void create() {
        Action testAction = new Action();
        testAction.actionName = "high interest";
//        testAction.applicant = applicantRepository.getApplicantById(14L);
//        testAction.company = companyRepository.getCompanyById(2L);
        testAction.actionDate = LocalDateTime.now();//LocalDateTime.of(2024, 12, 5, 12, 5, 12);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testAction)
                .when()
                .post("/")
                .then()
                .log().everything()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/api/action/1");
    }

    @Test
    void getAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/getAll")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void findById() {
        given()
                .pathParam("id", "100")
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body("actionName", is("favorable"),
                        "actionDate", is("2023-12-03T15:06:24"));
    }

    @Test
    @TestTransaction
    void delete() {
        Long id = 100L;
        given()
                .pathParam("id", id)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }
}