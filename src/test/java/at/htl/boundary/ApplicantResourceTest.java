package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Applicant;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.yasson.internal.JsonbDateFormatter;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.CoreMatchers.is;

import static org.assertj.core.api.Assertions.*;
@QuarkusTest
@TestHTTPEndpoint(ApplicantResource.class)
class ApplicantResourceTest {

    @InjectMock
    ApplicantRepository applicantRepository;

    @Inject
    ApplicantResource applicantResource;

    private Applicant applicant;

    @BeforeEach
    void setUp() {
        this.applicant = new Applicant();
        applicant.jobBranche = "Medientechnik";
        applicant.firstName = "John";
        applicant.skillDescr = "handy with camera stuff";
        applicant.id = 1L;
    }

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
    void testGetByIdEndpointMockingOK() {
        Mockito.when(applicantRepository.findByIdOptional(1L))
                .thenReturn(Optional.of(applicant));

        Response response = applicantResource.getById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response.getEntity());

        Applicant entity = (Applicant) response.getEntity();

        Assertions.assertEquals(1L, entity.id);
        Assertions.assertEquals("John", entity.firstName);
        Assertions.assertEquals("Medientechnik", entity.jobBranche);
    }

    @Test
    void testGetByIdEndpointMockingNotOK() {
        Mockito.when(applicantRepository.findByIdOptional(1L))
                .thenReturn(Optional.empty());

        Response response = applicantResource.getById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Assertions.assertNull(response.getEntity());
    }

    @Test
    void getAll() {
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant);

        Mockito.when(applicantRepository.listAll()).thenReturn(applicants);
        Response response = applicantResource.getAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response.getEntity());

        List<Applicant> entity = (List<Applicant>) response.getEntity();

        Assertions.assertFalse(entity.isEmpty());
        Assertions.assertEquals(1L, entity.get(0).id);
    }

//    @Test
//    public void testGetByIdEndpointMocking() {
//        Applicant a = new Applicant();
//
//        Mockito.when(applicantRepository.getApplicantById(12L)).thenReturn(a);
//        Assertions.assertSame(a, applicantRepository.findById(12L));
//        Assertions.assertNull(applicantRepository.findById(23L));
//    }

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