package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Applicant;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
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
        applicant.setJobBranche("Medientechnik");
        applicant.setFirstName("John");
        applicant.setSkillDescr("handy with camera stuff");
        applicant.setId(1L);

        this.applicant2 = new Applicant();
        applicant2.setJobBranche("Informatik");
        applicant2.setFirstName("Max");
        applicant2.setSkillDescr("skilled with SQL Queries");
        applicant2.setId(2L);
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

        assertEquals(1L, entity.getId());
        assertEquals("John", entity.getFirstName());
        assertEquals("Medientechnik", entity.getJobBranche());
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
        assertEquals(1L, entity.get(0).getId());
    }

    @Test
    void createApplicantMockOK() {

        Mockito.when(applicantRepositoryMock.save(
                applicant2
        )).thenReturn(applicant2);

        Response response = applicantResource.create(applicant2);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

    }

    @Test
    void createApplicantMockNotOK() {

        Applicant newApplicant = new Applicant(
                "Richard",
                "Schlaumeier",
                "bob.schlaumeier@gmail.com",
                "TESTPASSWORT123",
                null,
                "jahrelange Erfahrung in Firma Soo gesammelt",
                "Führung in der Werkstatt",
                "Tischlerei Meister",
                "Firma mit guter technischer Ausstattung",
                "Tischler",
                "Holzverarbeitung",
                38,
                true,
                null,
                null
        );

        Mockito.when(applicantRepositoryMock.save(
                ArgumentMatchers.any(Applicant.class)
        )).thenReturn(null);

        Response response = applicantResource.create(newApplicant);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNull(response.getLocation());

    }

//    @Test
//    void testUpdateByIdEndpointMockingOK() {
//
//        Applicant updatedApplicant = new Applicant();
//        updatedApplicant.jobBranche = "Grafikdesign";
//
//        Mockito.when(applicantRepositoryMock.findByIdOptional(1L))
//                .thenReturn(Optional.of(applicant));
//
//        Response response = applicantResource.update(updatedApplicanta, UriInfo);
//
//        assertNotNull(response);
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        assertNotNull(response.getEntity());
//
//        Applicant entity = (Applicant) response.getEntity();
//
//        assertEquals(1L, entity.id);
//         assertEquals("Grafikdesign", entity.jobBranche);
//    }
//
//    @Test
//    void testUpdateByIdEndpointMockingKO() {
//
//        Mockito.when(applicantRepositoryMock.findByIdOptional(1L))
//                .thenReturn(Optional.empty());
//
//        Response response = applicantResource.update(new Applicant());
//
//        assertNotNull(response);
//        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
//        assertNull(response.getEntity());
//    }

    @Test
    void deleteByIdOK(){
        Mockito.when(applicantRepositoryMock.deleteById(1L)).thenReturn(true);
        Response response = applicantResource.delete(1L);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(response.getEntity(), false);
        assertNotNull(response.getEntity());
    }
}