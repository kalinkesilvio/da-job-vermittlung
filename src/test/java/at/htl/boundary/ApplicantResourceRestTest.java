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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicantResourceRestTest extends AccessTokenProvider {

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
    @Order(1)
    @TestTransaction
    void create() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .body(this.applicant)
                .when()
                .post("/create")
                .prettyPeek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("firstName", is("John"),
                        "skillDescr", is("handy with camera stuff"),
                        "jobBranche", is("Medientechnik"));
    }

    @Test
    @Order(2)
    void getById() {
        given()
                .pathParam("id", "1")
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/{id}")
                .then()
                // .log().body()
                .statusCode(200)
                .body("firstName", is("John"),
                        "skillDescr", is("handy with camera stuff"),
                        "jobBranche", is("Medientechnik"));
    }

    @Test
    @Order(3)
    void getAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/getAll")
                .then()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @Order(99)
    void delete() {
        given()
                .pathParam("id", 15L)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .log().everything();
    }

    @Test
    @Order(5)
    void getAllByBranche() {
        given()
                .pathParam("jobField", "Gastronomie")
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/getAllByBranche/{jobField}")
                .peek()
                .then()
                .statusCode(200)
                .log().body()
                .body("size()", is(1));
    }

    @Test
    @Order(6)
    void updateById() {

        Applicant testApplicant = applicantRepository.getApplicantById(2L);
        testApplicant.setLastName("Prameeeeek");
        testApplicant.setEmail("pramek1231234@gmail.com");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testApplicant)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .put("/update")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("lastName", is(testApplicant.getLastName()),
                        "email", is(testApplicant.getEmail()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testApplicant.getId())
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("lastName", is(testApplicant.getLastName()),
                        "email", is(testApplicant.getEmail()));
    }

/*    @Test
    void updateById_Address() {

        Applicant testApplicant = applicantRepository.getApplicantById(14L);
        testApplicant.setLastName("Prameeeeek");
        testApplicant.setEmail("pramek1231234@gmail.com");

        testApplicant.getAddress().setCity("UPDATED STADT");
        testApplicant.getAddress().setStreetNo("123 A");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testApplicant)
                .when()
                .put("/update")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("lastName", is(testApplicant.getLastName()),
                        "email", is(testApplicant.getEmail())
                );
    }*/
}