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
    public Manufacturer creat(Manufacturer manufacturer) {
        String creatQuery = "INSERT INTO manufacturers(name, country) values(?,?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(creatQuery,
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
        String selectByIndexQuery = "SELECT * FROM manufacturers WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement
                        = connection.prepareStatement(selectByIndexQuery)) {

            getByIdStatement.setLong(1, id);
            ResultSet rs = getByIdStatement.executeQuery();

            rs.next();
            String name = rs.getString(INDEX_OF_NAME_COLUMN);
            String country = rs.getString(INDEX_OF_COUNTRY_COLUMN);
            return Optional.ofNullable(new Manufacturer(id, name, country));
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get manufacturer with ID: "
                    + id + " from table manufacturers", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectGetAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> manufacturerArrayList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement =
                        connection.prepareStatement(selectGetAllQuery)) {

            ResultSet rs = getAllStatement.executeQuery();
            while (rs.next()) {
                Long id = rs.getObject(INDEX_OF_ID_COLUMN, Long.class);
                String name = rs.getString(INDEX_OF_NAME_COLUMN);
                String country = rs.getString(INDEX_OF_COUNTRY_COLUMN);
                manufacturerArrayList.add(new Manufacturer(id, name, country));
            }
            return manufacturerArrayList;
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get manufacturers array "
                    + "from table manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());

            if (get(manufacturer.getId()).isPresent()) {
                updateStatement.executeUpdate();
            }
            return manufacturer;

        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't update manufacturer: "
                    + manufacturer + " in table manufacturers", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuary = "UPDATE manufacturers SET is_deleted = true WHERE id = ? ";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuary)) {

            deleteStatement.setLong(1, id);
            if (get(id).isPresent()) {
                deleteStatement.executeUpdate();
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't delete manufacturer with ID: "
                    + id + " in table manufacturers", e);
        }
    }
}

