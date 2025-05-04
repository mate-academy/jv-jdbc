package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class PerformManufacturerOperations {
    public void doAction(ManufacturerDao manufacturerDao) {
        Manufacturer manufacturerHonda = new Manufacturer();
        manufacturerHonda.setName("Honda");
        manufacturerHonda.setCountry("Japan");
        Manufacturer manufacturer = manufacturerDao.create(manufacturerHonda);

        System.out.println(manufacturerDao.get(manufacturer.getId()));

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println(manufacturerDao.delete(manufacturer.getId()));
    }
}
