package at.htl.boundary;

import at.htl.controller.AddressRepository;
import at.htl.entity.Address;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/address")
public class AddressResource {

    @Inject
    AddressRepository addressRepository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(addressRepository.findById(id)).build();
    }

    @POST
    @Path("/")
    public Response create(Address address) {
        addressRepository.save(address);
        return Response.created(URI.create("address/" + address.id)).build();
    }

    @PUT
    @Path("/update")
    @Transactional
    public Response update(Address address) {
        return Response.ok(addressRepository.saveWithReturn(address)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = addressRepository.deleteById(id);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
