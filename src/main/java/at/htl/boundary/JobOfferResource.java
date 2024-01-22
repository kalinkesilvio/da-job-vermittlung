package at.htl.boundary;

import at.htl.controller.JobOfferRepository;
import at.htl.entity.Company;
import at.htl.entity.JobOffer;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;

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
}
