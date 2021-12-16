package jdbc.hw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jdbc.hw.lib.Dao;
import jdbc.hw.model.Manufacturer;
import jdbc.hw.util.ConnectionUtil;
import lombok.extern.log4j.Log4j2;

@Dao
@Log4j2
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String CREATE_REQ =
            "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
    private static final String MF_SELECT_BY_ID_REQ =
            "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
    private static final String ALL_MF_REQ =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
    private static final String UPDATE_MF_REQ =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE";
    private static final String DEL_MF_REQ =
            "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        log.info("Manufacturer [create] method called for {}", manufacturer);

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createMfStatement =
                        connection.prepareStatement(CREATE_REQ, Statement.RETURN_GENERATED_KEYS)) {
            createMfStatement.setString(1, manufacturer.getName());
            createMfStatement.setString(2, manufacturer.getCountry());
            createMfStatement.executeUpdate();
            ResultSet generatedKeys = createMfStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new entry to [manufacturers] for "
                        + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        log.info("Manufacturer [get] method was called for id {}", id);

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectMfByIdStatement =
                        connection.prepareStatement(MF_SELECT_BY_ID_REQ)) {
            selectMfByIdStatement.setLong(1, id);
            ResultSet resultSet = selectMfByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createMf(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't obtain data from [manufacturers] "
                    + "SQL database for id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        log.info("Manufacturer [getAll] method was called");
        List<Manufacturer> allManufacturers = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement allMfStatement =
                        connection.prepareStatement(ALL_MF_REQ)) {
            ResultSet resultSet = allMfStatement.executeQuery();

            while (resultSet.next()) {
                allManufacturers.add(createMf(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't inquiry [manufacturers] SQL database", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        log.info("Manufacturer [update] method was called for manufacturer {}", manufacturer);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateMfStatement =
                        connection.prepareStatement(UPDATE_MF_REQ,
                                Statement.RETURN_GENERATED_KEYS)) {

            updateMfStatement.setString(1, manufacturer.getName());
            updateMfStatement.setString(2, manufacturer.getCountry());
            updateMfStatement.setLong(3, manufacturer.getId());
            updateMfStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update entry from [manufacturers] SQL "
                    + "database for manufacturer" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        log.info("Manufacturer [delete] method was called for id {}", id);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(DEL_MF_REQ, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);

            return deleteManufacturerStatement.executeUpdate() >= 1;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete entry from [manufacturers] "
                    + "SQL database for id: " + id, e);
        }
    }

    private Manufacturer createMf(ResultSet resultSet) throws SQLException {
        return Manufacturer.builder()
                .id(resultSet.getObject("id", Long.class))
                .name(resultSet.getString("name"))
                .country(resultSet.getString("country"))
                .build();
    }
}
