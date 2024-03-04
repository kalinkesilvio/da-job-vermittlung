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

    @PUT
    @Path("update/{id}")
    public Response updateById(@PathParam("id") Long id, Address address) {
        return addressRepository
                .findByIdOptional(id)
                .map(a -> {
                            a.city = address.city;
                            a.country = address.country;
                            a.state = address.state;
                            a.street = address.street;
                            a.streetNo = address.streetNo;
                            a.zipNo = address.zipNo;
                            return Response.ok(a).build();
                        })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
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
