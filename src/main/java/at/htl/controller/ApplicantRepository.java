package at.htl.controller;

import at.htl.entity.Address;
import at.htl.entity.Applicant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
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
        Applicant applicant = em.find(Applicant.class, id);
        if (applicant != null) {
            em.remove(applicant);
            return true;
        }
        return false;
    }

    @Transactional
    public Applicant addAddress(Long id, Address newAddress) {
        Applicant applicant = findById(id);
        applicant.setAddress(newAddress);
        return this.save(applicant);
    }

    public List<Applicant> getByJobField(String jobField) {
        return findAll()
                .stream()
                .filter(a -> Optional.ofNullable(a.getJobField()).isPresent())
                .filter(a -> a.getJobField().equalsIgnoreCase(jobField))
                .collect(Collectors.toList());
    }
}
