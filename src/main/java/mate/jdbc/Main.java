package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.model.ManufacturerDao;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer cadillac = new Manufacturer("Cadillac","USA");
        Manufacturer toyota = new Manufacturer("Toyota","Japan");
        Manufacturer koenigsegg = new Manufacturer("Koenigsegg","Swiden");
        //manufacturerDao.create(cadillac);
        //manufacturerDao.create(toyota);
        //manufacturerDao.create(koenigsegg);

        Manufacturer toyotaYaris = new Manufacturer(20L,"ToyotaYarisCross","Japan2");
        manufacturerDao.update(toyotaYaris);

        manufacturerDao.get(21L);

        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.delete(18L));
    }
}
