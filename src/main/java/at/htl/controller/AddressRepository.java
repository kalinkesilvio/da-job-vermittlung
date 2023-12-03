package at.htl.controller;

import at.htl.entity.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {

    @Transactional
    public void save(Address address) {
        if (address != null) {
            if (address.isPersistent()) {
                this.delete(address);
            }
            address.persist();
        }
    }

    @Transactional
    public Address saveWithReturn(Address address) {
        return getEntityManager().merge(address);
    }
}
