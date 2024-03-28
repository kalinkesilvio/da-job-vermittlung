package at.htl.boundary;

import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(JobOfferResource.class)
public class JobOfferResourceRestTest {

    @Inject
    CompanyResource companyResource;

//    @BeforeEach
//    void setUp() {
//
//    }

    @Test
    @TestTransaction
    public void create() {

        JobOffer jobOffer1 = new JobOffer();
        jobOffer1.category = "Solartechnik";
        jobOffer1.salary = 3500.00;
        jobOffer1.condition = "Bachelor Solartechnik & Energieerhaltung";
        jobOffer1.title = "End-Testing für Solartechnik";

        Company testCompany = new Company();
        testCompany.companyName = "Kuckuruz";
        testCompany.branche = "Gastwirtschaft";
        testCompany.email = "kukcuruz@office.gmail.com";

        jobOffer1.company = (Company) companyResource.getById(1L).getEntity();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jobOffer1)
                .when()
                .post("/create")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/api/joboffer/1");
    }

    @Test
    public void getById() {
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
    public void getAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/getAll")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(4));
    }

    @Test
    public void getByStringPartial_1() {
        String partial = "kell";

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
                        "get(0).title", is("Kellner an Wochenenden"));
    }

    @Test
    public void getByStringPartial_companyName() {
        String partial = "sanjis";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("partial", partial)
                .when()
                .get("/getByStringPartial/{partial}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("size()", is(2))
                .log().everything()
                .body("get(0).company.companyName", is("Sanjis Kochstube"));
    }

    @Test
    public void getByStringPartial_NOT_FOUND() {
        String partial = "KoAWDAWFAG4ch";

        given()
                .pathParam("partial", partial)
                .when()
                .get("/getByStringPartial/{partial}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void getRandomJobOffers_0() {
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
    public void getRandomJobOffers_1() {
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
    public void getRandomJobOffers_4() {
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
    public void getJobOfferByCategory() {
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
    public void getJobOfferByCategory_NOT_FOUND() {
        String category = "ThisMayNotExist";

        given()
                .pathParam("category", category)
                .when()
                .get("getJobOfferByCategory/{category}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    void delete() {
        given()
                .pathParam("id", 10L)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .log().everything();
    }
}

@QuarkusTest
class JobOfferDeleteTest {
    @Test
    @TestTransaction
    void delete_reference_to_application() {

        Long joboffer_id = 12L;

        given()
                .pathParam("id", joboffer_id)
                .when()
                .get("application/getByJobOfferId/{id}")
                .then()
                .log().body()
                .statusCode(Response.Status.OK.getStatusCode());


        given()
                .pathParam("id", 12L)
                .when()
                .delete("joboffer/{id}")
                .then()
                .statusCode(200)
                .log().everything();

        given()
                .pathParam("id", joboffer_id)
                .when()
                .get("application/getByJobOfferId/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
