package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class PerformManufacturerOperations {
    public void doAction(ManufacturerDao manufacturerDao) {
        Manufacturer manufacturerHonda = new Manufacturer();
        manufacturerHonda.setName("Honda");
        manufacturerHonda.setCountry("Japan");
        manufacturerDao.create(manufacturerHonda);

        System.out.println(manufacturerDao.get(8L));
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        Manufacturer manufacturerMersUpdate = new Manufacturer();
        manufacturerMersUpdate.setId(9L);
        manufacturerMersUpdate.setName("Mercedes");
        manufacturerMersUpdate.setCountry("Germany");
        manufacturerDao.update(manufacturerMersUpdate);

        System.out.println(manufacturerDao.delete(2L));
    }
}
