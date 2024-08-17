package at.htl.controller;

import at.htl.entity.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {

    @Inject
    EntityManager em;

    @Transactional
    public Address save(Address address) {
        return em.merge(address);
    }
}
