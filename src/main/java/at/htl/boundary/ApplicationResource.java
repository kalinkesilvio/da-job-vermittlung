package at.htl.boundary;

import at.htl.controller.ApplicationRepository;
import at.htl.entity.Application;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
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
            return Response.ok(application1).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/update")
    @Transactional
    public Response update(Application application) {
        return Response.ok(applicationRepository.save(application)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id) {
        return Response.ok(applicationRepository.remove(id)).build();
    }

    @GET
    @Path("/getAll")
    public Response getAll() {
        return Response.ok(
                applicationRepository
                        .listAll())
                .build();
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

    @GET
    @Path("/getTimeDifference/{id}")
    public Response getTimeDifferenceDaysHours(@PathParam("id") Long id) {
        LocalDateTime applicationDate = this.applicationRepository.findById(id).getStartDate();
        if (applicationDate != null) {
            long hours = Duration.between(LocalDateTime.now(), applicationDate).toHours();
            int days = (int) hours / 24;
            int modHours = (int) hours % 24;
            JsonObject o = Json.createObjectBuilder()
                    .add("days", days)
                    .add("hours", modHours)
                    .build();
            return Response.ok(o.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
