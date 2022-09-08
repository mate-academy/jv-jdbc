package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private static final String ID = "id";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO taxi_service_db.manufacturers(name, country) values(?, ?);";
        String checkIfPresent =
                "SELECT * FROM taxi_service_db.manufacturers WHERE name = ? "
                        + "AND country = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement checkPresence = connection.prepareStatement(checkIfPresent)) {
            //Check if the manufacturer is present in the DB
            /*List<Manufacturer> manufacturers = getAll();
            List<String> manufacturersNames = manufacturers.stream()
                    .map(Manufacturer::getName)
                    .collect(Collectors.toList());
            if (manufacturersNames.contains(manufacturer.getName())) {
                return manufacturer;
            }*/
            //End check

            //another check for manufacturer presence in DB
            checkPresence.setString(1, manufacturer.getName());
            checkPresence.setString(2, manufacturer.getCountry());
            ResultSet rs = checkPresence.executeQuery();
            if (rs.next()) {
                return manufacturer;
            }
            //End check
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + manufacturer.getName() + " from " + manufacturer.getCountry()
                    + " into DB taxi_service_db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerByID =
                "SELECT * FROM taxi_service_db.manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerByID)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(NAME);
                String country = resultSet.getString(COUNTRY);
                return Optional.of(new Manufacturer(id, name, country));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturers =
                "SELECT * FROM taxi_service_db.manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection
                        .prepareStatement(getAllManufacturers)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(NAME);
                String country = resultSet.getString(COUNTRY);
                long id = resultSet.getObject(ID, Long.class);
                manufacturers.add(new Manufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE taxi_service_db.manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createUpdateStatement = connection
                        .prepareStatement(updateRequest)) {
            createUpdateStatement.setString(1, manufacturer.getName());
            createUpdateStatement.setString(2, manufacturer.getCountry());
            createUpdateStatement.setLong(3, manufacturer.getId());
            createUpdateStatement.executeUpdate();
            return get(manufacturer.getId()).orElse(null);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer.getName() + " from " + manufacturer.getCountry()
                    + " with id: " + manufacturer.getId() + " from DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequestByID =
                "UPDATE taxi_service_db.manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createDeleteStatement = connection
                        .prepareStatement(deleteRequestByID)) {
            createDeleteStatement.setLong(1, id);
            return createDeleteStatement.executeUpdate() >= 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: "
                    + id + " from DB", e);
        }
    }
}
