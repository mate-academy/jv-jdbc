package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerZara = new Manufacturer();
        manufacturerZara.setName("ZARA");
        manufacturerZara.setCountry("Spain");
        manufacturerDao.create(manufacturerZara);

        Manufacturer manufacturerEcco = new Manufacturer();
        manufacturerEcco.setName("ECCO");
        manufacturerEcco.setCountry("Dania");
        manufacturerDao.create(manufacturerEcco);

        Manufacturer manufacturerEccoUsa = new Manufacturer();
        manufacturerEccoUsa.setName("ECCO");
        manufacturerEccoUsa.setCountry("USA");
        manufacturerDao.create(manufacturerEccoUsa);

        manufacturerEccoUsa.setName("ECCO_USA");
        manufacturerDao.update(manufacturerEccoUsa);

        System.out.println(manufacturerDao.get(manufacturerEccoUsa.getId()));
        System.out.println(manufacturerDao.delete(manufacturerZara.getId()));
        System.out.println(manufacturerDao.getAll());
    }
}
