package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer haval = new Manufacturer("Haval", "China");
        // create manufacturer for tests
        Manufacturer manufacturer = manufacturerDao.create(haval);
        System.out.println("create() method test");
        System.out.println("Created new Manufacturer and wrote it to DB . "
                + "Manufacturer should be: name = \"Haval\", country = \"China\", id = 3 "
                + "and ACTUAL is = " + manufacturer);
        System.out.println("---------------------------");
        System.out.println("get(Long id) method test");
        System.out.println("Result of get(manufacturer.getId()).get should be Manufacturer:"
                + " name = \"Haval\", country = \"China\", id = 3 and ACTUAL is = "
                + manufacturerDao.get(manufacturer.getId()).get());
        System.out.println("----------------------------");
        System.out.println("getAll() method test");
        System.out.println("List from getAll() method should contain Manufacturer: "
                + "name = \"Haval\", country = \"China\", id = 3. AND ACTUAL list contains: "
                + manufacturerDao.getAll());
        System.out.println("-----------------------------");
        System.out.println("update(Manufacturer manufacturer) method test");
        // changing some parameters
        haval.setName("Renault");
        haval.setCountry("France");
        System.out.println("Changed params of haval to " + haval);
        System.out.println("Result of calling method update(haval) should be:"
                + " name = \"Renault\", country = \"France\", id = 3 . AND ACTUAL IS = "
                + manufacturerDao.update(haval));
        System.out.println("AND ALSO :");
        System.out.println("Result of method get(haval.getId()) after changing some params "
                + "and calling method update(haval) should be : "
                + "name = \"Renault\", country = \"France\", id = 3. AND ACTUAL IS = "
                + manufacturerDao.get(haval.getId()).get());
        System.out.println("-------------------------------");
        System.out.println("delete(Long id) method test");
        System.out.println("Result of delete(haval.getId()) method should be true "
                + "AND ACTUAL IS = " + manufacturerDao.delete(haval.getId()));
        System.out.println("AND ALSO :");
        System.out.println("Result of method get(haval.getId()) after calling "
                + "delete(haval.getId()) should be Optional.empty. "
                + "AND ACTUAL IS = " + manufacturerDao.get(haval.getId()));
    }
}
