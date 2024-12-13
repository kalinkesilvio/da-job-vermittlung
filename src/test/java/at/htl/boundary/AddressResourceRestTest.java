package at.htl.boundary;

import at.htl.entity.Address;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
@TestHTTPEndpoint(AddressResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressResourceRestTest extends AccessTokenProvider {

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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
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
                .auth().oauth2(getAccessToken("admin", "admin"))
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }
}