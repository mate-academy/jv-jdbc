package mate.jdbc.dao;
;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.DataBaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement((Query.INSERT.string), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                manufacturer.setId(id);
            } else {
//                throw new DataProcessingException("Insert error!", new Throwable());
                System.out.println("hmm");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer!", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = DataBaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(Query.SELECT_ALL.string + " WHERE id = " + id);
            if (resultSet.next()) {
                return Optional.of(getManufacturerFromResultSet(resultSet));
            }
            throw new DataProcessingException("Can't find manufacturer!", new Throwable());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer!", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = DataBaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            List<Manufacturer> manufacturerList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(Query.SELECT_ALL.string);
            while (resultSet.next()) {
                manufacturerList.add(getManufacturerFromResultSet(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB!", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getLong(1));
        manufacturer.setName(resultSet.getString(2));
        manufacturer.setCountry(resultSet.getString(3));
        return manufacturer;
    }
    private enum Query {
        SELECT_ALL("SELECT * FROM manufacturers"),
        INSERT("INSERT INTO manufacturers (name, country) VALUES (?, ?);");

        private String string;
        Query(String string) {
            this.string = string;
        }
    }
}

