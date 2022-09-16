package mate.jdbc.dao.impl;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement = connection.prepareStatement(
                     insertQuery, Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, ex);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement = connection.prepareStatement(
                     deleteQuery, Statement.RETURN_GENERATED_KEYS);) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id " + id, ex);
        }
    }
}
