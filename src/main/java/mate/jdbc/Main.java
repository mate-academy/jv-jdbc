package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        // Create new manufacturers
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer kia = new Manufacturer("KIA", "South Korea");
        // Test: Write manufacturers to DB
        System.out.println("Create toyota in DB : " + manufacturerDao.create(toyota));
        System.out.println("Create KIA in DB : " + manufacturerDao.create(kia));
        // Test: Read manufacturers which wrote previously
        System.out.println("Get from db Toyota : " + manufacturerDao.get(toyota.getId()));
        System.out.println("Get from db KIA : " + manufacturerDao.get(kia.getId()));
        // Test: Read all manufacturers from db
        System.out.println("getAll : " + manufacturerDao.getAll());
        //Change name and country in manufacturer
        kia.setCountry("USA");
        kia.setName("kia");
        toyota.setCountry("Ukraine");
        toyota.setName("TOYOTA");
        //Update data in DB
        System.out.println("Update country and name in KIA : " + manufacturerDao.update(kia));
        System.out.println("Update country and name in TOYOTA : " + manufacturerDao.update(toyota));
        // Test: Read all manufacturers from db with new name and country
        System.out.println("getAll : " + manufacturerDao.getAll());
        // Test: Delete data from DB
        System.out.println("delete kia from db : " + manufacturerDao.delete(kia.getId()));
        System.out.println("delete TOYOTA from db : " + manufacturerDao.delete(toyota.getId()));
        // Test: Read all manufacturers from db after deleted
        System.out.println("getAll after delete: " + manufacturerDao.getAll());
        //Change name and country in manufacturer
        kia.setCountry("SOUTH KOREA");
        kia.setName("KIA");
        toyota.setCountry("JAPAN");
        toyota.setName("toyota");
        //Test: Update after delete
        System.out.println("Update after delete KIA from DB : "
                + manufacturerDao.update(kia));
        System.out.println("Update after delete TOYOTA from DB : "
                + manufacturerDao.update(toyota));
        //Test: Read after delete
        System.out.println("Get after delete from db Toyota : "
                + manufacturerDao.get(toyota.getId()));
        System.out.println("Get after delete from db KIA : "
                + manufacturerDao.get(kia.getId()));
    }
}
