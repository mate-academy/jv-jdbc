package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturerToCreate = new Manufacturer();
        manufacturerToCreate.setName("Dell");
        manufacturerToCreate.setCountry("US");
        Manufacturer createdManufacturer = dao.create(manufacturerToCreate);

        Manufacturer manufacturerGotById = dao.get(3L).orElseThrow();

        boolean isDeleted = dao.delete(5L);

        Manufacturer manufacturerToUpdate = new Manufacturer();
        manufacturerToUpdate.setId(2L);
        manufacturerToUpdate.setName("Apple");
        manufacturerToUpdate.setCountry("US");
        Manufacturer updatedManufacturer = dao.update(manufacturerToUpdate);

        List<Manufacturer> allManufacturersFromDB = dao.getAll();

        allManufacturersFromDB.forEach(System.out::println);
    }
}
