package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<String> brands = List.of("Mercedes-Benz","Volkswagen","Jeep","Hyundai");
        List<String> countries = List.of("Germany","Germany","US","S.Korea");
        List<Manufacturer> manufacturers = new ArrayList<>();
        for (int i = 0; i < brands.size(); i++) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(brands.get(i));
            manufacturer.setCountry(countries.get(i));
            manufacturers.add(manufacturer);
        }
        for (Manufacturer manufacturer : manufacturers) {
            Manufacturer addedManufacturer = manufacturerDao.create(manufacturer);
            System.out.println("Create method. Manufacturer added: '"
                    + addedManufacturer + "' to the database.");
        }
        System.out.println("---------------------");
        System.out.println("GetAll method. List of manufacturers in the DB");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        System.out.println("---------------------");
        Long id = 4L;
        System.out.println("Get method. Manufacturer with id " + id + " is:");
        Manufacturer getManufacturer = manufacturerDao.get(id).get();
        System.out.println(getManufacturer);
        System.out.println("---------------------");
        System.out.println("Update method. Manufacturer with id " + id + " is update:");
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setId(4L);
        newManufacturer.setName("Porsche");
        newManufacturer.setCountry("Germany");
        Manufacturer updateManufacturer = manufacturerDao.update(newManufacturer);
        System.out.println(updateManufacturer);
        System.out.println("---------------------");
        System.out.println("Delete method. Manufacturer with id " + id + " is deleted.");
        manufacturerDao.delete(id);
        System.out.println("List of manufacturers in the DB");
        List<Manufacturer> allManufacturersRemain = manufacturerDao.getAll();
        allManufacturersRemain.forEach(System.out::println);
    }
}
