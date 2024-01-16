package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Applicant;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(ApplicantResource.class)
class ApplicantResourceRestTest {

    @Inject
    ApplicantRepository applicantRepository;

    private Applicant applicant;
    private Applicant applicant2;

    @BeforeEach
    void setUp() {
        this.applicant = new Applicant();
        applicant.jobBranche = "Medientechnik";
        applicant.firstName = "John";
        applicant.skillDescr = "handy with camera stuff";
        applicant.id = 1L;

        this.applicant2 = new Applicant();
        applicant2.jobBranche = "Informatik";
        applicant2.firstName = "Max";
        applicant2.skillDescr = "skilled with SQL Queries";
        applicant2.id = 2L;
    }

    @Test
    void create() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.applicant)
                .when()
                .post("/create")
                .prettyPeek()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:9090/api/applicant/1");
    }

    @Test
    void updateById() {
    }

    @Test
    void getById() {
        //        Applicant a1 = new Applicant();
//
//        a1.firstName = "Ludwig";
//        a1.jobField = "Koch";
//        a1.id = Long.parseLong("9");
//
//        this.applicantRepository.save(a1);

        given()
                .pathParam("id", "14")
                .when()
                .get("/{id}")
                .then()
                // .log().body()
                .statusCode(200)
                .body("firstName", is("Ludwig"),
                        "jobField", is("Koch"));
    }

    @Test
    void getAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllByBranche() {
        Applicant a1 = new Applicant();

        a1.firstName = "Joseph";
        a1.jobBranche = "Gastronomie";

        this.applicantRepository.save(a1);

        given()
                .pathParam("branche", "Gastronomie")
                .when()
                .get("/getAllByBranche/{branche}")
                .peek()
                .then()
                .statusCode(200)
                .log().body()
                .body("size()", is(2));
    }
}