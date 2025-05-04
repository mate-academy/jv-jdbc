package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println("Create:");
        System.out.println(manufacturerDao.create(audi));
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");
        System.out.println(manufacturerDao.create(mercedes));
        System.out.println("------------------------------");
        System.out.println("Get:");
        System.out.println(manufacturerDao.get(audi.getId()).orElseThrow());
        System.out.println("------------------------------");
        System.out.println("Update:");
        audi.setName("BMW");
        System.out.println(manufacturerDao.update(audi));
        System.out.println("------------------------------");
        System.out.println("GetAll:");
        System.out.println(manufacturerDao.getAll());
        System.out.println("------------------------------");
        System.out.println("Delete:");
        System.out.println(manufacturerDao.delete(1L));
    }
}

