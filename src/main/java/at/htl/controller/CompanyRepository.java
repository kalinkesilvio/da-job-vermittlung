package at.htl.controller;

import at.htl.entity.Address;
import at.htl.entity.Applicant;
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
    public void save(Company company) {
        em.persist(company);
    }

    @Transactional
    public Company updateWithReturn(Company company) {
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
        company.address = addressRepository.findById(addressId);
        return this.updateWithReturn(company);
    }
}
