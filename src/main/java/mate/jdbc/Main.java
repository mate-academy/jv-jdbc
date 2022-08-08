package mate.jdbc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Volkswagen");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Opel");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers.stream()
                .map(Manufacturer::getName)
                .collect(Collectors.toList()));
        Optional<Manufacturer> rebrand = manufacturerDao.get(5L);
        if (rebrand.isPresent()) {
            rebrand.get().setName("Porsche");
            manufacturerDao.update(rebrand.get());
        }
        manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers.stream()
                .map(Manufacturer::getName)
                .collect(Collectors.toList()));
        manufacturerDao.delete(5L);
        manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers.stream()
                .map(Manufacturer::getName)
                .collect(Collectors.toList()));
        if (manufacturerDao.get(1L).isPresent()) {
            System.out.println(manufacturerDao.get(1L).get().getName());
        }
    }
}
