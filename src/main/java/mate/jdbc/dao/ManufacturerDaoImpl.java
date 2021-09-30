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
import mate.jdbc.custromexception.MyCustomException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new MyCustomException("Wasn't added a new instance with parameters:"
                    + System.lineSeparator()
                    + manufacturer.toString(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getInstanceManufacturersStatement = connection.prepareStatement(
                        getManufacturerRequest)) {
            getInstanceManufacturersStatement.setLong(1, id);
            ResultSet resultSet = getInstanceManufacturersStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = createManufacturerFromDataDB(resultSet);
            } else {
                throw new MyCustomException(("The instance with id = "
                        + id + " doesn't exists!"));
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new MyCustomException("Can't get an instance from DB with id = " + id, e);
        } catch (NullPointerException | NoSuchElementException e) {
            throw new MyCustomException("The instance with id = "
                    + id + " doesn't exists!", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = createManufacturerFromDataDB(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new MyCustomException("Can't get all manufactures from DB!", e);
        }

        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers "
                        + "SET name = ?, country = ? "
                        + "WHERE id = ? AND is_deleted = false";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement updateManufacturerStatement =
                    connection.prepareStatement(updateManufacturerRequest);
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();

        } catch (SQLException e) {
            throw new MyCustomException("Wasn't updated the instance in DB with parameters:"
                    + System.lineSeparator()
                    + manufacturer.toString(), e);
        } catch (NoSuchElementException e) {
            throw new MyCustomException("The instance with id = "
                    + manufacturer.getId() + " doesn't exists!", e);
        }
        Optional<Manufacturer> optional = get(manufacturer.getId());
        if (optional.isPresent()) {
            return get(manufacturer.getId()).get();
        }
        throw new MyCustomException(("The instance with id = "
                + manufacturer.getId() + " doesn't exists!"));
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(deleteManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new MyCustomException("Wasn't deleted the instance in DB with id = " + id, e);
        }
    }

    private Manufacturer createManufacturerFromDataDB(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject(ID, Long.class);
            String name = resultSet.getString(NAME);
            String country = resultSet.getString(COUNTRY);
            return new Manufacturer(id, name, country);
        } catch (SQLException e) {
            throw new MyCustomException(
                    "Data from DB aren't valid to create Manufacturer instance", e);
        }
    }
}
