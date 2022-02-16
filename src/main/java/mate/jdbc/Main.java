package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer firstCar = initializeManufacturer("BMW", "Germany");
        Manufacturer secondCar = initializeManufacturer("Ferrari", "Japan");
        Manufacturer thirdCar = initializeManufacturer("Opel", "USA");
        Manufacturer fourthCar = initializeManufacturer("Kamaz", "Russian");
        manufacturerDao.create(firstCar);
        manufacturerDao.create(secondCar);
        manufacturerDao.create(thirdCar);
        manufacturerDao.create(fourthCar);
        System.out.println("Create our manufacturers. List of all cars : "
                + manufacturerDao.getAll());
        firstCar.setName("Mercedes");
        fourthCar.setCountry("Ukraine");
        manufacturerDao.update(firstCar);
        manufacturerDao.update(fourthCar);
        System.out.println("Update first and fourth car. List of all cars : "
                + manufacturerDao.getAll());
        manufacturerDao.delete(firstCar.getId());
        manufacturerDao.delete(secondCar.getId());
        manufacturerDao.delete(thirdCar.getId());
        manufacturerDao.delete(fourthCar.getId());
        System.out.println("get all after deletion"
                + manufacturerDao.getAll());
    }

    private static Manufacturer initializeManufacturer(String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
