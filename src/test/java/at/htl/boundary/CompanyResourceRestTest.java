package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Company;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestTransaction
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(CompanyResource.class)
//@Disabled
class CompanyResourceRestTest extends AccessTokenProvider {

    @Inject
    CompanyRepository companyRepository;

    private static String token = null;

    @BeforeAll
    public static void setup() {
        token = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(Map.of(
                        "username", "admin",
                        "password", "admin",
                        "grant_type", "password",
                        "client_id", "quarkus-be-job",
                        "client_secret", "yav88kWxD5uxS9VgUFaIqXQRvH4bAoXE",
                        "scope", "openid"
                ))
                .post("https://auth.htl-leonding.ac.at/realms/kalinke/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }

    private Company company1;

    @BeforeEach
    void setUp() {
        this.company1 = new Company();
        company1.setCompanyName("CP GmbH");
        company1.setBranche("Heiztechnik");
        company1.setId(2L);
    }
    @Test
    @Order(1)
    void create() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.company1)
                .auth().oauth2(token)
                .when()
                .post("/create")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "companyName", is("CP GmbH"),
                        "branche", is("Heiztechnik"),
                        "id", is(2)
                );
    }

    @Test
    @Order(2)
    void getById() {
        given()
                .pathParam("id", "3")
                .auth().oauth2(token)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body("companyName", is("Crop 7 GmbH"),
                        "branche", is("Grafik & Design"));
    }

    @Test
    @Order(3)
    void getAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .auth().oauth2(token)
                .when()
                .get("/getAll")
                .then()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @Order(4)
    void update() {
        Company companyToUpdate = companyRepository.getCompanyById(3L);
        companyToUpdate.setCompanyName("UPDATE Crop 8 GmbH");
        companyToUpdate.setEmail("crop8updated-office@gmail.com");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(companyToUpdate)
                .auth().oauth2(token)
                .when()
                .put("/update")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .log().body()
                .body("companyName", is(companyToUpdate.getCompanyName()),
                        "email", is(companyToUpdate.getEmail()));
    }

    @Test
    @Order(99)
    @Disabled
    @TestTransaction
    void delete() {

        Long removeId = 2L;

        given()
                .pathParam("id", removeId)
                .auth().oauth2(token)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .log().everything();
    }
}