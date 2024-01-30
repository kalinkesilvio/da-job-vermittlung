package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.controller.JobOfferRepository;
import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class JobOfferResourceTest {

    @Inject
    JobOfferResource jobOfferResource;

    @InjectMock
    JobOfferRepository jobOfferRepository;

    @InjectMock
    CompanyRepository companyRepository;

    private JobOffer jobOffer;
    private Company testCompany;

    @BeforeEach
    void setUp() {
        this.jobOffer = new JobOffer();
        this.jobOffer.id = 1L;
        this.jobOffer.category = "Gastwirtschaft";
        this.jobOffer.condition = "flexible Arbeitsstunden";
        this.jobOffer.salary = 2000.00;
        this.jobOffer.title = "Kellner*in";

        this.testCompany = new Company();
        this.testCompany.id = 30L;
        this.testCompany.companyName = "Kuckuruz";
        this.testCompany.branche = "Gastwirtschaft";
        this.testCompany.email = "kukcuruz@office.gmail.com";
    }

    @Test
    void create() {
        Mockito.when(companyRepository.getCompanyById(30L)).thenReturn(testCompany);
        Mockito.when(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer);

        jobOffer.company = testCompany;

        Response response = jobOfferResource.create(jobOffer);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
    }
}