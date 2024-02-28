package at.htl.boundary;

import at.htl.controller.JobOfferRepository;
import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/joboffer")
public class JobOfferResource {

    @Inject
    JobOfferRepository jobOfferRepository;

    @POST
    @Path("/create")
    @Transactional
    public Response create(JobOffer jobOffer) {
        JobOffer jobOffer1 = jobOfferRepository.save(jobOffer);
        if (jobOffer1 != null) {
            return Response.created(URI.create("joboffer/" + jobOffer1.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        JobOffer jobOffer = jobOfferRepository.findById(id);
        if (jobOffer != null) {
            return Response.ok(jobOffer).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/getAll")
    public Response getAll() {
        return Response.ok(jobOfferRepository.listAll()).build();
    }

    @GET
    @Path("/getByStringPartial/{partial}")
    public Response getByStringPartial(@PathParam("partial") String partial) {
        List<JobOffer> jobOffers = new ArrayList<>(jobOfferRepository.getJobOffersWithPartialString(partial));

        if (!jobOffers.isEmpty()) {
            return Response.ok(jobOffers).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("getRandomJobOffers/{quantity}")
    public Response getRandomJobOffers(@PathParam("quantity") int quantity) {
        List<JobOffer> randomOffers = jobOfferRepository.getRandomJobOffers(quantity);
        if (!randomOffers.isEmpty()) {
            return Response.ok(randomOffers).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("getJobOfferByCategory/{category}")
    public Response getJobOfferByCategory(@PathParam("category") String category) {
        List<JobOffer> jobOffers = jobOfferRepository.getByCategory(category);
        if (!jobOffers.isEmpty()) {
            return Response.ok(jobOffers).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("getByCompanyId/{companyId}")
    public Response getJobOfferByCompany(@PathParam("companyId") Long companyId) {
        List<JobOffer> jobOffers = jobOfferRepository.getByCompany(companyId);
        if (!jobOffers.isEmpty()) {
            return Response.ok(jobOffers).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("update")
    public Response update(JobOffer jobOffer) {
        return Response.ok(jobOfferRepository.getEntityManager().merge(jobOffer)).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = jobOfferRepository.deleteById(id);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
