package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //create first manufacturer
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        manufacturerDao.create(ford);
        //create second manufacturer
        Manufacturer mitsubishi = new Manufacturer();
        mitsubishi.setName("Mitsubishi");
        mitsubishi.setCountry("Japan");
        manufacturerDao.create(mitsubishi);
        //create third manufacturer
        Manufacturer tesla = new Manufacturer();
        tesla.setName("Tesla");
        tesla.setCountry("USA");
        manufacturerDao.create(tesla);
        System.out.println("Manufacturers was created...");
        System.out.println("Show manufacturers 1, 2 and 3");
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));
        System.out.println("Update manufacturer with id 1");
        Manufacturer bmw = new Manufacturer();
        bmw.setId(1L);
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        manufacturerDao.update(bmw);
        System.out.println("Manufacturer with id 2 was deleted...");
        manufacturerDao.delete(2L);
        List<Manufacturer> manufacturersAfterAllRequest = manufacturerDao.getAll();
        System.out.println("Show all manufacturers");
        for (Manufacturer m : manufacturersAfterAllRequest) {
            System.out.println(m);
        }
    }
}
