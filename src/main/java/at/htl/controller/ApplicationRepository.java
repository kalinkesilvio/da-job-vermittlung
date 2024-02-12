package at.htl.controller;

import at.htl.entity.Application;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ApplicationRepository implements PanacheRepository<Application> {

    @Inject
    EntityManager em;

    public List<Application> findByJobOfferId(Long id) {
        return em.createQuery(
                "SELECT a FROM Application a WHERE a.jobOffer.id = ?1", Application.class
                )
                .setParameter(1, id)
                .getResultList();
    }
}
