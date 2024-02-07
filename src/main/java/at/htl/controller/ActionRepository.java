package at.htl.controller;

import at.htl.entity.Action;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ActionRepository implements PanacheRepository<Action> {

    @Inject
    EntityManager em;

    public Action save(Action action) {
        return em.merge(action);
    }
}
