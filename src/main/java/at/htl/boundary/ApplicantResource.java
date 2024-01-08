package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Address;
import at.htl.entity.Applicant;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/applicant")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return applicantRepository
                .findByIdOptional(id)
                .map(applicant -> Response.ok(applicant).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/getAll")
    public Response getAll() {
        return Response.ok(applicantRepository.listAll()).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return Response.ok(this.applicantRepository.remove(id)).build();
    }


    @POST
    @Transactional
    @Path("/addAddress")
    public Response addAddress(Applicant applicant, Address address) {
        applicantRepository.addAddress(applicant.id, address);
        return Response.created(URI.create("applicant/" + applicant.id)).build();
    }

    @GET
    @Path("getAllByBranche/{branche}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getAllByBranche(@PathParam("branche") String branche) {
        return Response.ok(applicantRepository.getByBranche(branche)).build();
    }
}
