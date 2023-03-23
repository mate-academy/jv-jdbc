package mate.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer carUpdate = new Manufacturer();
        carUpdate.setName("Mercedes");
        carUpdate.setCountry("Germany");
        Manufacturer carToyota = new Manufacturer();
        carToyota.setName("Toyota");
        carToyota.setCountry("Japan");
        Manufacturer carSubaru = new Manufacturer();
        carSubaru.setName("Subaru");
        carSubaru.setCountry("Japan");
        Manufacturer carAudi = new Manufacturer();
        carAudi.setName("Audi");
        carAudi.setCountry("Germany");
        List<Manufacturer> cars = new ArrayList<>(Arrays.asList(carToyota, carSubaru, carAudi));
        for (Manufacturer car : cars) {
            manufacturerDao.create(car);
        }
        Optional<Manufacturer> manufacturer = manufacturerDao.get(1L);
        List<Manufacturer> all = manufacturerDao.getAll();
        boolean delete = manufacturerDao.delete(6L);
        manufacturerDao.update(carUpdate);

    }
}
