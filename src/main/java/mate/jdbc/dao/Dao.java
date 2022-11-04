package mate.jdbc.dao;

import mate.jdbc.models.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T create(T t);
    Optional<T> get(Long id);
    List<Manufacturer> getALl();
}
