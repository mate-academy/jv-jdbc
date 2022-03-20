package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
   private static final int INDEX_ID = 1;
   private static final int INDEX_NAME = 2;
   private static final int INDEX_COUNTRY = 3;

   @Override
   public Manufacturer create(Manufacturer manufacturer) {
      String insertManufacturerRequest = "INSERT INTO manufacturers(`manufacturers.name`, country)"
          + " values(?, ?);";
      try (Connection taxiServiceConnection = ConnectionUtil.getConnection();
           PreparedStatement createManufacturersStatement = taxiServiceConnection.prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
         createManufacturersStatement.setString(INDEX_ID, manufacturer.getName());
         createManufacturersStatement.setString(INDEX_NAME, manufacturer.getCountry());
         createManufacturersStatement.executeUpdate();
         ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
         if (generatedKeys.next()) {
            Long id = generatedKeys.getObject(INDEX_ID, Long.class);
            manufacturer.setId(id);
         }
      } catch (SQLException e) {
         throw new RuntimeException("Can't insert a manufacturer into DB", e);
      }
      return manufacturer;
   }

   @Override
   public Optional<Manufacturer> get(Long id) {
      String getManufacturerRequest = "SELECT id, `manufacturers.name`, country FROM manufacturers" + " where id = ? and is_deleted = false;";
      try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement getManufacturerStatement = connection.prepareStatement(getManufacturerRequest)) {
         getManufacturerStatement.setLong(INDEX_ID, id);
         ResultSet resultSet = getManufacturerStatement.executeQuery();
         if (resultSet.next()) {
            return Optional.of(createManufacturer(resultSet));
         }
      } catch (SQLException e) {
         throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, e);
      }
      return Optional.empty();
   }

   @Override
   public List<Manufacturer> getAll() {
      List<Manufacturer> allManufacturers = new ArrayList<>();
      try (Connection taxiServiceConnection = ConnectionUtil.getConnection(); Statement getManufacturersStatement = taxiServiceConnection.createStatement()) {
         ResultSet setOfManufacturers = getManufacturersStatement.executeQuery("SELECT * FROM taxi_service.manufacturers WHERE is_deleted = 0");
         while (setOfManufacturers.next()) {
            String manufacturerName = setOfManufacturers.getString("manufacturers.name");
            String manufacturerCountry = setOfManufacturers.getString("country");
            Long id = setOfManufacturers.getObject("id", Long.class);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(manufacturerName);
            manufacturer.setCountry(manufacturerCountry);
            manufacturer.setId(id);
            allManufacturers.add(manufacturer);
         }
      } catch (SQLException e) {
         throw new RuntimeException("Can't get all manufactures from DB", e);
      }
      return allManufacturers;
   }

   @Override
   public Manufacturer update(Manufacturer manufacturer) {
      String updateRequest = "UPDATE manufacturers SET `manufacturers.name` = ?, country = ? where id = ?" + " and is_deleted = false;";
      try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement updateManufacturerStatement = connection.prepareStatement(updateRequest)) {
         updateManufacturerStatement.setString(INDEX_ID, manufacturer.getName());
         updateManufacturerStatement.setString(INDEX_NAME, manufacturer.getCountry());
         updateManufacturerStatement.setLong(INDEX_COUNTRY, manufacturer.getId());
         updateManufacturerStatement.executeUpdate();
      } catch (SQLException e) {
         throw new DataProcessingException("Can't update manufacturer in DB,"
             + " manufacturer: " + manufacturer, e);
      }
      return manufacturer;
   }

   @Override
   public boolean delete(Long id) {
      String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
      try (Connection taxiServiceConnection = ConnectionUtil.getConnection();
           PreparedStatement deleteManufacturerStatement = taxiServiceConnection
               .prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
         deleteManufacturerStatement.setLong(INDEX_ID, id);
         return deleteManufacturerStatement.executeUpdate() >= 1;
      } catch (SQLException e) {
         throw new RuntimeException("Can't insert a manufacturer into DB", e);
      }
   }

   private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
      Long id = resultSet.getObject(1, Long.class);
      String name = resultSet.getString("name");
      String country = resultSet.getString("country");
      Manufacturer manufacturer = new Manufacturer();
      manufacturer.setId(id);
      manufacturer.setName(name);
      manufacturer.setCountry(country);
      return manufacturer;
   }
}
