package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        Manufacturer fordMustang = new Manufacturer();
        fordMustang.setName("Ford Mustang");
        fordMustang.setCountry("USA");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(audi);
        manufacturerDao.create(fordMustang);

        System.out.println(manufacturerDao.get(fordMustang.getId()));
        manufacturerDao.getAll().forEach(System.out::println);

        fordMustang.setName("Ford");
        manufacturerDao.update(fordMustang);
        System.out.println(manufacturerDao.get(fordMustang.getId()));
        manufacturerDao.delete(audi.getId());
    }
}
