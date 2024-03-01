package at.htl.controller;

import at.htl.entity.Action;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ActionRepository implements PanacheRepository<Action> {

    @Inject
    EntityManager em;

    @Transactional
    public Action save(Action action) {
        return em.merge(action);
    }

}
