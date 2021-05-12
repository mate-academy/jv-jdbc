package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufactureDaoImpl implements ManufactureDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers"
                + "(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufactureStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            insertManufactureStatement.setString(1, manufacturer.getName());
            insertManufactureStatement.setString(2, manufacturer.getCountry());
            insertManufactureStatement.executeUpdate();
            ResultSet generatedKeys = insertManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert Manufacturer to DB" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers"
                + " WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufactureStatement =
                        connection.prepareStatement(getManufacturerRequest)) {
            getManufactureStatement.setLong(1, id);
            ResultSet resultSet = getManufactureStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = createManufactureInstance(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturesStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturesStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE deleted = false");
            while (resultSet.next()) {
                manufacturers.add(createManufactureInstance(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all information from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufactureRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufactureStatement =
                        connection.prepareStatement(updateManufactureRequest)) {
            updateManufactureStatement.setString(1, manufacturer.getName());
            updateManufactureStatement.setString(2, manufacturer.getCountry());
            updateManufactureStatement.setLong(3, manufacturer.getId());
            updateManufactureStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update Manufacturer in DB " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureRequest = "UPDATE manufacturers SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createDeleteStatement =
                        connection.prepareStatement(deleteManufactureRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createDeleteStatement.setLong(1, id);
            return createDeleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete Manufacturer from DB by id: " + id, e);
        }
    }

    private Manufacturer createManufactureInstance(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getNString("name");
            String country = resultSet.getNString("country");
            return new Manufacturer(id, name, country);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create instance of Manufacture", e);
        }
    }
}
