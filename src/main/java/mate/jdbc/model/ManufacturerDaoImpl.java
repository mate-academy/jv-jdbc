package mate.jdbc.model;

import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDaoImpl implements ManufacturerDao{
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
            Statement getAllDateStatement = connection.createStatement()) {
            ResultSet resultSet = getAllDateStatement
                    .executeQuery("SELECT * FROM manufacturers where is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Long id = resultSet.getLong("id");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get all date from db", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerReqest = "INSERT INTO manufacturers(name) values(?);";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement = connection.prepareStatement(insertManufacturerReqest,
                    Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant create and insert manufacturer to  db", e);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer get(Long id) {
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteReqest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createFormatStatement =
                     connection.prepareStatement(deleteReqest, Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, String.valueOf(id));
            return createFormatStatement.executeUpdate() > 1;
        } catch (SQLException e) {
            throw new RuntimeException("Cant create and insert format to  db", e);
        }
    }
}
