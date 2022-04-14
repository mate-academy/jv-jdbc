package model.DAO;

import mate.jdbc.lib.Dao;

import java.util.List;
import java.util.Optional;

@Dao
public interface DAO<DOMAIN> {
    DOMAIN create(DOMAIN manufacturer);

    Optional<DOMAIN> get(Long id);

    List<DOMAIN> getAll();

    DOMAIN update(DOMAIN manufacturer);

    boolean delete(Long id);
}
