package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = new ArrayList<>();
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");
        manufacturers.add(mercedes);
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        manufacturers.add(ford);
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        manufacturers.add(toyota);
        System.out.println(manufacturers);
        manufacturers.forEach(manufacturerDao::create);
        Manufacturer manufacturerUpdate = manufacturers.get(0);
        manufacturerUpdate.setName("Mazda");
        manufacturerUpdate.setCountry("Japan");
        manufacturerDao.update(manufacturerUpdate);
        Manufacturer manufacturerDelete = manufacturers.get(1);
        manufacturerDao.delete(manufacturerDelete.getId());
        manufacturerDao.getAll();
    }
}
