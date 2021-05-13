package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer dell = new Manufacturer();
        dell.setName("Dell");
        dell.setCountry("US");
        Manufacturer honda = new Manufacturer();
        dell.setName("Honda");
        dell.setCountry("Japan");

        Manufacturer uploadedDell = dao.create(dell);
        Manufacturer uploadedHonda = dao.create(honda);

        Manufacturer manufacturerGotById = dao.get(uploadedDell.getId()).orElseThrow();

        boolean isDeleted = dao.delete(uploadedDell.getId());

        Manufacturer apple = new Manufacturer();
        apple.setId(uploadedHonda.getId());
        apple.setName("Apple");
        apple.setCountry("US");
        Manufacturer updatedManufacturerById = dao.update(apple);

        List<Manufacturer> allManufacturersFromDB = dao.getAll();

        allManufacturersFromDB.forEach(System.out::println);
    }
}
