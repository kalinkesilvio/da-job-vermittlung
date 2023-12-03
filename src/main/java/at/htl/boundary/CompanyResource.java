package at.htl.boundary;

import at.htl.controller.CompanyRepository;
import at.htl.entity.Applicant;
import at.htl.entity.Company;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/company")
public class CompanyResource {

    @Inject
    CompanyRepository companyRepository;

    @POST
    @Transactional
    @Path("/create")
    public Response create(Company company) {
        companyRepository.save(company);
        return Response.created(URI.create("company/" + company.id)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(companyRepository.getCompanyById(id)).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return Response.ok(this.companyRepository.remove(id)).build();
    }
}
