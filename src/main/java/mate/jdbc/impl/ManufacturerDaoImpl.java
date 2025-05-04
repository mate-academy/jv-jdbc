package mate.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exeption.DataProcessingExeption;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int INDEX_OF_ID_COLUMN = 1;
    private static final int INDEX_OF_NAME_COLUMN = 2;
    private static final int INDEX_OF_COUNTRY_COLUMN = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?,?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(INDEX_OF_ID_COLUMN, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;

        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't insert manufacturer: "
                    + manufacturer + " to table manufacturers", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ?";
        Manufacturer manufacturer = null;

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement
                        = connection.prepareStatement(query)) {

            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();

            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);

        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get manufacturer with ID: "
                    + id + " from table manufacturers", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> manufacturerArrayList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement =
                        connection.prepareStatement(query)) {

            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerArrayList.add(getManufacturer(resultSet));
            }
            return manufacturerArrayList;
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get manufacturers array "
                    + "from table manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? "
                + "AND is_deleted = FALSE";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(query)) {

            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());

            updateStatement.executeUpdate();

            return manufacturer;

        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't update manufacturer: "
                    + manufacturer + " in table manufacturers", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ? ";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(query)) {

            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't delete manufacturer with ID: "
                    + id + " in table manufacturers", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(INDEX_OF_ID_COLUMN, Long.class);
        String name = resultSet.getString(INDEX_OF_NAME_COLUMN);
        String country = resultSet.getString(INDEX_OF_COUNTRY_COLUMN);
        return new Manufacturer(id, name, country);
    }
}

