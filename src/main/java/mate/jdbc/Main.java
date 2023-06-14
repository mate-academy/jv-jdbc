package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.getAll());
        //create first car
        Manufacturer firstManufacturer = new Manufacturer("mercedes-benz", "Germany");
        System.out.println(manufacturerDao.create(firstManufacturer));
        //create second car
        Manufacturer secondManufacturer = new Manufacturer("fiat", "italy");
        System.out.println(manufacturerDao.create(secondManufacturer));
        //get all manufacturers
        System.out.println(manufacturerDao.getAll());
        //update name for first car
        firstManufacturer.setName("volkswagen");
        manufacturerDao.update(firstManufacturer);
        //get all manufacturers
        System.out.println(manufacturerDao.getAll());;
        //get firstManufacturer by id
        System.out.println(manufacturerDao.get(firstManufacturer.getId()));
        //delete firstManufacturer by id
        System.out.println(manufacturerDao.delete(firstManufacturer.getId()));
        //get all manufacturers
        System.out.println(manufacturerDao.getAll());
    }
}
