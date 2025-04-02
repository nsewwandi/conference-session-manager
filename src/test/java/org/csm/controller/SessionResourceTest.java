//package org.csm.controller;
//
//import io.dropwizard.testing.junit5.ResourceExtension;
//import org.csm.beans.Session;
//import org.csm.dao.SessionDAO;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SessionResourceTest {
//
//    @Mock
//    private SessionDAO sessionDAO;  // Mock SessionDAO
//
//    private SessionResource resource;  // Resource that depends on SessionDAO
//    private ResourceExtension resources;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        resource = new SessionResource(sessionDAO);
//        resources = ResourceExtension.builder()
//                .addResource(resource)
//                .build();
//    }
//
//    @Test
//    void testCreateSession() {
//        Session session = new Session();
//        session.setTitle("Session 1");
//        session.setDescription("Description of Session 1");
//        session.setSpeaker("Speaker 1");
//        session.setFileUrl("http://example.com/session1");
//
//        // Mock DAO behavior
//        when(sessionDAO.insert(session)).thenReturn(1L);
//        when(sessionDAO.findById(1L)).thenReturn(session);
//
//        Response response = resources.target("/sessions")
//                .request()
//                .post(Entity.entity(session, MediaType.APPLICATION_JSON));
//
//        assertEquals(200, response.getStatus());
//        assertNotNull(response.getEntity());
//    }
//
//    @Test
//    void testGetSession() {
//        long sessionId = 1L;
//        Session session = new Session();
//        session.setId(sessionId);
//        session.setTitle("Session 1");
//
//        // Mock DAO behavior
//        when(sessionDAO.findById(sessionId)).thenReturn(session);
//
//        Response response = resources.target("/sessions/" + sessionId)
//                .request()
//                .get();
//
//        assertEquals(200, response.getStatus());
//        assertEquals(session, response.getEntity());
//    }
//
//    @Test
//    void testGetAllSessions() {
//        List<Session> sessions = new ArrayList<>();
//        Session session1 = new Session();
//        session1.setTitle("Session 1");
//        sessions.add(session1);
//
//        // Mock DAO behavior
//        when(sessionDAO.findAll(5, 5)).thenReturn(sessions);
//
//        Response response = resources.target("/sessions")
//                .request()
//                .get();
//
//        assertEquals(200, response.getStatus());
//        assertFalse(((List<Session>) response.getEntity()).isEmpty());
//    }
//
//    @Test
//    void testUpdateSession() {
//        long sessionId = 1L;
//        Session session = new Session();
//        session.setId(sessionId);
//        session.setTitle("Updated Title");
//
//        // Mock DAO behavior
//        when(sessionDAO.findById(sessionId)).thenReturn(session);
//
//        Response response = resources.target("/sessions/" + sessionId)
//                .request()
//                .put(Entity.entity(session, MediaType.APPLICATION_JSON));
//
//        assertEquals(200, response.getStatus());
//    }
//
//    @Test
//    void testDeleteSession() {
//        long sessionId = 1L;
//        doNothing().when(sessionDAO).delete(sessionId); // Mock delete method
//
//        Response response = resources.target("/sessions/" + sessionId)
//                .request()
//                .delete();
//
//        assertEquals(204, response.getStatus());
//    }
//}
