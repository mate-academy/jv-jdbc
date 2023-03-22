package mate.jdbc.dao.impl;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.util.ConnectionUtil;
import model.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String INSERT_INTO_MANUFACTURES_MANUFACTURER_VALUES =
            "INSERT INTO manufactures(name, country) values(?, ?);";
   private static final String UPDATE_MANUFACTURES_SET_IS_DELETED_TRUE_WHERE_ID =
           "UPDATE manufactures SET is_deleted = true where id = ?";
    private static final String UPDATE_MANUFACTURES_WHERE_ID =
            "UPDATE manufactures SET(name, country) WHERE id = ?, values(?, ?)"
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
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery("SELECT * FROM manufactures where is_deleted = false AND id = ?");
            while (resultSet != null && resultSet.next()) {
                String name = resultSet.getString(COLUMN_NAME);
                String country = resultSet.getString(COLUMN_COUNTRY);
                Long idFromDb = resultSet.getObject(COLUMN_ID, Long.class);
                manufacturer = new Manufacturer();
                manufacturer.setId(idFromDb);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't get manufacturer from db", exception);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery("SELECT * FROM manufactures where is_deleted = false");
            while (resultSet != null && resultSet.next()) {
                String name = resultSet.getString(COLUMN_NAME);
                String country = resultSet.getString(COLUMN_COUNTRY);
                Long id = resultSet.getObject(COLUMN_ID, Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all formats from db", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try(Connection connection = ConnectionUtil.getConnection();
        PreparedStatement updateStatement =
                connection.prepareStatement(UPDATE_MANUFACTURES_WHERE_ID,
                        Statement.RETURN_GENERATED_KEYS)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't update fields in DB", exception);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createFormatStatement =
                     connection.prepareStatement(UPDATE_MANUFACTURES_SET_IS_DELETED_TRUE_WHERE_ID,
                             Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setLong(1, id);
            return createFormatStatement.executeUpdate() >= 1;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't insert format to DB", exception);
        }
    }
}
