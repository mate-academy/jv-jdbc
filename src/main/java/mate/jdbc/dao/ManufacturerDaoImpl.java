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
import mate.jdbc.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    public ManufacturerDaoImpl() {
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sql = "INSERT INTO manufacturers "
                + "(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer"
                    + manufacturer.toString() + "to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String sql = "select * from manufacturers where id = ? and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                manufacturer.setId(rs.getLong(1));
                manufacturer.setCountry(rs.getString(2));
                manufacturer.setName(rs.getString(3));
            }
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer for id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturersFromTable = new ArrayList<>();
        String sql = "select * from manufacturers where is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                manufacturersFromTable.add(new Manufacturer(rs.getLong(1), rs.getString(2),
                        rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufactures from DB", e);
        }
        return manufacturersFromTable;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sql = "update manufacturers SET name = ?, "
                + "country = ? where id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return manufacturer;
            }
            throw new NoSuchElementException("Manufacturer doesn't exist in DB " + manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "update manufacturers SET is_deleted = true "
                + "where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer in DB. Id = " + id, e);
        }
    }
}
