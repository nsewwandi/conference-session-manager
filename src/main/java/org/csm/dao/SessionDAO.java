package org.csm.dao;

import org.csm.beans.Session;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.*;
import java.util.List;

@RegisterBeanMapper(Session.class)
public interface SessionDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS sessions (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), description TEXT, speaker VARCHAR(255), fileUrl VARCHAR(255))")
    void createTable();

    @SqlUpdate("INSERT INTO sessions (title, description, speaker, fileUrl) VALUES (:title, :description, :speaker, :fileUrl)")
    @GetGeneratedKeys
    long insert(@BindBean Session session);

    @SqlQuery("SELECT * FROM sessions WHERE id = :id")
    Session findById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM sessions LIMIT :limit OFFSET :offset")
    List<Session> findAll(@Bind("limit") int limit, @Bind("offset") int offset);

    @SqlUpdate("UPDATE sessions SET title = :title, description = :description, speaker = :speaker, fileUrl = :fileUrl WHERE id = :id")
    void update(@BindBean Session session);

    @SqlUpdate("DELETE FROM sessions WHERE id = :id")
    void delete(@Bind("id") long id);

}
