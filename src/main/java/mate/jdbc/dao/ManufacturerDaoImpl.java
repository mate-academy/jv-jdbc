package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import mate.jdbc.util.DataProcessingException;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) value(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturesStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturesStatement.setString(1, manufacturer.getName());
            createManufacturesStatement.setString(2, manufacturer.getCountry());
            createManufacturesStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturesStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject("id", Long.class);
                manufacturer.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Can't insert %s into DB", manufacturer), e);
        }

        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false AND WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufactures =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            getManufactures.setLong(1, id);
            ResultSet resultSet = getManufactures.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Can't get manufacturer with id = %d from DB", id), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturersList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement getManufacturesStatement =
                        connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = getManufacturesStatement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                manufacturersList.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all Manufactures from DB", e);
        }
        return manufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufactureStatement =
                        connection.prepareStatement(updateQuery)) {
            updateManufactureStatement.setString(1, manufacturer.getName());
            updateManufactureStatement.setString(2, manufacturer.getCountry());
            updateManufactureStatement.setLong(3, manufacturer.getId());
            if (updateManufactureStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Can't update %s in DB", manufacturer), e);
        }
        throw new NoSuchElementException(String.format("Can't find %s in DB", manufacturer));
    }

    @Override
    public boolean delete(Long id) {
        String deletedRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ? ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufactures =
                        connection.prepareStatement(deletedRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            deleteManufactures.setLong(1, id);
            return deleteManufactures.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Can't delete manufacturer with id = %d from DB", id), e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
