package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer opel = new Manufacturer();
        opel.setName("Opel");
        opel.setCountry("Germany");
        Manufacturer opelWithId = manufacturerDao.create(opel);
        Manufacturer fiat = new Manufacturer();
        fiat.setName("Fiat");
        fiat.setCountry("Italy");
        fiat.setId(opelWithId.getId());
        manufacturerDao.update(fiat);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        Manufacturer manufacturerById = manufacturerDao.get(
                fiat.getId()).orElseThrow(() -> new RuntimeException(
                "Can't get manufacturer by id " + fiat.getId()));
        if (!manufacturerDao.delete(fiat.getId())) {
            throw new RuntimeException("Can't delete manufacturer by id "
                    + fiat.getId());
        }
    }
}
