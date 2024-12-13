package at.htl.boundary;

import at.htl.controller.ApplicationRepository;
import at.htl.controller.JobOfferRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ApplicationResource.class)
@Disabled
class ApplicationResourceRestTest extends AccessTokenProvider {

    @Test
    void getTimeDifference_days() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowSpy = Mockito.spy(now);

        Long testId = 1L;

        Mockito.doReturn(LocalDateTime.of(2024, 11, 10, 15, 30, 0)).when(nowSpy);

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