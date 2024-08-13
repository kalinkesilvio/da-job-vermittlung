package at.htl.boundary;

import at.htl.controller.ActionRepository;
import at.htl.entity.Action;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("action")
public class ActionResource {

    @Inject
    ActionRepository actionRepository;

    @POST
    @Transactional
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
    public Response getAll() {
        return Response.ok(actionRepository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Action action = actionRepository.findById(id);
        if (action.isPersistent()) {
            return Response.ok(action).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = actionRepository.deleteById(id);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
