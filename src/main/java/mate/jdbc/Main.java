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
        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("Volkswagen");
        volkswagen.setCountry("Germany");
        manufacturerDao.create(volkswagen);
        System.out.println(volkswagen.getName());
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        manufacturerDao.create(audi);
        System.out.println(audi.getName());
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        manufacturerDao.create(bmw);
        System.out.println(bmw.getName());
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");
        manufacturerDao.create(mercedes);
        System.out.println(mercedes.getName());
        Manufacturer opel = new Manufacturer();
        opel.setName("Opel");
        opel.setCountry("Germany");
        manufacturerDao.create(opel);
        System.out.println(opel.getName());
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
