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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ActionResource.class)
class ActionResourceRestTest {

    private Action testAction;

    @Inject
    ApplicantRepository applicantRepository;

    @Inject
    CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        this.testAction = new Action();
        this.testAction.id = 2L;
        this.testAction.actionName = "high interest";
        this.testAction.actionDate = LocalDateTime.parse(
                LocalDateTime.of(2022, 5, 10, 15, 22, 32)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    @TestTransaction
    void create() {
        testAction.applicant = applicantRepository.getApplicantById(14L);
        testAction.company = companyRepository.getCompanyById(2L);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testAction)
                .when()
                .post("/")
                .then()
                .log().everything()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:9090/api/action/3");
    }

    @Test
    void getAll() {
    }

    @Test
    void findById() {
    }
}