package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.utility.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers(name,country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufactureStatement
                     = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKeys = createManufactureStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturer;
    }


    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String selectQuery = "SELECT *"
                + " FROM manufacturers"
                + " WHERE is_deleted='FALSE' AND id=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufactureStatement
                     = connection.prepareStatement(selectQuery)
        ) {
            getManufactureStatement.setLong(1, id);
            boolean successes = getManufactureStatement.execute();
            if (!successes) {
                return Optional.empty();
            }
            ResultSet resultSet = getManufactureStatement.getResultSet();

            while (resultSet.next()) {
                manufacturer.setId(resultSet.getLong(1));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(manufacturer);
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
        return false;
    }
}
