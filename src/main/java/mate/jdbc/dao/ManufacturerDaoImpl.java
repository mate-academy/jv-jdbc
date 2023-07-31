package mate.jdbc.dao;

import lombok.extern.log4j.Log4j2;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.exceptions.EntityNotFoundException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
@Log4j2
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sql = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, manufacturer.getName());
            stmt.setString(2, manufacturer.getCountry());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    manufacturer.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't insert manufacturer to DB "
                    + manufacturer, ex);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sql = "SELECT * FROM manufacturers WHERE id = ?";
        Manufacturer manufacturer = null;
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getLong("id"));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer by id from DB"
                    + id, ex);
        }
        return Optional.ofNullable(manufacturer);

    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String sql = "SELECT * FROM manufacturers";
        try (Connection conn = ConnectionUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getLong("id"));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all manufacturer from DB ", ex);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sql = "UPDATE manufacturers " +
                "SET name = ?, country = ? " +
                "WHERE id = ?";

        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, manufacturer.getName());
            stmt.setString(2, manufacturer.getCountry());
            stmt.setLong(3, manufacturer.getId());
            int updatedRows = stmt.executeUpdate();
            if(updatedRows > 0) {
                return manufacturer;
            } else {
                throw new EntityNotFoundException("Manufacturer not found with id: " + manufacturer.getId());
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update manufacturer to DB "
                    + manufacturer, ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM manufacturers WHERE id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int deletedRows = stmt.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer by id from DB "
                    + id, ex);
        }
    }
}
