package at.htl.controller;

import at.htl.entity.Applicant;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ApplicantRepositoryTest {

    @Inject
    ApplicantRepository applicantRepository;

    @Test
    void getApplicantsByBrancheOK() {
        List<Applicant> applicants = this.applicantRepository.getByJobField("Gastronomie");

        assertNotNull(applicants);
        assertFalse(applicants.isEmpty());
        assertEquals(2L, applicants.get(0).getId());
        assertEquals("Gastronomie", applicants.get(0).getJobField());
        assertEquals("Ludwig", applicants.get(0).getFirstName());
    }

    @Test
        void getApplicantsByBrancheKO() {
            List<Applicant> applicants = this.applicantRepository.getByJobField("Automechanik");

            assertNotNull(applicants);
            assertTrue(applicants.isEmpty());
    }
}