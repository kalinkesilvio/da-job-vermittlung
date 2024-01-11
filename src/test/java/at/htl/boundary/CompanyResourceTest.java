package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Company;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(CompanyResource.class)
class CompanyResourceTest {

    @Inject
    CompanyResource companyResource;

    @InjectMock
    CompanyRepository companyRepository;

    @Test
    void createCompanyMockOK() {

        Company newCompany = new Company();
        newCompany.companyName = "CP GmbH";
        newCompany.branche = "Heiztechnik";

        Mockito.doNothing().when(companyRepository).persist(
                ArgumentMatchers.any(Company.class)
        );

        Mockito.when(companyRepository.isPersistent(
                ArgumentMatchers.any(Company.class)
        )).thenReturn(true);

        Response response = companyResource.create(newCompany);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
    }

}