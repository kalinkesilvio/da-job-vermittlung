package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Applicant;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.yasson.internal.JsonbDateFormatter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.CoreMatchers.is;

import static org.assertj.core.api.Assertions.*;
@QuarkusTest
@TestHTTPEndpoint(ApplicantResource.class)
class ApplicantResourceTest {

    @Inject
    ApplicantRepository applicantRepository;

    @Test
    void testGetByIdEndpoint() {

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
    void testCreateApplicant() {

        Applicant applicant = new Applicant(
                    "Bob",
                    "Schlaumeier",
                    "bob.schlaumeier@gmail.com",
                    "TESTPASSWORT123",
                    null,
                    "jahrelange Erfahrung in Firma Soo gesammelt",
                    "Tischlerei Meister",
                    "Firma mit guter technischer Ausstattung",
                    "Tischler",
                    "Holzverarbeitung",
                    "Führung in der Werkstatt",
                    38,
                    true,
                    null,
                    null
        );

        given()
                .body(applicant)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                //    .log().all()
                .when()
                    .post("/create")
                //    .peek()
                .then()
                .log().body()
                    .statusCode(Response.Status.CREATED.getStatusCode())
                    .header("location", "http://localhost:8081/api/applicant/2");
    }

    @Test
    void testGetApplicantsByBranche() {

        Applicant a1 = new Applicant();

        a1.firstName = "Joseph";
        a1.jobBranche = "Gastronomie";

        this.applicantRepository.save(a1);

        given()
                .pathParam("branche", "Gastronomie")
                .when()
                .get("/getAllByBranche/{branche}")
        //        .peek()
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }
}