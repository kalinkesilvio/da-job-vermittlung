package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Applicant;
import at.htl.entity.Company;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.net.URI;

@Path("/company")
public class CompanyResource {

    @Inject
    CompanyRepository companyRepository;

    @POST
    @Transactional
    @Path("/create")
    public Response create(Company company) {
        Company savedCompany = companyRepository.save(company);
        if (savedCompany != null) {
            return Response.ok(savedCompany).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Transactional
    @Path("/update")
    public Response update(Company company) {
        return Response.ok(companyRepository.save(company)).build();
    }

    @PUT
    @Transactional
    @Path("addAddressById/{companyId}/{addressId}")
    public Response addAddress(
            @PathParam("companyId") Long companyId,
            @PathParam("addressId") Long addressId) {
        return Response.ok(
                this.companyRepository.addAddressById(companyId, addressId)
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(companyRepository.getCompanyById(id)).build();
    }

    @GET
    @Path("/getAll")
    public Response getAll() {
        return Response.ok(companyRepository.listAll()).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return Response.ok(this.companyRepository.remove(id)).build();
    }
}
