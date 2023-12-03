package at.htl.controller;

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

    @Transactional
    public void save(Company company) {
        em.persist(company);
    }

    public Company getCompanyById(Long id) {
        return findById(id);
    }

    @Transactional
    public void remove(Long id) {
        deleteById(id);
    }
}
