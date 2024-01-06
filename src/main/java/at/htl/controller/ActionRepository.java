package at.htl.controller;

import at.htl.entity.Action;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActionRepository implements PanacheRepository<Action> {
}
