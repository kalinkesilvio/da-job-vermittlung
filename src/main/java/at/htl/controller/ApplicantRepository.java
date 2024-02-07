package at.htl.controller;

import at.htl.entity.Address;
import at.htl.entity.Applicant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ApplicantRepository implements PanacheRepository<Applicant> {

    @Inject
    EntityManager em;

    @Transactional // (value = Transactional.TxType.REQUIRES_NEW)
    public Applicant save(Applicant applicant) {
        return em.merge(applicant);
    }

    public Applicant getApplicantById(Long id) {
        return findById(id);
    }

    @Transactional
    public boolean remove(Long id) {
        return deleteById(id);
    }

    @Transactional
    public Applicant addAddress(Long id, Address newAddress) {
        Applicant applicant = findById(id);
        applicant.address = newAddress;
        return this.save(applicant);
    }

    public List<Applicant> getByBranche(String branche) {
        return findAll()
                .stream()
                .filter(a -> a.jobBranche.equalsIgnoreCase(branche))
                .collect(Collectors.toList());
    }
}
