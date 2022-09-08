package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer cadillac = new Manufacturer();
        cadillac.setName("Cadillac");
        cadillac.setCountry("USA");
        manufacturerDao.create(cadillac);
        Manufacturer manufacturerAudi = manufacturerDao.create(new Manufacturer("Audi","Germany"));

        manufacturerDao.update(new Manufacturer(manufacturerAudi.getId(),
                "Audi", "Germany"));


    }
}
