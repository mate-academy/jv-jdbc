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
import mate.jdbc.util.ConnectionUtil;
import model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String INSERT_INTO_MANUFACTURES_MANUFACTURER_VALUES =
            "INSERT INTO manufactures(name, country) VALUES(?, ?);";
    private static final String UPDATE_MANUFACTURES_SET_IS_DELETED_TRUE_WHERE_ID =
            "UPDATE manufactures  SET is_deleted = true WHERE id = ?";
    private static final String UPDATE_MANUFACTURES_WHERE_ID =
            "UPDATE manufactures SET name = ?, country = ? WHERE id = ? AND is_deleted = false";
    private static final String GET_MANUFACTURE_WHERE_ID =
            "SELECT * FROM manufactures WHERE is_deleted = false AND id = ?";
    private static final String GET_ALL_MANUFACTURES_WHERE_IS_DELETED_FALSE =
            "SELECT * FROM manufactures WHERE is_deleted = false";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_ID = "id";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(INSERT_INTO_MANUFACTURES_MANUFACTURER_VALUES,
                             Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, manufacturer.getName());
            createFormatStatement.setString(2, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't insert format to DB", exception);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacture = connection
                        .prepareStatement(GET_MANUFACTURE_WHERE_ID)) {
            getManufacture.setLong(1, id);
            ResultSet resultSet = getManufacture.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't get manufacturer from db", exception);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers
                    .executeQuery(GET_ALL_MANUFACTURES_WHERE_IS_DELETED_FALSE);
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException exception) {
            throw new DataProcessingException("Couldn't get a list of manufacturers "
                    + "from manufacturers table.", exception);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(UPDATE_MANUFACTURES_WHERE_ID)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't update fields in DB", exception);
        }
        throw new DataProcessingException("Value doesn't exist");
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(
                                UPDATE_MANUFACTURES_SET_IS_DELETED_TRUE_WHERE_ID)) {
            createFormatStatement.setLong(1, id);
            return createFormatStatement.executeUpdate() >= 1;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't insert format to DB", exception);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        Long id = resultSet.getObject("id", Long.class);
        manufacturer.setId(id);
        String name = resultSet.getString("name");
        manufacturer.setName(name);
        String country = resultSet.getString("country");
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
