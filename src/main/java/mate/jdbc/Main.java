package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("MTLB");
        manufacturer.setCountry("Russia");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Peugeot");
        manufacturer.setCountry("France");
        manufacturerDao.create(manufacturer);
        manufacturerDao.delete(1L);
        List<Manufacturer> allAlive = manufacturerDao.getAll();
        for (Manufacturer aliveOne: allAlive) {
            System.out.println(aliveOne.getName() + " " + aliveOne.getCountry());
        }
    }
}
