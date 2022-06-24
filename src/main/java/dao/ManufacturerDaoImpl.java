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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createManufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("cannot insert manufacturer into db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer;
        String request = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement requestStatement = connection
                        .prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            requestStatement.setLong(1, id);
            ResultSet generatedKey = requestStatement.executeQuery();
            if (generatedKey.next()) {
                String name = generatedKey.getString("name");
                String country = generatedKey.getString("country");
                manufacturer = new Manufacturer(id, name, country);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("cannot get manufacturer from db where id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> list = new ArrayList<>();
        try (
                Connection connection = ConnectionUtil.getConnection();
                Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                list.add(manufacturer);
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
                        .prepareStatement(updateRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateFormatStatement.setString(1, manufacturer.getName());
            updateFormatStatement.setString(2, manufacturer.getCountry());
            updateFormatStatement.setLong(3, manufacturer.getId());
            updateFormatStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("cannot update manufacturer from db", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement = connection
                        .prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setLong(1, id);
            return createFormatStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("cannot delete manufacturer from db", e);
        }
    }
}
