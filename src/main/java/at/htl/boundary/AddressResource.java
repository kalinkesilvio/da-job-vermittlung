package at.htl.boundary;

import at.htl.controller.AddressRepository;
import at.htl.entity.Address;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

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

    @GET
    @Path("/getById_partial/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById_partial(@PathParam("id") Long id) {
        Address address = addressRepository.findById(id);

        if (address != null) {
            JsonObject o = Json.createObjectBuilder()
                    .add("country", "Testland")
                    .add("state", "Teststaat")
                    .add("city", address.getCity())
                    .add("zipno", "" + address.getZipNo())
                    .build();
            return Response.ok(o.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(addressRepository.listAll()).build();
    }

    @POST
    @Path("/")
    public Response create(Address address) {
        Address address1 = addressRepository.save(address);
        if (address1 != null) {
            return Response.ok(address1).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/update")
    @Transactional
    public Response update(Address address) {
        return Response.ok(addressRepository.save(address)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = addressRepository.deleteById(id);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
