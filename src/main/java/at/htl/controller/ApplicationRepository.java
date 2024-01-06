package at.htl.controller;

import at.htl.entity.Application;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationRepository implements PanacheRepository<Application> {
}
