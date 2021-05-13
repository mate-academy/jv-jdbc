package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer bmwManufacturer = new Manufacturer("BMW", "Germany");
        System.out.println("Create method test:");
        System.out.println(manufacturerDao.create(bmwManufacturer) + System.lineSeparator());

        System.out.println("Get method test:");
        System.out.println(manufacturerDao.get(2L) + System.lineSeparator());

        System.out.println("Delete method test:");
        System.out.println(manufacturerDao.delete(12L) + System.lineSeparator());

        Manufacturer kfcManufacturer = new Manufacturer("KFC", "USA");
        kfcManufacturer.setId(8L);
        System.out.println("Update method test:");
        System.out.println(manufacturerDao.update(kfcManufacturer) + System.lineSeparator());

        System.out.println("GetAll method test:");
        System.out.println(manufacturerDao.getAll() + System.lineSeparator());
    }
}
