package at.htl.controller;

import at.htl.entity.JobOffer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class JobOfferRepository implements PanacheRepository<JobOffer> {


    @Inject
    EntityManager em;

    @Transactional
    public void save(JobOffer jobOffer) {
        em.persist(jobOffer);
    }


}
