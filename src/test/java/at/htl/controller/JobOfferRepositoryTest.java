package at.htl.controller;

import at.htl.boundary.JobOfferResource;
import at.htl.entity.JobOffer;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class JobOfferRepositoryTest {

    @Inject
    JobOfferRepository jobOfferRepository;

    @Inject
    JobOfferResource jobOfferResource;

    @Test
    void getJobOffersWithPartialString() {
        String partial = "Gastr";

        List<JobOffer> jobOffers = jobOfferRepository.getJobOffersWithPartialString(partial);

        assertFalse(jobOffers.isEmpty());
        assertThat(jobOffers.size(), is(2));
        assertEquals("Gastronomie", jobOffers.get(0).getCategory());
        assertEquals("Gastronomie", jobOffers.get(1).getCategory());
        assertEquals("Koch an Wochenenden", jobOffers.get(0).getTitle());
        assertEquals("Kellner an Wochenenden", jobOffers.get(1).getTitle());
    }

    @Test
    void getJobOffersWithPartialString_NOT_FOUND() {
        String partial = "WAD";

        List<JobOffer> jobOffers = jobOfferRepository.getJobOffersWithPartialString(partial);

        assertTrue(jobOffers.isEmpty());
    }

    @Test
    void getRandomJobOffers0_Mock() {

        JobOfferRepository jobOfferRepository1 = new JobOfferRepository();
        JobOfferRepository jobOfferRepositorySpy = Mockito.spy(jobOfferRepository1);

        int testQuant = 0;

        Mockito.doReturn(List.of()).when(jobOfferRepositorySpy).jobOfferIds();

        Mockito.doReturn(null).when(jobOfferRepositorySpy).findJobOfferById(0L);

        List<JobOffer> resultJobOffers = jobOfferRepositorySpy.getRandomJobOffers(testQuant);

        assertTrue(resultJobOffers.isEmpty());
    }

    @Test
    void getRandomJobOffers1_Mock() {
        int testQuant = 1;

        JobOfferRepository jobOfferRepository1 = new JobOfferRepository();
        JobOfferRepository jobOfferRepositorySpy = Mockito.spy(jobOfferRepository1);

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1L);
        jobOffer.setCategory("Gastwirtschaft");
        jobOffer.setCondition("flexible Arbeitsstunden");
        jobOffer.setSalary(2000.00);
        jobOffer.setTitle("Kellner*in");

        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        List<Long> testIds = jobOffers.stream().map(JobOffer::getId).toList();

        Mockito.doReturn(testIds).when(jobOfferRepositorySpy).jobOfferIds();

        Mockito.doReturn(jobOffers.get(0)).when(jobOfferRepositorySpy).findJobOfferById(testIds.get(0));

        List<JobOffer> resultJobOffers = jobOfferRepositorySpy.getRandomJobOffers(testQuant);

        assertEquals(resultJobOffers.size(), 1);
        assertEquals(jobOffers.get(0).getId(), resultJobOffers.get(0).getId());
    }

    @Test
    void getRandomJobOffers15_Mock() {

        int testQuant = 15;

        JobOfferRepository jobOfferRepository1 = new JobOfferRepository();
        JobOfferRepository jobOfferRepositorySpy = Mockito.spy(jobOfferRepository1);

        JobOffer jobOffer1 = new JobOffer();
        jobOffer1.setId(1L);
        jobOffer1.setCategory("Gastwirtschaft");
        jobOffer1.setCondition("flexible Arbeitsstunden");
        jobOffer1.setSalary(2000.00);
        jobOffer1.setTitle("Kellner*in");

        JobOffer jobOffer2 = new JobOffer();
        jobOffer2.setId(2L);
        jobOffer2.setCategory("Mediengestaltung");
        jobOffer2.setCondition("flexible Arbeitsstunden");
        jobOffer2.setSalary(2500.00);
        jobOffer2.setTitle("Mediengestalter*in");

        JobOffer jobOffer3 = new JobOffer();
        jobOffer3.setId(3L);
        jobOffer3.setCategory("Gastwirtschaft");
        jobOffer3.setCondition("Wochenende");
        jobOffer3.setSalary(1800.00);
        jobOffer3.setTitle("Kellner*in");

        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer1);
        jobOffers.add(jobOffer2);
        jobOffers.add(jobOffer3);

        List<Long> testIds = jobOffers.stream().map(JobOffer::getId).toList();

        Mockito.doReturn(testIds).when(jobOfferRepositorySpy).jobOfferIds();

        Mockito.doReturn(jobOffers.get(0), jobOffers.get(1), jobOffers.get(2))
                .when(jobOfferRepositorySpy).findJobOfferById(testIds.get(0));

        List<JobOffer> resultJobOffers = jobOfferRepositorySpy.getRandomJobOffers(testQuant);

        assertEquals(resultJobOffers.size(), 3);

    }
}