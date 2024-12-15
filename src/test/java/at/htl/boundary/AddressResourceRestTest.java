package at.htl.boundary;

import at.htl.entity.Address;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
@TestHTTPEndpoint(AddressResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressResourceRestTest {

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

    @Test
    @TestTransaction
    @Order(1)
    void create() {

        Address testAddress = new Address(
                "Teststraße",
                "2",
                3465,
                "Teststadt",
                "Testland",
                "Teststaat"
        );

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testAddress)
                .auth().oauth2(token)
                .when()
                .post("/")
                .then()
                .log().everything()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("street", is("Teststraße"),
                        "streetNo", is(Integer.toString(2)),
                        "city", is("Teststadt"));
    }

    @Test
    @Order(2)
    void getById() {

        given()
                .pathParam("id", "1")
                .auth().oauth2(token)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body("street", is("Teststraße"),
                        "streetNo", is(Integer.toString(2)),
                        "city", is("Teststadt"));
    }

    @Test
    @Order(2)
    void getById_restriction() {

        given()
                .pathParam("id", "1")
                .auth().oauth2(token)
                .when()
                .get("/getById_partial/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body(  "street", nullValue(),
                        "zipno", is(Integer.toString(3465)),
                        "country", is("Testland"),
                        "city", is("Teststadt"),
                        "state", is("Teststaat"),
                        "size()", is(4));
    }

    @Test
    @Order(3)
    void updateById() {

        Address testUpdateAddress = new Address(
                "UpdateTeststraße",
                "22",
                3465,
                "Teststadt",
                "updatedTestland",
                "Teststaat"
        );
        testUpdateAddress.setId(5L);


        given()
                .contentType(MediaType.APPLICATION_JSON)
                .auth().oauth2(token)
                .body(testUpdateAddress)
                .when()
                .put("/update")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("street", is(testUpdateAddress.getStreet()),
                        "id", is(5),
                         "country", is(testUpdateAddress.getCountry()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .auth().oauth2(token)
                .pathParam("id", testUpdateAddress.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("street", is(testUpdateAddress.getStreet()));
    }

    @Test
    @Order(4)
    void delete() {
        Long id = 1L;
        given()
                .pathParam("id", id)
                .auth().oauth2(token)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }
}