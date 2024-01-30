package at.htl.controller;

import at.htl.entity.Applicant;
import io.quarkus.test.TestTransaction;
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
        List<Applicant> applicants = this.applicantRepository.getByBranche("Mediendesign");

        assertNotNull(applicants);
        assertFalse(applicants.isEmpty());
        assertEquals(15L, applicants.get(0).id);
        assertEquals("Mediendesign", applicants.get(0).jobBranche);
        assertEquals("Georg", applicants.get(0).firstName);
    }

    @Test
        void getApplicantsByBrancheKO() {
            List<Applicant> applicants = this.applicantRepository.getByBranche("Automechanik");

            assertNotNull(applicants);
            assertTrue(applicants.isEmpty());
    }
}