package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufactureToyota = new Manufacturer("Toyota", "Japan");
        Manufacturer manufactureRenault = new Manufacturer("Renault", "France");

        manufacturerDao.create(manufactureToyota);
        manufacturerDao.create(manufactureRenault);
        System.out.println("SOUT for manufactureToyota: "
                + manufacturerDao.get(manufactureToyota.getId()));
        System.out.println("SOUT for manufactureRenault: "
                + manufacturerDao.get(manufactureRenault.getId()));
        System.out.println("SOUT for empty manufacture: " + manufacturerDao.get(5L));
        System.out.println("SOUT for all data" + manufacturerDao.getAll());

        boolean deleted = manufacturerDao.delete(manufactureToyota.getId());
        System.out.println("SOUT for deleting manufactureToyota: " + deleted);
        manufactureRenault.setCountry("Germany");
        manufacturerDao.update(manufactureRenault);
        System.out.println("SOUT for update in manufactureRenault: "
                + manufacturerDao.getAll());
    }
}
