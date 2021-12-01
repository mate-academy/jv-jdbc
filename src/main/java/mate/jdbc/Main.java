package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer bmw = new Manufacturer("BWM", "Germany");
        Manufacturer ferrari = new Manufacturer("Ferrari", "Italia");
        Manufacturer audi = new Manufacturer("Audi", "Germany");

        manufacturerDao.create(bmw);
        manufacturerDao.create(ferrari);
        manufacturerDao.create(audi);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();

        Optional<Manufacturer> manufacturer = manufacturerDao.get(1L);

        Manufacturer bentley = manufacturerDao.create(bmw);
        bentley.setName("Bentley");
        bentley.setCountry("England");
        manufacturerDao.update(bentley);
    }
}

