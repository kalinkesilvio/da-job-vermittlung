package at.htl.boundary;

import at.htl.controller.ApplicationRepository;
import at.htl.entity.Application;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("application")
public class ApplicationResource {

    @Inject
    ApplicationRepository applicationRepository;

    @GET
    @Path("/getByJobOfferId/{id}")
    public Response getByJobOffer(@PathParam("id") Long id) {
        List<Application> applications = applicationRepository.findByJobOfferId(id);
        if (!applications.isEmpty()) {
            return Response.ok(applications).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
