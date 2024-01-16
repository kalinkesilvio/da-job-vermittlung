package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Company;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(CompanyResource.class)
class CompanyResourceRestTest {

    @Inject
    CompanyRepository companyRepository;

    private Company company1;

    @BeforeEach
    void setUp() {
        this.company1 = new Company();
        company1.companyName = "CP GmbH";
        company1.branche = "Heiztechnik";
        company1.id = 1L;
    }
    @Test
    void create() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.company1)
                .when()
                .post("/create")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:9090/api/company/1");
    }

}