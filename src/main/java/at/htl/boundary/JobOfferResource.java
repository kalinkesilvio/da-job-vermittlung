package at.htl.boundary;

import at.htl.controller.JobOfferRepository;
import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/joboffer")
public class JobOfferResource {

    @Inject
    JobOfferRepository jobOfferRepository;

    @POST
    @Transactional
    @Path("/create")
    public Response create(JobOffer jobOffer) {
        JobOffer jobOffer1 = jobOfferRepository.save(jobOffer);
        if (jobOffer1 != null) {
            return Response.created(URI.create("company/" + jobOffer1.id)).build();
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
}
