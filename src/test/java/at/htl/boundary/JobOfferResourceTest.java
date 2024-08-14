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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
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
        this.jobOffer.setId(1L);
        this.jobOffer.setCategory("Gastwirtschaft");
        this.jobOffer.setCondition("flexible Arbeitsstunden");
        this.jobOffer.setSalary(2000.00);
        this.jobOffer.setTitle("Kellner*in");

        this.testCompany = new Company();
        this.testCompany.setId(30L);
        this.testCompany.setCompanyName("Kuckuruz");
        this.testCompany.setBranche("Gastwirtschaft");
        this.testCompany.setEmail("kukcuruz@office.gmail.com");
    }

    @Test
    void create() {
        Mockito.when(companyRepository.getCompanyById(30L)).thenReturn(testCompany);
        Mockito.when(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer);

        jobOffer.setCompany(testCompany);

        Response response = jobOfferResource.create(jobOffer);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
    }

    @Test
    void getAll() {
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);

        Mockito.when(jobOfferRepository.listAll()).thenReturn(jobOffers);
        Response response = jobOfferResource.getAll();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        List<JobOffer> entity = (List<JobOffer>) response.getEntity();

        assertFalse(entity.isEmpty());
        assertEquals(1L, entity.get(0).getId());
        assertEquals("Kellner*in", entity.get(0).getTitle());
    }

    @Test
    void getById() {
        Mockito.when(jobOfferRepository.findById(1L)).thenReturn(jobOffer);

        Response response = jobOfferResource.getById(1L);
        JobOffer jobOfferFromResponse = (JobOffer) response.getEntity();

        assertTrue(response.hasEntity());
        assertEquals(1L, jobOfferFromResponse.getId());
        assertEquals("Kellner*in", jobOfferFromResponse.getTitle());
    }

    @Test
    void getJobOffersByStringPartial_OK() {
        String partialString = "Kel";
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);

        Mockito.when(jobOfferRepository.getJobOffersWithPartialString(partialString)).thenReturn(jobOffers);

        Response response = jobOfferResource.getByStringPartial(partialString);
        List<JobOffer> jobOffersResponse = (List<JobOffer>) response.getEntity();

        assertTrue(response.hasEntity());
        assertEquals(1L, jobOffersResponse.get(0).getId());
        assertEquals("Kellner*in", jobOffersResponse.get(0).getTitle());
    }
    @Test
    void getJobOffersByStringPartial_NOT_FOUND() {
        String partialString = "PARF";

        Mockito.when(jobOfferRepository.getJobOffersWithPartialString(partialString)).thenReturn(List.of());

        Response response = jobOfferResource.getByStringPartial(partialString);
        List<JobOffer> jobOffersResponse = (List<JobOffer>) response.getEntity();

        assertFalse(response.hasEntity());
        assertEquals(response.getStatusInfo(), Response.Status.NOT_FOUND);
    }
}