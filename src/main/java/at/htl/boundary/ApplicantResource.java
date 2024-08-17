package at.htl.boundary;

import at.htl.controller.ApplicantRepository;
import at.htl.entity.Address;
import at.htl.entity.Applicant;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Optional;

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
        Applicant savedApplicant = applicantRepository.save(applicant);
        if (savedApplicant != null) {
            return Response.ok(savedApplicant).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Transactional
    @Path("/update")
    public Response update(Applicant applicant) {
        return Response.ok(applicantRepository.save(applicant)).build();
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


//    @POST
//    @Transactional
//    @Path("/addAddress")
//    public Response addAddress(Applicant applicant, Address address) {
//        applicantRepository.addAddress(applicant.id, address);
//        return Response.created(URI.create("applicant/" + applicant.id)).build();
//    }

    @GET
    @Path("getAllByBranche/{jobField}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getAllByBranche(@PathParam("jobField") String jobField) {
        return Response.ok(applicantRepository.getByBranche(jobField)).build();
    }
}
