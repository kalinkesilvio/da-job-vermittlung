package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Company;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

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
        company1.companyName = "CP GmbH";
        company1.branche = "Heiztechnik";
    }


    @Test
    void createCompanyMockOK() {

        Mockito.doNothing().when(companyRepository).persist(
                ArgumentMatchers.any(Company.class)
        );

        Mockito.when(companyRepository.isPersistent(
                ArgumentMatchers.any(Company.class)
        )).thenReturn(true);

        Response response = companyResource.create(company1);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
    }

    @Test
    void createCompanyMockKO() {

        Mockito.doNothing().when(companyRepository).persist(
                ArgumentMatchers.any(Company.class)
        );

        Mockito.when(companyRepository.isPersistent(
                ArgumentMatchers.any(Company.class)
        )).thenReturn(false);

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

        assertNotNull(entity.isEmpty());
        assertEquals(1L, entity.get(0).id);
    }

}