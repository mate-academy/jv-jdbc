package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.db.Manufacturer;
import mate.jdbc.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer tesla = new Manufacturer();
        tesla.setCountry("USA");
        tesla.setName("Tesla");
        Manufacturer bentley = new Manufacturer();
        bentley.setCountry("England");
        bentley.setName("Bentley");
        ManufacturerDao manufacturerDao
                = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(tesla);
        manufacturerDao.create(bentley);
        manufacturerDao.delete(1L);
        Manufacturer audi = new Manufacturer();
        audi.setId(3L);
        audi.setName("Audi");
        audi.setCountry("Germany");
        manufacturerDao.update(audi);
        List<Manufacturer> manufactures = manufacturerDao.getAll();
        manufactures.forEach(System.out::println);
        Optional<Manufacturer> manufacturer = manufacturerDao.get(5L);
        System.out.println(manufacturer);
    }
}
