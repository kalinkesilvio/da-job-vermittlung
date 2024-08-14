package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Applicant;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ApplicantResource.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicantResourceRestTest {

     private Applicant applicant;
    private Applicant applicant2;

    @Inject
    ApplicantRepository applicantRepository;

    @BeforeEach
    void setUp() {
        this.applicant = new Applicant();
        applicant.setJobBranche("Medientechnik");
        applicant.setFirstName("John");
        applicant.setSkillDescr("handy with camera stuff");
        applicant.setId(1L);

        this.applicant2 = new Applicant();
        applicant2.setJobBranche("Informatik");
        applicant2.setFirstName("Max");
        applicant2.setSkillDescr("skilled with SQL Queries");
        applicant2.setId(2L);
    }

    @Test
//    @Order(4)
    @TestTransaction
    void create() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.applicant)
                .when()
                .post("/create")
                .prettyPeek()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/api/applicant/1");
    }

//    @Test
//    void updateById() {
//    }

    @Test
//    @Order(1)
    void getById() {
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
//    @Order(2)
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
//    @Order(4)
    void delete() {
        given()
                .pathParam("id", 15L)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .log().everything();
    }

    @Test
//    @Order(3)
    void getAllByBranche() {
        given()
                .pathParam("branche", "Gastronomie")
                .when()
                .get("/getAllByBranche/{branche}")
                .peek()
                .then()
                .statusCode(200)
                .log().body()
                .body("size()", is(1));
    }

    @Test
    void updateById() {

        Applicant testApplicant = applicantRepository.getApplicantById(14L);
        testApplicant.setLastName("Prameeeeek");
        testApplicant.setEmail("pramek1231234@gmail.com");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testApplicant.getId())
                .body(testApplicant)
                .when()
                .put("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("lastName", is(testApplicant.getLastName()),
                        "email", is(testApplicant.getEmail()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testApplicant.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("lastName", is(testApplicant.getLastName()),
                        "email", is(testApplicant.getEmail()));
    }

    @Test
    void updateById_Address() {

        Applicant testApplicant = applicantRepository.getApplicantById(14L);
        testApplicant.setLastName("Prameeeeek");
        testApplicant.setEmail("pramek1231234@gmail.com");

        testApplicant.getAddress().setCity("UPDATED STADT");
        testApplicant.getAddress().setStreetNo("123 A");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testApplicant.getId())
                .body(testApplicant)
                .when()
                .put("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("lastName", is(testApplicant.getLastName()),
                        "email", is(testApplicant.getEmail()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testApplicant.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("lastName", is(testApplicant.getLastName()),
                        "email", is(testApplicant.getEmail()));
    }
}