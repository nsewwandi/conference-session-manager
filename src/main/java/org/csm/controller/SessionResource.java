package org.csm.controller;

import org.csm.beans.Session;
import org.csm.dao.SessionDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {
    private final SessionDAO sessionDAO;

    public SessionResource(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
        this.sessionDAO.createTable();
    }

    @POST
    public Response createSession(@Valid Session session) {
        long id = sessionDAO.insert(session);
        return Response.ok(sessionDAO.findById(id)).build();
    }

    @GET
    @Path("/{id}")
    public Response getSession(@PathParam("id") long id) {
        Session session = sessionDAO.findById(id);
        return session != null ? Response.ok(session).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public List<Session> getAllSessions(@QueryParam("limit") @DefaultValue("10") int limit,
                                        @QueryParam("offset") @DefaultValue("0") int offset) {
        return sessionDAO.findAll(limit, offset);
    }

    @PUT
    @Path("/{id}")
    public Response updateSession(@PathParam("id") long id, @Valid Session session) {
        session.setId(id);
        sessionDAO.update(session);
        return Response.ok(sessionDAO.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSession(@PathParam("id") long id) {
        sessionDAO.delete(id);
        return Response.noContent().build();
    }
}
