package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        System.out.println("Create:");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");
        Manufacturer audi = manufacturerDao.create(manufacturer);
        System.out.println(audi);
        System.out.println("------------------------------");
        System.out.println("Get:");
        Manufacturer getAudi = manufacturerDao.get(audi.getId()).orElseThrow();
        System.out.println(getAudi);
        System.out.println("------------------------------");
        System.out.println("Update:");
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        manufacturer.setName("BMW");
        Manufacturer updateAudi = manufacturerDao.update(manufacturer);
        System.out.println(updateAudi);
        System.out.println("------------------------------");
        System.out.println("GetAll:");
        System.out.println(manufacturerDao.getAll());
        System.out.println("------------------------------");
        System.out.println("Delete:");
        System.out.println(manufacturerDao.delete(1L));
    }
}

