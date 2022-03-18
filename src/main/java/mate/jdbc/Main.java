package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");
        Manufacturer porsche = new Manufacturer();
        porsche.setName("Porsche");
        porsche.setCountry("Germany");
        Manufacturer mitsubishi = new Manufacturer();
        mitsubishi.setName("Mitsubishi");
        mitsubishi.setCountry("Japan");
        Manufacturer lamborghini = new Manufacturer();
        lamborghini.setName("Lamborghini");
        lamborghini.setCountry("Italia");
        /**
         * Create multiple columns using the method 'create'.
         */
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(bmw);
        manufacturerDao.create(mercedes);
        manufacturerDao.create(porsche);
        manufacturerDao.create(mitsubishi);
        /**
         * Check how the table is filled, using the method 'getAll'
         */
        List<Manufacturer> manufacturerList;
        manufacturerList = manufacturerDao.getAll();
        for (Manufacturer manufacturerIterator: manufacturerList) {
            System.out.println(manufacturerIterator);
        }
        /**
         * We change the value in table by id, using the method 'update'.
         * And check the changes using the method 'get'
         */
        System.out.println(manufacturerDao.get(porsche.getId()));
        lamborghini.setId(porsche.getId());
        manufacturerDao.update(lamborghini);
        System.out.println(manufacturerDao.get(porsche.getId()));
        /**
         * Delete data from the table using the method 'delete',
         * and check using the method 'getAll'
         */
        manufacturerDao.delete(mitsubishi.getId());
        manufacturerList = manufacturerDao.getAll();
        for (Manufacturer manufacturerIterator: manufacturerList) {
            System.out.println(manufacturerIterator);
        }
    }
}

