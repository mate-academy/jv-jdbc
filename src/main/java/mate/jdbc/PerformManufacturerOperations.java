package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class PerformManufacturerOperations {
    public void doAction(ManufacturerDao manufacturerDao) {
        Manufacturer manufacturerHonda = new Manufacturer();
        manufacturerHonda.setName("Honda");
        manufacturerHonda.setCountry("Japan");
        manufacturerDao.create(manufacturerHonda);

        Manufacturer manufacturerBmw = new Manufacturer();
        manufacturerBmw.setName("BMW");
        manufacturerBmw.setCountry("Germany");
        manufacturerDao.create(manufacturerBmw);

        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();

        Manufacturer manufacturerMersUpdate = new Manufacturer();
        manufacturerMersUpdate.setId(2L);
        manufacturerMersUpdate.setName("Mercedes");
        manufacturerMersUpdate.setCountry("Germany");
        manufacturerDao.update(manufacturerMersUpdate);

        boolean delete = manufacturerDao.delete(2L);
    }
}
