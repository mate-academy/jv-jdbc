package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("China");
        manufacturer.setName("SAIC Motor");
        System.out.println("Inserted manufacturer after create request: "
                + manufacturerDao.create(manufacturer));
        System.out.println("All manufacturers from db after create request:");
        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer updatedManufacturer = new Manufacturer();
        updatedManufacturer.setId(1L);
        updatedManufacturer.setName("Dongfeng Motor");
        updatedManufacturer.setCountry("China");
        System.out.println("Updated manufacturer: " + manufacturerDao.update(updatedManufacturer));
        System.out.println("All manufacturers from db after update request: ");
        manufacturerDao.getAll().forEach(System.out::println);

        Long manufacturerId = 1L;
        System.out.println("Manufacturer received by id=" + manufacturerId
                + ": " + manufacturerDao.get(manufacturerId).orElseThrow());

        System.out.println("Delete request result: " + manufacturerDao.delete(manufacturerId));
        System.out.println("Left manufacturers after delete request:");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
