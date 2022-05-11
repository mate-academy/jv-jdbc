package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //create
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mitsubishi");
        manufacturer.setCountry("Japan");
        Manufacturer createManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("create" + System.lineSeparator() + createManufacturer);
        //getAll
        System.out.println("getAll");
        List<Manufacturer> all = manufacturerDao.getAll();
        for (Manufacturer request: all) {
            System.out.println(request);
        }
        //get
        Optional<Manufacturer> gottenManufacturer = manufacturerDao.get(3L);
        System.out.println("get" + System.lineSeparator() + gottenManufacturer);
        //update
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setName("Volkswagen");
        updateManufacturer.setCountry("Germany");
        updateManufacturer.setId(3L);
        Manufacturer updatedManufacturer = manufacturerDao.update(updateManufacturer);
        System.out.println("update" + System.lineSeparator()
                + updatedManufacturer + System.lineSeparator());
        all = manufacturerDao.getAll();
        for (Manufacturer request: all) {
            System.out.println(request);
        }
        //delete
        System.out.println("delete");
        manufacturerDao.delete(3L);
        all = manufacturerDao.getAll();
        for (Manufacturer request: all) {
            System.out.println(request);
        }
    }
}
