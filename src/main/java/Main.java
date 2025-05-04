import dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer lincoln = new Manufacturer("Lincoln", "USA");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        // test of create()
        manufacturerDao.create(lincoln);
        manufacturerDao.create(ford);
        manufacturerDao.create(audi);
        manufacturerDao.create(bmw);
        //test of delete()
        manufacturerDao.delete(lincoln.getId());
        manufacturerDao.delete(audi.getId());
        // test of get()
        System.out.println(manufacturerDao.get(lincoln.getId()));
        System.out.println(manufacturerDao.get(bmw.getId()));
        // test of update()
        manufacturerDao.update(new Manufacturer(lincoln.getId(), "Volkswagen", "Germany"));
        System.out.println(manufacturerDao.get(lincoln.getId()));
        //test of getAll()
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
