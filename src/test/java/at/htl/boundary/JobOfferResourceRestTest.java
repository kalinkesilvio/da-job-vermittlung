package at.htl.boundary;

import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(JobOfferResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JobOfferResourceRestTest extends AccessTokenProvider {

    @Inject
    CompanyResource companyResource;



//    @BeforeEach
//    void setUp() {
//
//    }

    @Test
    @TestTransaction
    @Order(1)
    public void create() {

        JobOffer jobOffer1 = new JobOffer();
        jobOffer1.setCategory("Solartechnik");
        jobOffer1.setSalary(3500.00);
        jobOffer1.setCondition("Bachelor Solartechnik & Energieerhaltung");
        jobOffer1.setTitle("End-Testing für Solartechnik");

        Company testCompany = new Company();
        testCompany.setCompanyName("Kuckuruz");
        testCompany.setBranche("Gastwirtschaft");
        testCompany.setEmail("kukcuruz@office.gmail.com");

        jobOffer1.setCompany((Company) companyResource.getById(1L).getEntity());

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jobOffer1)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .post("/create")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().everything()
                .body(
                        "company.companyName", is(jobOffer1.getCompany().getCompanyName()),
                        "company.branche", is(jobOffer1.getCompany().getBranche()),
                        "category", is(jobOffer1.getCategory()),
                        "condition", is(jobOffer1.getCondition()),
                        "salary", is(jobOffer1.getSalary().floatValue())
                );
    }

    @Test
    @Order(2)
    public void getById() {
        given()
                .pathParam("id", 13)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", is(13),
                        "category", is("Kartografie"),
                        "salary", is(3000.0F));
    }

    @Test
    @Order(2)
    public void getById_partial() {
        given()
                .pathParam("id", 13)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/partial/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", nullValue(),
                        "category", is("Kartografie"),
                        "condition", is("langjährige Erfahrung als Kartografe"),
                        "title", is("Kartografe (Meerebene)"),
                        "salary", nullValue());
    }

    @Test
    @Order(3)
    public void getAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/getAll")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(5));
    }

    @Test
    @Order(4)
    public void getByStringPartial_1() {
        String partial = "kell";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("partial", partial)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/getByStringPartial/{partial}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(1))
                .log().everything()
                .body("get(0).category", is("Gastronomie"),
                        "get(0).title", is("Kellner an Wochenenden"));
    }

    @Test
    @Order(5)
    public void getByStringPartial_companyName() {
        String partial = "nam";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("partial", partial)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/getByStringPartial/{partial}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("size()", is(2))
                .log().everything()
                .body("get(0).company.companyName", is("Nami Kartografie"));
    }

    @Test
    @Order(6)
    public void getByStringPartial_NOT_FOUND() {
        String partial = "KoAWDAWFAG4ch";

        given()
                .pathParam("partial", partial)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("/getByStringPartial/{partial}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(7)
    public void getRandomJobOffers_0() {
        int quantity = 0;
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("quantity", quantity)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("getRandomJobOffers/{quantity}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @Order(8)
    public void getRandomJobOffers_1() {
        int quantity = 1;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("quantity", quantity)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("getRandomJobOffers/{quantity}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(1),
                        "get(0).id", is(notNullValue()))
                .log().body();
    }

    @Test
    @Order(9)
    public void getRandomJobOffers_4() {
        int quantity = 4;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("quantity", quantity)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("getRandomJobOffers/{quantity}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(4),
                        "get(0).id", is(notNullValue()),
                        "get(1).id", is(notNullValue()),
                        "get(2).id", is(notNullValue()),
                        "get(3).id", is(notNullValue()))
                .log().body();
    }

    @Test
    @Order(10)
    public void getJobOfferByCategory() {
        String category = "gastronomie";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("category", category)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("getJobOfferByCategory/{category}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(2))
                .log().everything()
                .body("get(0).category", is("Gastronomie"),
                        "get(1).category", is("Gastronomie"));
    }

    @Test
    @Order(11)
    public void getJobOfferByCategory_NOT_FOUND() {
        String category = "ThisMayNotExist";

        given()
                .pathParam("category", category)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("getJobOfferByCategory/{category}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    @Order(99)
    void delete() {
        given()
                .pathParam("id", 10L)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .log().everything();
    }
}

@QuarkusTest
@Disabled
class JobOfferDeleteTest extends AccessTokenProvider {
    @Test
    @TestTransaction
    @Order(98)
    void delete_reference_to_application() {

        Long joboffer_id = 13L;

        given()
                .pathParam("id", joboffer_id)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("application/getByJobOfferId/{id}")
                .then()
                .log().body()
                .statusCode(Response.Status.OK.getStatusCode());


        given()
                .pathParam("id", 12L)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .delete("joboffer/{id}")
                .then()
                .statusCode(200)
                .log().everything();

        given()
                .pathParam("id", joboffer_id)
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .get("application/getByJobOfferId/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
