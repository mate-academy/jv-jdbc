package jdbc.hw;

import jdbc.hw.dao.ManufacturerDao;
import jdbc.hw.dao.ManufacturerDaoImpl;
import jdbc.hw.lib.Injector;
import jdbc.hw.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("jdbc.hw");

    public static void main(String[] args) {

      //  ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

      //  Connection test = ConnectionUtil.getConnection();

       // manufacturerDao.getAll().forEach(System.out::println);
      // System.out.println("Deleted manufacturer: " + manufacturerDao.delete(3L));
       // System.out.println(manufacturerDao.get(3L));

        Manufacturer insertManudacturer = new Manufacturer();
        insertManudacturer.setName("vjuhaBest");
        insertManudacturer.setCountry("Nishtyaky");
        insertManudacturer.setId(3L);
        manufacturerDao.update(insertManudacturer);

       // manufacturerDao.create(insertManudacturer);
    }
}
