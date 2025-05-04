package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer sony = new Manufacturer();
        sony.setName("Sony");
        sony.setCountry("Europe");

        Manufacturer philips = new Manufacturer();
        philips.setName("Philips");
        philips.setCountry("Japan");

        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //C
        Manufacturer sonyManufacturer = manufacturerDao.create(sony);
        Manufacturer philipsManufacturer = manufacturerDao.create(philips);
        System.out.println("Create manufacturers: " + sonyManufacturer + " " + philipsManufacturer);
        //R
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println("Get all manufacturers from DB: " + allManufacturers);
        Optional<Manufacturer> getPhilips = manufacturerDao.get(philips.getId());
        System.out.println(getPhilips.get());
        //U
        sony.setCountry("Ukraine");
        Manufacturer updateSony = manufacturerDao.update(sony);
        System.out.println("Update data from DB: " + updateSony);
        //D
        boolean deletedManufacturer = manufacturerDao.delete(sony.getId());
        System.out.println(deletedManufacturer);
    }
}
