package at.htl.boundary;

import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestTransaction
@TestHTTPEndpoint(JobOfferResource.class)
class JobOfferResourceRestTest {

    @Inject
    CompanyResource companyResource;
    private JobOffer jobOffer1;
    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new Company();
        testCompany.id = 30L;
        testCompany.companyName = "Kuckuruz";
        testCompany.branche = "Gastwirtschaft";
        testCompany.email = "kukcuruz@office.gmail.com";

        jobOffer1 = new JobOffer();
        jobOffer1.id = 1L;
        jobOffer1.category = "Solartechnik";
        jobOffer1.salary = 3500.00;
        jobOffer1.condition = "Bachelor Solartechnik & Energieerhaltung";
        jobOffer1.title = "End-Testing für Solartechnik";
    }

    @Test
    void create() {

        this.jobOffer1.company = (Company) companyResource.getById(1L).getEntity();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.jobOffer1)
                .when()
                .post("/create")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:9090/api/joboffer/1");
    }

    @Test
    void getById() {
        given()
                .pathParam("id", 13)
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", is(13),
                        "category", is("Kartografie"),
                        "salary", is(3000.0F));
    }

    @Test
    void getAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/getAll")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(4));
    }

    @Test
    void getByStringPartial_1() {
        String partial = "Koch";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("partial", partial)
                .when()
                .get("/getByStringPartial/{partial}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(1))
                .log().everything()
                .body("get(0).category", is("Gastronomie"),
                        "get(0).title", is("Koch an Wochenenden"));
    }

    @Test
    void getByStringPartial_NOT_FOUND() {
        String partial = "KoAWDAWFAG4ch";

        given()
                .pathParam("partial", partial)
                .when()
                .get("/getByStringPartial/{partial}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void getRandomJobOffers_0() {
        int quantity = 0;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("quantity", quantity)
                .when()
                .get("getRandomJobOffers/{quantity}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    void getRandomJobOffers_1() {
        int quantity = 1;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("quantity", quantity)
                .when()
                .get("getRandomJobOffers/{quantity}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(1),
                        "get(0).id", is(notNullValue()))
                .log().body();
    }

    @Test
    void getRandomJobOffers_4() {
        int quantity = 4;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("quantity", quantity)
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
    void getJobOfferByCategory() {
        String category = "gastronomie";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("category", category)
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
    void getJobOfferByCategory_NOT_FOUND() {
        String category = "ThisMyNotExist";

        given()
                .pathParam("category", category)
                .when()
                .get("getJobOfferByCategory/{category}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}