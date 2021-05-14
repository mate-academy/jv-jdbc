package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer dell = new Manufacturer();
        dell.setName("Dell");
        dell.setCountry("US");
        Manufacturer honda = new Manufacturer();
        honda.setName("Honda");
        honda.setCountry("Japan");

        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer createdDell = dao.create(dell);
        Manufacturer createdHonda = dao.create(honda);

        Manufacturer manufacturerGotById = dao.get(dell.getId()).orElseThrow();
        System.out.println(manufacturerGotById + System.lineSeparator());

        boolean isDeleted = dao.delete(honda.getId());

        dell.setName("IBM");
        dell.setCountry("US");
        Manufacturer updatedManufacturer = dao.update(dell);
        System.out.println(updatedManufacturer + System.lineSeparator());

        dao.getAll().forEach(System.out::println);
    }
}
