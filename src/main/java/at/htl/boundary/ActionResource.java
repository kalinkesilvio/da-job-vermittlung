package at.htl.boundary;

import at.htl.controller.ActionRepository;
import at.htl.entity.Action;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Authenticated
@Path("action")
public class ActionResource {

    @Inject
    ActionRepository actionRepository;

    @POST
    @Transactional
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Action action) {
        Action savedAction = actionRepository.save(action);
        if (savedAction != null) {
            return Response.ok(savedAction).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/getAll")
    @RolesAllowed("admin")
    public Response getAll() {
        return Response.ok(actionRepository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response findById(@PathParam("id") Long id) {
        Action action = actionRepository.findById(id);
        if (action.isPersistent()) {
            return Response.ok(action).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Transactional
    @PermitAll
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = actionRepository.deleteById(id);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
