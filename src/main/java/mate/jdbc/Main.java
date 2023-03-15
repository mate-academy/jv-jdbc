package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        // create
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Renault");
        manufacturer.setCountry("France");
        Manufacturer savedToDbManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(savedToDbManufacturer);

        // get
//        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(13L);
//        System.out.println(manufacturerOptional);

        // getAll
//        List<Manufacturer> allManufacturersList = manufacturerDao.getAll();
//        for (Manufacturer eachManufacturer : allManufacturersList) {
//            System.out.println(eachManufacturer);
//        }

        // update
//        Manufacturer updateManufacturer = new Manufacturer();
//        updateManufacturer.setId(13L);
//        updateManufacturer.setCountry("India");
//        updateManufacturer.setName("Mahindra");
//        System.out.println(manufacturerDao.update(updateManufacturer));

        // delete
//        Manufacturer manufacturer = new Manufacturer();
//        manufacturer.setId(12L);
//        System.out.println(manufacturerDao.delete(manufacturer.getId()));
    }
}
