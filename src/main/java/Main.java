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
        manufacturerDao.create(lincoln);
        manufacturerDao.create(ford);
        manufacturerDao.create(audi);
        manufacturerDao.create(bmw);

        manufacturerDao.delete(16L);
        manufacturerDao.delete(22L);
        manufacturerDao.delete(24L);
        manufacturerDao.delete(27L);

        System.out.println(manufacturerDao.get(23L));
        manufacturerDao.update(new Manufacturer(23L, "Volkswagen", "Germany"));
        System.out.println(manufacturerDao.get(23L));

    }
}
