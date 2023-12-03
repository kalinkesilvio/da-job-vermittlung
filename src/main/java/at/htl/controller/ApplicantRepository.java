package at.htl.controller;

import at.htl.entity.Applicant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ApplicantRepository implements PanacheRepository<Applicant> {

    @Inject
    EntityManager em;

    @Transactional
    public void save(Applicant applicant) {
        em.persist(applicant);
    }

    public Applicant getApplicantById(Long id) {
        return findById(id);
    }

    @Transactional
    public boolean remove(Long id) {
        return deleteById(id);
    }

}
