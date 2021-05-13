package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final Long WRONG_ID = 200L;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerSandro = new Manufacturer("Sandro", "France");
        Manufacturer manufacturerMaje = new Manufacturer("Maje", "France");
        Manufacturer manufacturerZara = new Manufacturer("Zara", "Spain");
        manufacturerDao.create(manufacturerSandro);
        manufacturerDao.create(manufacturerMaje);
        manufacturerDao.create(manufacturerZara);

        manufacturerDao.getAll();
        System.out.println(manufacturerDao.get(manufacturerMaje.getId()));
        manufacturerDao.get(WRONG_ID);

        manufacturerSandro.setCountry("Spain");
        manufacturerDao.update(manufacturerSandro);
        manufacturerDao.delete(manufacturerZara.getId());
    }
}
