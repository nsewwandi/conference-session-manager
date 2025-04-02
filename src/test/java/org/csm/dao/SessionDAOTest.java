package org.csm.dao;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import org.csm.beans.Session;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionDAOTest {

    private Jdbi jdbi;
    private SessionDAO sessionDAO;

    @BeforeEach
    void setUp() {
        jdbi = mock(Jdbi.class);
        sessionDAO = mock(SessionDAO.class);
    }

    @Test
    void testInsertSession() {
        Session session = new Session();
        session.setTitle("Test Session");
        session.setDescription("Description");
        session.setSpeaker("Speaker");

        when(sessionDAO.insert(session)).thenReturn(1L);

        long sessionId = sessionDAO.insert(session);
        assertEquals(1L, sessionId);
    }

    @Test
    void testFindSessionById() {
        Session session = new Session();
        session.setId(1L);
        session.setTitle("Test Session");

        when(sessionDAO.findById(1L)).thenReturn(session);

        Session result = sessionDAO.findById(1L);
        assertEquals("Test Session", result.getTitle());
    }
}
