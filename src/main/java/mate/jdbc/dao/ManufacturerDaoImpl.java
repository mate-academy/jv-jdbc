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
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufactureRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement = connection
                        .prepareStatement(insertManufactureRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKeys = createManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't insert format to DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufactures = new ArrayList<>();
        String getAllManufactureRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                       PreparedStatement getAllManufactureStatement = connection
                               .prepareStatement(getAllManufactureRequest)) {
            ResultSet resultSet = getAllManufactureStatement.executeQuery();
            while (resultSet.next()) {
                allManufactures.add(getManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException(
                    "Can't get all manufacturers from the DB", throwables);
        }
        return allManufactures;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufactureRequest =
                "SELECT * FROM manufacturers WHERE id = ? and is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                       PreparedStatement getManufactureStatement = connection
                               .prepareStatement(getManufactureRequest)) {
            getManufactureStatement.setLong(1, id);
            ResultSet resultSet = getManufactureStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException throwables) {
            throw new DataProcessingException(
                    "Can't get id manufacturers from the DB", throwables);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufactureRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement updateManufactureStatement = connection
                                .prepareStatement(updateManufactureRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufactureStatement.setString(1, manufacturer.getName());
            updateManufactureStatement.setString(2, manufacturer.getCountry());
            updateManufactureStatement.setLong(3, manufacturer.getId());
            updateManufactureStatement.executeUpdate();
            return manufacturer;

        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't update manufactures from the DB", throwables);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureRequest =
                "UPDATE manufacturers SET is_deleted = true " + "WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement deleteManufactureStatement = connection
                                .prepareStatement(deleteManufactureRequest)) {
            deleteManufactureStatement.setLong(1, id);
            return deleteManufactureStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturer from DB, id: "
                    + id, throwables);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get manufacturer from " + resultSet, e);
        }
        return manufacturer;
    }
}
