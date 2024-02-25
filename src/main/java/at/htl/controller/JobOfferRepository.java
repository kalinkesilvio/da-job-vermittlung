package at.htl.controller;

import at.htl.entity.JobOffer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class JobOfferRepository implements PanacheRepository<JobOffer> {


    @Inject
    EntityManager em;

    @Transactional
    public JobOffer save(JobOffer jobOffer) {
        return em.merge(jobOffer);
    }

    @Transactional
    public JobOffer saveWithReturn(JobOffer jobOffer) {
        return em.merge(jobOffer);
    }

    public List<JobOffer> getJobOffersWithPartialString(String partial) {
        return findAll()
                .stream()
                .filter(j -> j.title.contains(partial)
                        || j.category.contains(partial))
                .collect(Collectors.toList());
    }

    public List<JobOffer> getRandomJobOffers(int quantity) {

        Random random = new Random();

        List<Long> ids = findAll()
                .stream()
                .map(jo -> jo.id)
                .toList();

        if (ids.size() < quantity) {
            quantity = ids.size();
        } else if (quantity == 0) {
            return List.of();
        }

        List<JobOffer> filteredJobOffers = new LinkedList<>();

        do {
            filteredJobOffers.add(findById(ids.get(random.nextInt(ids.size()))));

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


    public List<JobOffer> getByCategory(String category) {
        return em.createQuery(
                "SELECT j FROM JobOffer j WHERE LOWER(j.category) = LOWER(?1)", JobOffer.class
            )
                .setParameter(1, category)
                .getResultList();
    }
}
