package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerVolkswagen = new Manufacturer("Volkswagen", "Germany");
        Manufacturer manufacturerRenault = new Manufacturer("Renault", "France");
        Manufacturer manufacturerVolvo = new Manufacturer("SAAB", "Sweden");
        Manufacturer manufacturerSkoda = new Manufacturer("Skoda", "Czech Republic");
        manufacturerDao.create(manufacturerVolkswagen);
        manufacturerDao.create(manufacturerSkoda);
        manufacturerDao.create(manufacturerRenault);
        manufacturerDao.create(manufacturerVolvo);
        manufacturerDao.delete(manufacturerRenault.getId());
        manufacturerVolvo.setName("Volvo");
        manufacturerDao.update(manufacturerVolvo);
        manufacturerDao.get(manufacturerVolkswagen.getId());
        manufacturerDao.getAll();
    }
}
