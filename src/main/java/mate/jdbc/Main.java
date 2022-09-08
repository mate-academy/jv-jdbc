package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        System.out.println("Create manufacturers");
        Manufacturer bmw = new Manufacturer(null, "bmw", "Germany");
        manufacturerDao.create(bmw);

        Manufacturer tesla = new Manufacturer(null, "tesla", "USA");
        manufacturerDao.create(tesla);

        Manufacturer volkswagen = new Manufacturer(null, "volkswagen", "Germany");
        manufacturerDao.create(volkswagen);

        System.out.println("Get all manufacturers");
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("Set country to manufacturer");
        bmw.setCountry("Ukraine");
        manufacturerDao.update(bmw);

        System.out.println(manufacturerDao.get(bmw.getId()));

        System.out.println("Delete manufacturer bmw");
        manufacturerDao.delete(bmw.getId());

        System.out.println("Get all manufacturers after delete");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
