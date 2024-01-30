package at.htl.controller;

import at.htl.entity.JobOffer;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class JobOfferRepositoryTest {

    @Inject
    JobOfferRepository jobOfferRepository;

    @Test
    void getJobOffersWithPartialString() {
        String partial = "Gastr";

        List<JobOffer> jobOffers = jobOfferRepository.getJobOffersWithPartialString(partial);

        assertFalse(jobOffers.isEmpty());
        assertThat(jobOffers.size(), is(2));
        assertEquals("Gastronomie", jobOffers.get(0).category);
        assertEquals("Gastronomie", jobOffers.get(1).category);
        assertEquals("Koch an Wochenenden", jobOffers.get(0).title);
        assertEquals("Kellner an Wochenenden", jobOffers.get(1).title);
    }

    @Test
    void getJobOffersWithPartialString_NOT_FOUND() {
        String partial = "WAD";

        List<JobOffer> jobOffers = jobOfferRepository.getJobOffersWithPartialString(partial);

        assertTrue(jobOffers.isEmpty());
    }
}