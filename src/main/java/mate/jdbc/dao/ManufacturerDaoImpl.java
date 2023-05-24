package mate.jdbc.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = null;
        String query = "SELECT `id`, `name`, `country` from `taxi_service.manufacturer` "
                + "WHERE `is_deleted` = false;";
        Connection connection = ConnectionUtil.getConnection();
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
