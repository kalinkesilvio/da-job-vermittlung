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
class AddressResourceRestTest {

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
        testUpdateAddress.setId(1L);


        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", testUpdateAddress.getId())
                .body(testUpdateAddress)
                .when()
                .put("/update/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("street", is(testUpdateAddress.getStreet()),
                        "id", is(1),
                         "country", is(testUpdateAddress.getCountry()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
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
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }
}