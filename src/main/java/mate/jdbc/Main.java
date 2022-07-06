package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        //ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer peugeot = new Manufacturer("Peugeot", "France");
        Manufacturer citroen = new Manufacturer("Citroen", "France");
        Manufacturer newPeugeot = manufacturerDao.create(peugeot);
        Manufacturer newCitroen = manufacturerDao.create(citroen);
        boolean deletedPeugeot = manufacturerDao.delete(newPeugeot.getId());
        newCitroen.setCountry("Spain");
        newCitroen = manufacturerDao.update(newCitroen);
        Optional<Manufacturer> dbManufacturer = manufacturerDao.get(newCitroen.getId());
        System.out.println(dbManufacturer.orElse(newPeugeot).getName());
        List<Manufacturer> data = manufacturerDao.getAll();
        System.out.println(data.size());
    }
}
