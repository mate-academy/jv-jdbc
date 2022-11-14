package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer skodaCzech = new Manufacturer();
        skodaCzech.setName("Skoda");
        skodaCzech.setCountry("Czech");
        Manufacturer bmwGermany = new Manufacturer();
        bmwGermany.setName("BMW");
        bmwGermany.setCountry("Germany");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(skodaCzech);
        manufacturerDao.create(bmwGermany);

        bmwGermany.setName("bmw");
        Manufacturer updateManufacturer = manufacturerDao.update(bmwGermany);
        System.out.println(updateManufacturer);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        Optional<Manufacturer> manufacturerById = manufacturerDao.get(12L);
        System.out.println(manufacturerById);

        boolean delete = manufacturerDao.delete(14L);
        System.out.println(delete);
    }
}
