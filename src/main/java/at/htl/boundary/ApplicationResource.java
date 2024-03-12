package at.htl.boundary;

import at.htl.controller.ApplicationRepository;
import at.htl.entity.Application;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("application")
public class ApplicationResource {

    @Inject
    ApplicationRepository applicationRepository;


    @POST
    @Path("/create")
    @Transactional
    public Response create(Application application) {
        Application application1 = applicationRepository.save(application);
        if (application1 != null) {
            return Response.created(URI.create("application/" + application1.id)).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/getByJobOfferId/{id}")
    public Response getByJobOffer(@PathParam("id") Long id) {
        List<Application> applications = applicationRepository.findByJobOfferId(id);
        if (!applications.isEmpty()) {
            return Response.ok(applications).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/getByApplicantId/{id}")
    public Response getByApplicantId(@PathParam("id") Long id) {
        List<Application> applications = applicationRepository.findByApplicantId(id);
        if (!applications.isEmpty()) {
            return Response.ok(applications).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
