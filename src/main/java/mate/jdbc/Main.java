package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        for (int i = 1; i < 6; i++) {
            System.out.println(dao.create(new Manufacturer(null, "NameTest" + i, 
                    "CountryTest" + i)));
        }

        System.out.println(dao.get(3L).orElseThrow(() -> 
                new RuntimeException("Manufacturer not found")));

        System.out.println(dao.getAll());

        System.out.println(dao.update(new Manufacturer(4L, "UpdateNameTest", 
                "UpdateCountryTest")).get());

        System.out.println(dao.delete(2L));
        System.out.println(dao.get(2L).orElseThrow(() -> 
                new RuntimeException("Manufacturer not found")));
    }
}
