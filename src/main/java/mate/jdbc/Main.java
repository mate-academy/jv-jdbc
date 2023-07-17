package mate.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            ManufacturerDao manufacturerDao = new ManufacturerDaoImpl(connection);

            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName("Toyota");
            manufacturer.setCountry("Japan");

            manufacturerDao.create(manufacturer);

            Optional<Manufacturer> retrievedManufacturer = manufacturerDao
                    .get(manufacturer.getId());
            retrievedManufacturer.ifPresent(System.out::println);

            List<Manufacturer> allManufacturers = manufacturerDao.getAll();
            allManufacturers.forEach(System.out::println);

            manufacturer.setName("Updated Manufacturer");
            manufacturerDao.update(manufacturer);

            boolean isDeleted = manufacturerDao.delete(manufacturer.getId());
            System.out.println("Deletion successful: " + isDeleted);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to connect to the database.", e);
        }
    }
}
