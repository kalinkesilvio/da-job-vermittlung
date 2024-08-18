package at.htl.boundary;

import at.htl.controller.JobOfferRepository;
import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
            return Response.ok(jobOffer1).build();
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
    @Path("/partial/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById_partial(@PathParam("id") Long id) {
        JobOffer jobOffer = jobOfferRepository.findById(id);
        if (jobOffer != null) {
            JsonObject o = Json.createObjectBuilder()
                    .add("category", jobOffer.getCategory())
                    .add("condition", jobOffer.getCondition())
                    .add("title", jobOffer.getTitle())
                    .add("descr", jobOffer.getDescr())
                    .build();
            return Response.ok(o.toString()).build();
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
    @Transactional
    public Response update(JobOffer jobOffer) {
        return Response.ok(jobOfferRepository.getEntityManager().merge(jobOffer)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = jobOfferRepository.deleteById(id);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
