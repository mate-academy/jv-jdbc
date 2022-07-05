package mate.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.JdbcProcessorContext;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Objects.requireNonNull(manufacturer, "Manufacturer can't be null");
        String insertManufacturerQuery = "INSERT INTO manufacturers(name,country) VALUES(?,?);";
        Object[] sqlBindVariables = new Object[]{manufacturer.getName(), manufacturer.getCountry()};
        return (Manufacturer) new JdbcProcessorContext().processJdbc(
                insertManufacturerQuery, sqlBindVariables, statement -> {
                    try {
                        statement.executeUpdate();
                        ResultSet generatedKeys = statement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            Long id = generatedKeys.getObject(1, Long.class);
                            manufacturer.setId(id);
                        }
                        return manufacturer;
                    } catch (SQLException e) {
                        throw new DataProcessingException(
                                "Can't insert manufacturer " + manufacturer, e);
                    }
                });
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerQuery =
                "SELECT * FROM manufacturers WHERE is_deleted = false AND manufacturer_id = ?;";
        Object[] sqlBindVariables = new Object[]{id};
        Manufacturer receivedManufacturer = (Manufacturer) new JdbcProcessorContext().processJdbc(
                selectManufacturerQuery, sqlBindVariables, statement -> {
                    try {
                        ResultSet resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            return convertResultSetToManufacturer(resultSet);
                        }
                        return null;
                    } catch (SQLException e) {
                        throw new DataProcessingException("Can't get manufacturer by id " + id, e);
                    }
                });
        return Optional.ofNullable(receivedManufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        ArrayList<Manufacturer> allManufacturers = new ArrayList<>();
        String selectAllManufacturersQuery =
                "SELECT * FROM manufacturers WHERE is_deleted = false;";
        Object[] sqlBindVariables = new Object[0];
        new JdbcProcessorContext().processJdbc(selectAllManufacturersQuery, sqlBindVariables,
                statement -> {
                    try {
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            allManufacturers.add(convertResultSetToManufacturer(resultSet));
                        }
                        return allManufacturers;
                    } catch (SQLException e) {
                        throw new DataProcessingException("Can't get all manufacturers from DB", e);
                    }
                });
        return allManufacturers;
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) {
        Objects.requireNonNull(manufacturer, "Manufacturer can't be null");
        String updateManufacturerQuery =
                "UPDATE manufacturers SET name = ?, country = ? "
                        + "WHERE is_deleted = false and manufacturer_id = ?;";
        Object[] sqlBindVariables = new Object[]{
                manufacturer.getName(), manufacturer.getCountry(), manufacturer.getId()};
        Manufacturer updatedManufacturer = (Manufacturer) new JdbcProcessorContext().processJdbc(
                updateManufacturerQuery, sqlBindVariables, statement -> {
                    try {
                        if (statement.executeUpdate() > 0) {
                            return manufacturer;
                        }
                        return null;
                    } catch (SQLException e) {
                        throw new DataProcessingException(
                                "Can't update manufacturer " + manufacturer + " in DB", e);
                    }
                });
        return Optional.ofNullable(updatedManufacturer);
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerQuery =
                "UPDATE manufacturers SET is_deleted = true WHERE manufacturer_id = ?;";
        Object[] sqlBindVariables = new Object[]{id};
        return (boolean) new JdbcProcessorContext().processJdbc(
                deleteManufacturerQuery, sqlBindVariables, statement -> {
                    try {
                        return statement.executeUpdate() > 0;
                    } catch (SQLException e) {
                        throw new DataProcessingException(
                                "Can't delete manufacturer by id " + id + " from DB", e);
                    }
                });
    }

    private Manufacturer convertResultSetToManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("manufacturer_id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
