package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer firstCar = new Manufacturer("Tesla", "Ukraine");
        Manufacturer secondCar = new Manufacturer("Another good car", "Japan");
        Manufacturer thirdCar = new Manufacturer("Regular Car", "Japan");
        manufacturerDao.create(firstCar);
        manufacturerDao.create(secondCar);
        manufacturerDao.create(thirdCar);
        System.out.println("Get first car " + manufacturerDao.get(firstCar.getId()));
        System.out.println("Get third car " + manufacturerDao.get(thirdCar.getId()));
        System.out.println("Get all " + manufacturerDao.getAll());
        manufacturerDao.update(firstCar);
        manufacturerDao.delete(secondCar.getId());
        manufacturerDao.delete(thirdCar.getId());
        System.out.println("get all after deletion" + manufacturerDao.getAll());
    }
}
