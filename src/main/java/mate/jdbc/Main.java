package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer fordManufacturer = new Manufacturer("FORD", "USA");
        Manufacturer nissanManufacturer = new Manufacturer("NISSAN", "JAPAN");
        manufacturerDao.create(fordManufacturer);
        manufacturerDao.create(nissanManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(fordManufacturer.getId()));
        Manufacturer bmwManufacturer = new Manufacturer();
        bmwManufacturer.setId(fordManufacturer.getId());
        bmwManufacturer.setName("BMW");
        bmwManufacturer.setCountry("GERMANY");
        System.out.println(manufacturerDao.update(bmwManufacturer));
        manufacturerDao.delete(nissanManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
