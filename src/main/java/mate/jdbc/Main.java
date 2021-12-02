package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerSaab = new Manufacturer("SAAB", "Sweden");
        Manufacturer manufacturerJeep = new Manufacturer("Jeep", "USA");
        Manufacturer manufacturerRenault = new Manufacturer("Renault", "France");
        manufacturerDao.create(manufacturerSaab);
        manufacturerDao.create(manufacturerJeep);
        manufacturerDao.create(manufacturerRenault);

        System.out.println(manufacturerDao.get(manufacturerSaab.getId()));
        manufacturerSaab.setName("Spyker");
        manufacturerDao.update(manufacturerSaab);
        System.out.println(manufacturerDao.get(manufacturerSaab.getId()));
        manufacturerDao.delete(manufacturerRenault.getId());
        System.out.println("changed table");
        System.out.println(manufacturerDao.getAll());
    }
}
