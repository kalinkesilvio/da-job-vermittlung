package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Applicant;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(ApplicantResource.class)
class ApplicantResourceTest {

    @InjectMock
    ApplicantRepository applicantRepositoryMock;

    @Inject
    ApplicantResource applicantResource;

    private Applicant applicant;
    private Applicant applicant2;

    @BeforeEach
    void setUp() {
        this.applicant = new Applicant();
        applicant.jobBranche = "Medientechnik";
        applicant.firstName = "John";
        applicant.skillDescr = "handy with camera stuff";
        applicant.id = 1L;

        this.applicant2 = new Applicant();
        applicant2.jobBranche = "Informatik";
        applicant2.firstName = "Max";
        applicant2.skillDescr = "skilled with SQL Queries";
        applicant2.id = 2L;
    }

    @Test
    void testGetByIdEndpointMockingOK() {
        Mockito.when(applicantRepositoryMock.findByIdOptional(1L))
                .thenReturn(Optional.of(applicant));

        Response response = applicantResource.getById(1L);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        Applicant entity = (Applicant) response.getEntity();

        assertEquals(1L, entity.id);
        assertEquals("John", entity.firstName);
        assertEquals("Medientechnik", entity.jobBranche);
    }

    @Test
    void testGetByIdEndpointMockingNotOK() {
        Mockito.when(applicantRepositoryMock.findByIdOptional(1L))
                .thenReturn(Optional.empty());

        Response response = applicantResource.getById(1L);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    void getAll() {
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant);

        Mockito.when(applicantRepositoryMock.listAll()).thenReturn(applicants);
        Response response = applicantResource.getAll();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        List<Applicant> entity = (List<Applicant>) response.getEntity();

        assertFalse(entity.isEmpty());
        assertEquals(1L, entity.get(0).id);
    }

//    @Test
//    public void testGetByIdEndpointMocking() {
//        Applicant a = new Applicant();
//
//        Mockito.when(applicantRepository.getApplicantById(12L)).thenReturn(a);
//        Assertions.assertSame(a, applicantRepository.findById(12L));
//        Assertions.assertNull(applicantRepository.findById(23L));
//    }

    @Test
    void createApplicantMockOK() {

        Applicant newApplicant = new Applicant(
                null,
                "Richard",
                "Schlaumeier",
                "bob.schlaumeier@gmail.com",
                "TESTPASSWORT123",
                null,
                "jahrelange Erfahrung in Firma Soo gesammelt",
                "Tischlerei Meister",
                "Firma mit guter technischer Ausstattung",
                "Tischler",
                "Holzverarbeitung",
                "Führung in der Werkstatt",
                38,
                true,
                null,
                null
        );

        Mockito.when(applicantRepositoryMock.save(
                ArgumentMatchers.any(Applicant.class)
        )).thenReturn(newApplicant);

        Mockito.when(Mockito.mock(Applicant.class).isPersistent()).thenReturn(true);

        Response response = applicantResource.create(newApplicant);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());

    }

    @Test
    void createApplicantMockNotOK() {

        Applicant newApplicant = new Applicant(
                null,
                "Richard",
                "Schlaumeier",
                "bob.schlaumeier@gmail.com",
                "TESTPASSWORT123",
                null,
                "jahrelange Erfahrung in Firma Soo gesammelt",
                "Tischlerei Meister",
                "Firma mit guter technischer Ausstattung",
                "Tischler",
                "Holzverarbeitung",
                "Führung in der Werkstatt",
                38,
                true,
                null,
                null
        );

        Mockito.when(applicantRepositoryMock.save(
                ArgumentMatchers.any(Applicant.class)
        )).thenReturn(applicant);

        Mockito.when(applicantRepositoryMock.isPersistent(
                ArgumentMatchers.any(Applicant.class)
        )).thenReturn(false);

        Response response = applicantResource.create(newApplicant);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNull(response.getLocation());

    }

    @Test
    void testUpdateByIdEndpointMockingOK() {

        Applicant updatedApplicant = new Applicant();
        updatedApplicant.jobBranche = "Grafikdesign";

        Mockito.when(applicantRepositoryMock.findByIdOptional(1L))
                .thenReturn(Optional.of(applicant));

        Response response = applicantResource.updateById(1L, updatedApplicant);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        Applicant entity = (Applicant) response.getEntity();

        assertEquals(1L, entity.id);
         assertEquals("Grafikdesign", entity.jobBranche);
    }

    @Test
    void testUpdateByIdEndpointMockingKO() {

        Mockito.when(applicantRepositoryMock.findByIdOptional(1L))
                .thenReturn(Optional.empty());

        Response response = applicantResource.updateById(1L, new Applicant());

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    void deleteByIdOK(){
        Mockito.when(applicantRepositoryMock.deleteById(1L)).thenReturn(true);
        Response response = applicantResource.delete(1L);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(response.getEntity(), true);
        assertNotNull(response.getEntity());
    }
}