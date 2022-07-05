package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufecturersDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufecturersDao manufecturersDao = (ManufecturersDao) injector
                .getInstance(ManufecturersDao.class);

        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("Jackson");
        firstManufacturer.setCountry("Israel");
        Manufacturer manufacturer = manufecturersDao.create(firstManufacturer);
        Optional<Manufacturer> manufacturerOptional = manufecturersDao.get(manufacturer.getId());
        System.out.println(manufacturerOptional);
        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setId(1L);
        secondManufacturer.setName("Tilly");
        secondManufacturer.setCountry("Benghazi");
        manufecturersDao.update(secondManufacturer);
        manufecturersDao.delete(firstManufacturer.getId());
    }
}
