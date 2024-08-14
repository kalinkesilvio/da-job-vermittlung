package at.htl.controller;

import at.htl.entity.Company;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CompanyRepository implements PanacheRepository<Company> {

    @Inject
    EntityManager em;

    @Inject
    AddressRepository addressRepository;

    @Transactional
    public Company save(Company company) {
        return em.merge(company);
    }

    public Company getCompanyById(Long id) {
        return findById(id);
    }

    @Transactional
    public boolean remove(Long id) {
        return deleteById(id);
    }

    @Transactional
    public Company addAddressById(Long companyId, Long addressId) {
        Company company = findById(companyId);
        company.setAddress(addressRepository.findById(addressId));
        return this.save(company);
    }
}
