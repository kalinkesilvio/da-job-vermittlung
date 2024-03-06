package at.htl.boundary;

import at.htl.entity.Address;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(AddressResource.class)
class AddressResourceRestTest {

    @Test
    void getById() {
    }

    @Test
    @TestTransaction
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
                .when()
                .post("/")
                .then()
                .log().everything()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/api/address/1");
    }

    @Test
    void updateById() {

        Address testUpdateAddress = new Address(
                "UpdateTeststraße",
                "22",
                3465,
                "Teststadt",
                "updatedTestland",
                "Teststaat"
        );
        testUpdateAddress.id = 5L;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testUpdateAddress.id)
                .body(testUpdateAddress)
                .when()
                .put("/update/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("street", is(testUpdateAddress.street));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testUpdateAddress.id)
                .when()
                .get("/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("street", is(testUpdateAddress.street));
    }

    @Test
    void delete() {
    }
}