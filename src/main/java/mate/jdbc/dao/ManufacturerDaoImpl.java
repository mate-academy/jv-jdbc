package mate.jdbc.dao;

import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao{
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(company_name, country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement createManufacturerStatement = connection
                    .prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS);
            createManufacturerStatement.setNString(1,manufacturer.getCompanyName());
            createManufacturerStatement.setNString(2,manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert format to DB",e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String getManufacturersByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerByIdStatement
                     = connection.prepareStatement(getManufacturersByIdRequest)) {
            getManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerByIdStatement
                    .executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        Statement getAllManufacturersStatement;
        try (Connection connection = ConnectionUtil.getConnection()){
            getAllManufacturersStatement = connection.createStatement();
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = FALSE");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String companyName = resultSet.getNString("company_name");
                String country = resultSet.getNString("country");
                Manufacturer manufacturer = new Manufacturer(id,companyName,country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB",e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest
                = "UPDATE manufacturers SET company_name = ?,"
                + " country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerByIdStatement
                     = connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerByIdStatement.setLong(3, manufacturer.getId());
            updateManufacturerByIdStatement.setString(1, manufacturer.getCompanyName());
            updateManufacturerByIdStatement.setString(2, manufacturer.getCountry());

            updateManufacturerByIdStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerByIdRequest
                = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerByIdStatement = connection
                     .prepareStatement(deleteManufacturerByIdRequest)) {
            deleteManufacturerByIdStatement.setLong(1, id);
            return deleteManufacturerByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with this id: " + id, e);
        }
    }
    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String companyName = resultSet.getNString("company_name");
            String country = resultSet.getNString("country");
            return new Manufacturer(id,companyName,country);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer", e);
        }
    }
}
