package at.htl.boundary;

import at.htl.controller.AddressRepository;
import at.htl.entity.Address;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("address")
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
    @Path("/create")
    public Response create(Address address) {
        addressRepository.save(address);
        return Response.created(URI.create("address/" + address.id)).build();
    }
}
