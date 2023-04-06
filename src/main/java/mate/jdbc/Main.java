package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.get(2L).get().getName());

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Reno");
        manufacturer.setCountry("France");
        Manufacturer saveManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.update(saveManufacturer).getCountry());

        manufacturerDao.delete(saveManufacturer.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
