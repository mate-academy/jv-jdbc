package mate.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) throws SQLException {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        manufacturer.setName("Honda");
        manufacturer.setCountry("Japan");
        manufacturer = manufacturerDao.create(manufacturer);
        Optional<Manufacturer> niceManufacturer = manufacturerDao.get(manufacturer.getId());

        if (niceManufacturer.isPresent()) {
            System.out.println(niceManufacturer.get());
        }

        manufacturer.setName("Opel");
        manufacturer.setCountry("Germany");
        manufacturer = manufacturerDao.create(manufacturer);
        manufacturerDao.delete(manufacturer.getId());

        manufacturer.setName("Ford");
        manufacturer.setCountry("USA");
        manufacturer = manufacturerDao.create(manufacturer);
        manufacturer.setCountry("United States of America");
        manufacturerDao.update(manufacturer);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacture : allManufacturers) {
            System.out.printf("%d,%s,%s" + System.lineSeparator(),
                    manufacture.getId(),
                    manufacture.getName(),
                    manufacture.getCountry());
        }
    }
}
