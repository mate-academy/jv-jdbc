import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturerOne = new Manufacturer();
        manufacturerOne.setName("BMW");
        manufacturerOne.setCountry("Germany");

        Manufacturer manufacturerTwo = new Manufacturer();
        manufacturerTwo.setName("Toyota");
        manufacturerTwo.setCountry("Japanese");

        Manufacturer manufacturerThree = new Manufacturer();
        manufacturerThree.setName("Mercedes");
        manufacturerThree.setCountry("Germany");

        Manufacturer manufacturerFour = new Manufacturer();
        manufacturerFour.setName("Tesla");
        manufacturerFour.setCountry("USA");

        Manufacturer manufacturer1 = manufacturerDao.create(manufacturerOne);
        Manufacturer manufacturer2 = manufacturerDao.create(manufacturerTwo);
        Manufacturer manufacturer3 = manufacturerDao.create(manufacturerThree);
        Manufacturer manufacturer4 = manufacturerDao.create(manufacturerFour);

        System.out.println(manufacturerDao.update(manufacturer3));
        System.out.println(manufacturerDao.update(manufacturer4));

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));

        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);
    }
}
