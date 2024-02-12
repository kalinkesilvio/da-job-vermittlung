package at.htl.boundary;

import at.htl.controller.ActionRepository;
import at.htl.entity.Action;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("action")
public class ActionResource {

    @Inject
    ActionRepository actionRepository;

    @POST
    public Response create(Action action) {
        Action savedAction = actionRepository.save(action);
        if (savedAction != null) {
            return Response.created(URI.create("action/" + savedAction.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
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
}
