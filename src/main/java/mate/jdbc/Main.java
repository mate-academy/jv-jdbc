package mate.jdbc;

import mate.jdbc.dao.ManufecturersDao;
import mate.jdbc.dao.ManufecturersDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufecturersDao manufecturersDao = (ManufecturersDao) injector.getInstance(ManufecturersDao.class);

        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("Jackson");
        firstManufacturer.setCountry("Israel");
        Manufacturer manufacturer = manufecturersDao.create(firstManufacturer);
        Optional<Manufacturer> manufacturerOptional = manufecturersDao.get(manufacturer.getId());
        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("Tilly");
        secondManufacturer.setCountry("Benghazi");
        manufecturersDao.update(manufacturerOptional.get().getId(), secondManufacturer);
        manufecturersDao.delete(firstManufacturer.getId());
    }
}
