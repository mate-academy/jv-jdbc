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

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("KrAZ");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);

        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(1L);
        Manufacturer manufacturer1 = optionalManufacturer.orElseThrow();
        System.out.println(manufacturer1);

        manufacturerDao.delete(1L);

        Manufacturer bmw = manufacturerDao.get(2L).orElseThrow();
        bmw.setCountry("Deutchland");
        manufacturerDao.update(bmw);

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList
                .forEach((m) -> System.out.println(m.getId()
                        + " " + m.getName() + " " + m.getCountry()));
    }
}
