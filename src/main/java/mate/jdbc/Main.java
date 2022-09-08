package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturersList = manufacturerDao.getAll();
        Manufacturer fiat = manufacturerDao.create(new Manufacturer("Fiat", "Italy"));
        Manufacturer toyota = manufacturerDao.create(new Manufacturer("Toyota", "Japan"));
        Manufacturer ford = manufacturerDao.create(new Manufacturer("Ford", "USA"));
        for (Manufacturer manufacturer : manufacturersList) {
            System.out.println(manufacturer);
        }
        System.out.println("Create manufacturers: " + fiat + "; " + toyota + "; " + ford + ";");
        System.out.println("------------");
        boolean isDeletedFiat = manufacturerDao.delete(fiat.getId());
        System.out.println("Delete Fiat: " + isDeletedFiat);
        System.out.println("------------");
        Optional<Manufacturer> optionalToyota = manufacturerDao.get(toyota.getId());
        System.out.println("Get Toyota: " + optionalToyota);
        System.out.println("------------");
        Manufacturer updateToyota = manufacturerDao.update(new Manufacturer(toyota.getId(),
                "Toyota_Motor", toyota.getCountry()));
        System.out.println("Update Toyota to Toyota Motor: " + updateToyota);
    }
}
