package mate.jdbc.dao.implementation;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exeptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "insert into manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer ("
                    + manufacturer.getName() + ", "
                    + manufacturer.getCountry()
                    + ")", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "select * from manufacturers where id = ? and is_deleted = false;";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement getByIdStatement = connection.prepareStatement(getByIdRequest)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id = " + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturersStatement = connection.createStatement()) {
            List<Manufacturer> allManufacturers = new ArrayList<>();
            String sqlRequest = "select * from manufacturers;";
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromResultSet(resultSet);
                allManufacturers.add(manufacturer);
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't gat all manufacturers from DB.", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertManufacturerRequest = "update manufacturers set name = ?, country = ? where id = ? and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.setLong(3, manufacturer.getId());
            createManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
