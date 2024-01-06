package at.htl.boundary;

import at.htl.entity.Applicant;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.yasson.internal.JsonbDateFormatter;
import org.junit.jupiter.api.Test;

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

    @Test
    void create() {
    }

    @Test
    void testGetByIdEndpoint() {

    given()
            .pathParam("id", "14")
            .when()
            .get("/{id}")
            .then()
            .log().body()
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
                    "2014-06-28T00:00:00",
                    38,
                    true,
                    null,
                    null
        );

        given()
                .body(applicant)
                .contentType(MediaType.APPLICATION_JSON)
                .log().all()
                .when()
                .post("/create")
                .peek()
                .then()
                .log().body()
                .statusCode(200);
    }
}