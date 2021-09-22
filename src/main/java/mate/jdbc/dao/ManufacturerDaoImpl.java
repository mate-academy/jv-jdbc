package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerStatement =
                "INSERT INTO manufacturers(name, country) VALUES (?, ?);";

        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement =
                    connection.prepareStatement(insertManufacturerStatement,
                            Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getObject(1, Long.class);
                    manufacturer.setId(id);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can insert manufacturer to DB!", e);
            }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerStatement =
                String.format( "SELECT * FROM manufacturers WHERE id = %d AND is_deleted = 0;", id);

        try (Connection connection = ConnectionUtil.getConnection();
             Statement getManufacturerStatement = connection.createStatement();) {
            ResultSet resultSet = getManufacturerStatement.executeQuery(selectManufacturerStatement);
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get information according id from DB!");
        }
        return Optional.empty();
    }
}
