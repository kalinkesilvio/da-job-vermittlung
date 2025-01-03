package at.htl.controller;

import at.htl.entity.JobOffer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class JobOfferRepository implements PanacheRepository<JobOffer> {


    @Inject
    EntityManager em;

    public JobOffer save(JobOffer jobOffer) {
        return em.merge(jobOffer);
    }

    public List<JobOffer> getJobOffersWithPartialString(String partial) {
        return findAll()
                .stream()
                .filter(j -> j.getTitle().toLowerCase().contains(partial.toLowerCase())
                        || j.getCategory().toLowerCase().contains(partial.toLowerCase())
                        || j.getCompany().getCompanyName().toLowerCase().contains(partial.toLowerCase())
                )
                .collect(Collectors.toList());
    }

    public List<JobOffer> getRandomJobOffers(int quantity) {

        Random random = new Random();

        List<Long> ids = this.jobOfferIds();

        if (ids.size() < quantity) {
            if (ids.isEmpty()) {
                return List.of();
            }
            quantity = ids.size();
        } else if (quantity <= 0) {
            return List.of();
        }

        List<JobOffer> filteredJobOffers = new LinkedList<>();

        do {
            Long randomId = ids.get(random.nextInt(ids.size()));
            filteredJobOffers.add(this.findJobOfferById(randomId));

            filteredJobOffers = filteredJobOffers
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());

        } while (filteredJobOffers.size() < quantity);

        return filteredJobOffers
                .stream()
                .unordered()
                .collect(Collectors.toList());
    }

    public JobOffer findJobOfferById(Long id) {
        return findById(id);
    }

    public List<Long> jobOfferIds() {
        return findAll()
                .stream()
                .map(JobOffer::getId)
                .toList();
    }

    public List<JobOffer> getByCategory(String category) {
        return em.createQuery(
                "SELECT j FROM JobOffer j WHERE LOWER(j.category) = LOWER(?1)", JobOffer.class
            )
                .setParameter(1, category)
                .getResultList();
    }

    public List<JobOffer> getByCompany(Long companyId) {
        return em.createQuery(
                "SELECT j FROM JobOffer j WHERE j.company.id = ?1", JobOffer.class
        )
                .setParameter(1, companyId)
                .getResultList();
    }
}
