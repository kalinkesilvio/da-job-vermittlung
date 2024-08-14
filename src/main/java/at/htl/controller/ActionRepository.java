package at.htl.controller;

import at.htl.entity.Action;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ActionRepository implements PanacheRepositoryBase<Action, Long> {

    @Inject
    EntityManager em;

    public Action save(Action action) {
        return em.merge(action);
    }

}
