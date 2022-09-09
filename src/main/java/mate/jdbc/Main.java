package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(
                ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);

        System.out.println("******************* GET ALL ******************");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        System.out.println("******************* UPDATE ******************");
        manufacturer.setName("Ford");
        manufacturer.setCountry("USA");
        manufacturer.setId(2L);
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturer);
        manufacturerDao.delete(12L);
        System.out.println("********************************************");
        System.out.println(manufacturerDao.get(150L));
    }
}
