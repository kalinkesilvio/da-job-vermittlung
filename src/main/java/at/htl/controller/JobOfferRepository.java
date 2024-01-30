package at.htl.controller;

import at.htl.entity.JobOffer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class JobOfferRepository implements PanacheRepository<JobOffer> {


    @Inject
    EntityManager em;

    @Transactional
    public JobOffer save(JobOffer jobOffer) {
        return em.merge(jobOffer);
    }

    @Transactional
    public JobOffer saveWithReturn(JobOffer jobOffer) {
        return em.merge(jobOffer);
    }

    public List<JobOffer> getJobOffersWithPartialString(String partial) {
        return findAll()
                .stream()
                .filter(j -> j.title.contains(partial)
                        || j.category.contains(partial))
                .collect(Collectors.toList());
    }


}
