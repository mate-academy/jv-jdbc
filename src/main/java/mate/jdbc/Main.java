package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer opel = new Manufacturer(null, "Opel", "Germany");
        Manufacturer mazda = new Manufacturer(null, "Mazda", "Japan");

        System.out.println("*** Step 1. Insert ");
        opel = manufacturerDao.create(opel);
        mazda = manufacturerDao.create(mazda);
        System.out.println(opel);
        System.out.println(mazda);

        System.out.println("*** Step 2. Read all");
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        for (Manufacturer manufacturer: manufacturerList) {
            System.out.println(manufacturer);
        }

        System.out.println("*** Step 3. Read by id");
        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);
        if (manufacturer.isPresent()) {
            System.out.println(manufacturer.get());
        } else {
            System.out.println("Do not found value by id " + 2);
        }
        manufacturer = manufacturerDao.get(200L);
        if (manufacturer.isPresent()) {
            System.out.println(manufacturer.get());
        } else {
            System.out.println("Do not found value by id " + 200);
        }

        System.out.println("*** Step 3. Update ");
        opel.setCountry("France");
        System.out.println(manufacturerDao.update(opel));

        System.out.println("*** Step 4. Delete ");
        System.out.println(manufacturerDao.delete(11L));
    }
}
