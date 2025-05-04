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
import mate.jdbc.utility.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers(name,country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(createQuery,
                                                    Statement.RETURN_GENERATED_KEYS)) {
            setStatementParameters(manufacturer, createManufactureStatement);
            createManufactureStatement.executeUpdate();
            setIdToManufacturerObj(manufacturer, createManufactureStatement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectQuery = "SELECT *"
                + " FROM manufacturers"
                + " WHERE is_deleted=FALSE AND id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufactureStatement =
                        connection.prepareStatement(selectQuery)) {
            getManufactureStatement.setLong(1, id);
            ResultSet resultSet = getManufactureStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String selectAllQuery = "SELECT *"
                + " FROM manufacturers"
                + " WHERE is_deleted= FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturesStatement =
                        connection.prepareStatement(selectAllQuery)) {
            getAllManufacturesStatement.executeQuery();
            ResultSet resultSet = getAllManufacturesStatement.getResultSet();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all manufacturers", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers "
                + "SET name = ?, country= ? "
                + "WHERE is_deleted=FALSE AND id=?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(updateQuery)) {
            setStatementParameters(manufacturer, updateStatement);
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        int changedRowCount;
        String deleteQuery = "UPDATE manufacturers "
                + "SET is_deleted='1'"
                + "WHERE is_deleted=FALSE AND id=?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            changedRowCount = deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete manufacturer by id " + id, e);
        }
        return changedRowCount > 0;
    }

    private Manufacturer getManufacturer(ResultSet resultSet)
            throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(1, Long.class));
        manufacturer.setName(resultSet.getString(2));
        manufacturer.setCountry(resultSet.getString(3));
        return manufacturer;
    }

    private void setStatementParameters(Manufacturer manufacturer, PreparedStatement statement)
            throws SQLException {
        statement.setString(1, manufacturer.getName());
        statement.setString(2, manufacturer.getCountry());
    }

    private void setIdToManufacturerObj(Manufacturer manufacturer, PreparedStatement statement)
            throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        while (generatedKeys.next()) {
            Long id = generatedKeys.getObject(1, Long.class);
            manufacturer.setId(id);
        }
    }
}
