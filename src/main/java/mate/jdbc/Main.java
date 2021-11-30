package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacture1 = new Manufacturer("Toyota", "Japan");
        Manufacturer manufacture2 = new Manufacturer("Renault", "France");

        manufacturerDao.create(manufacture1);
        manufacturerDao.create(manufacture2);
        System.out.println("SOUT for manufacture1: " + manufacturerDao.get(manufacture1.getId()));
        System.out.println("SOUT for manufacture2: " + manufacturerDao.get(manufacture2.getId()));
        System.out.println("SOUT for empty manufacture: " + manufacturerDao.get(5L));
        System.out.println("SOUT for all data" + manufacturerDao.getAll());

        boolean deleted = manufacturerDao.delete(manufacture1.getId());
        System.out.println("SOUT for deleting manufacture1: " + deleted);
        manufacture2.setCountry("Germany");
        manufacturerDao.update(manufacture2);
        System.out.println("SOUT for changed manufacture2: " + manufacturerDao.getAll());
    }
}
