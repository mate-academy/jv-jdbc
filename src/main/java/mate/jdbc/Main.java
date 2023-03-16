package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        /* Test data
        Manufacturer firstDummyManufacturer = new Manufacturer();
        firstDummyManufacturer.setId(1L);
        firstDummyManufacturer.setName("Porsche");
        firstDummyManufacturer.setCountry("Germany");

        Manufacturer secondDummyManufacturer = new Manufacturer();
        secondDummyManufacturer.setId(2L);
        secondDummyManufacturer.setName("Mitsubishi");
        secondDummyManufacturer.setCountry("Japan");

        Manufacturer thirdDummyManufacturer = new Manufacturer();
        thirdDummyManufacturer.setId(3L);
        thirdDummyManufacturer.setName("Zaz");
        thirdDummyManufacturer.setCountry("Ukraine");

        manufacturerDao.create(firstDummyManufacturer);
        manufacturerDao.create(secondDummyManufacturer);
        manufacturerDao.create(thirdDummyManufacturer);
         */

        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("BMW");
        newManufacturer.setCountry("Germany");
        Manufacturer createdManufacturer = manufacturerDao.create(newManufacturer);
        System.out.println(".create() new: " + createdManufacturer + System.lineSeparator());

        Long createdManufacturerId = createdManufacturer.getId();
        Manufacturer gotManufacturerFromDb = manufacturerDao.get(createdManufacturerId).get();
        System.out.println(".get() by id: " + gotManufacturerFromDb + System.lineSeparator());

        System.out.println(".getAll() from DB:");
        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setName("Ford");
        updateManufacturer.setCountry("USA");
        updateManufacturer.setId(createdManufacturerId);
        System.out.println(System.lineSeparator() + ".update(): with " + updateManufacturer);
        System.out.println("Old manufacturer: " + gotManufacturerFromDb);
        Manufacturer updatedManufacturer = manufacturerDao.update(updateManufacturer);
        System.out.println("Updated manufacturer: " + updatedManufacturer + System.lineSeparator());

        boolean delete = manufacturerDao.delete(createdManufacturerId);
        System.out.println(".delete(): for " + updatedManufacturer
                + " is " + delete + System.lineSeparator());

        System.out.println(".getAll() after .delete() operation: ");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());
    }
}
