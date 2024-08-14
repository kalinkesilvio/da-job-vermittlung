package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Company;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(CompanyResource.class)
class CompanyResourceTest {

    @Inject
    CompanyResource companyResource;

    @InjectMock
    CompanyRepository companyRepository;

    private Company company1;

    @BeforeEach
    void setUp() {
        this.company1 = new Company();
        company1.setCompanyName("CP GmbH");
        company1.setBranche("Heiztechnik");
        company1.setId(1L);
    }

    @Test
    void createCompanyMockOK() {

        Mockito.when(companyRepository.save(company1)).thenReturn(company1);

        Response response = companyResource.create(company1);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
    }

    @Test
    void createCompanyMockKO() {

        Mockito.when(companyRepository.save(company1)).thenReturn(null);

        Response response = companyResource.create(company1);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNull(response.getLocation());
    }

    @Test
    void getAllMock() {

        List<Company> companies = new ArrayList<>();
        companies.add(this.company1);

        Mockito.when(companyRepository.listAll()).thenReturn(companies);

        Response response = companyResource.getAll();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        List<Company> entity = (List<Company>) response.getEntity();

        assertEquals(1L, entity.get(0).getId());
    }

}