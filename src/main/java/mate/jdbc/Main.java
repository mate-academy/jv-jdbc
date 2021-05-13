package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerSandro = new Manufacturer(1L, "Sandro", "France");
        Manufacturer manufacturerMaje = new Manufacturer(2L, "Maje", "France");
        Manufacturer manufacturerZara = new Manufacturer(3L, "Zara", "Spain");
        manufacturerDao.create(manufacturerSandro);
        manufacturerDao.create(manufacturerMaje);
        manufacturerDao.create(manufacturerZara);

        manufacturerDao.getAll();
        manufacturerDao.get(2L);
        manufacturerDao.get(200L);

        manufacturerSandro.setCountry("Spain");
        manufacturerDao.update(manufacturerSandro);
        manufacturerDao.delete(3L);
    }
}
