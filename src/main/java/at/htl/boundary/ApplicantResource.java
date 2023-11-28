package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Applicant;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/applicant")
public class ApplicantResource {

    @Inject
    ApplicantRepository applicantRepository;

    @POST
    @Transactional
    @Path("/create")
    public Response create(Applicant applicant) {
        applicantRepository.save(applicant);
        return Response.created(URI.create("applicant/" + applicant.id)).build();
    }

}
