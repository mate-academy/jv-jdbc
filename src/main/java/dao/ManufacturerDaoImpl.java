package dao;

import exceptions.DataProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import models.Manufacturer;
import util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String request = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet resultSet = createManufacturerStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("cannot insert manufacturer into db."
                    + "Manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String request = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement requestStatement = connection
                        .prepareStatement(request)) {
            requestStatement.setLong(1, id);
            ResultSet resultSet = requestStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("cannot get manufacturer from db where id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> list = new ArrayList<>();
        String preparedRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(preparedRequest)) {
            ResultSet resultSet = getAllStatement
                    .executeQuery();
            while (resultSet.next()) {
                list.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("cannot get all manufacturers from db", e);
        }
        return list;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateFormatStatement = connection
                        .prepareStatement(updateRequest)) {
            updateFormatStatement.setString(1, manufacturer.getName());
            updateFormatStatement.setString(2, manufacturer.getCountry());
            updateFormatStatement.setLong(3, manufacturer.getId());
            updateFormatStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("cannot update manufacturer from db. "
                    + "Manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement = connection
                        .prepareStatement(deleteRequest)) {
            createFormatStatement.setLong(1, id);
            return createFormatStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("cannot delete manufacturer from db by "
                    + "id=" + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Long id = resultSet.getObject("id", Long.class);
        return new Manufacturer(id, name, country);
    }
}
