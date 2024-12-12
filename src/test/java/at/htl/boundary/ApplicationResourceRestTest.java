package at.htl.boundary;

import at.htl.controller.ApplicationRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ApplicationResource.class)
class ApplicationResourceRestTest {

    @Test
    void getTimeDifference_days() {
        Long testId = 1L;

        Mockito.when(LocalDateTime.now())
                .thenReturn(LocalDateTime.of(2024, 11, 10, 15, 30, 0));

        given()
                .pathParam("id", testId)
                .when()
                .get("/getTimeDifference/{id}")
                .then()
                .statusCode(200)
                .log().body()
                .body(is("13"));
    }
}