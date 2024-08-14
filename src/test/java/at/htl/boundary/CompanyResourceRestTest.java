package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Company;
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
@TestTransaction
//@TestMethodOrder(MethodOrderer.class)
@TestHTTPEndpoint(CompanyResource.class)
class CompanyResourceRestTest {

    @Inject
    CompanyRepository companyRepository;

    private Company company1;

    @BeforeEach
    void setUp() {
        this.company1 = new Company();
        company1.setCompanyName("CP GmbH");
        company1.setBranche("Heiztechnik");
        company1.setId(2L);
    }
    @Test
//    @Order(99)
    void create() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.company1)
                .when()
                .post("/create")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/api/company/2");
    }

    @Test
//    @Order(1)
    void getById() {
        given()
                .pathParam("id", "3")
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body("companyName", is("Crop 7 GmbH"),
                        "branche", is("Grafik & Design"));
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
                .body("size()", is(3));
    }

    @Test
    void update() {
        Company companyToUpdate = companyRepository.getCompanyById(3L);
        companyToUpdate.setCompanyName("UPDATE Crop 8 GmbH");
        companyToUpdate.setEmail("crop8updated-office@gmail.com");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", companyToUpdate.getId())
                .body(companyToUpdate)
                .when()
                .put("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("companyName", is(companyToUpdate.getCompanyName()),
                        "email", is(companyToUpdate.getEmail()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", companyToUpdate.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("companyName", is(companyToUpdate.getCompanyName()),
                        "email", is(companyToUpdate.getEmail()));
    }

    @Test
    void delete() {
        given()
                .pathParam("id", 2L)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .log().everything();
    }
}