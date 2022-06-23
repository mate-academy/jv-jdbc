package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer renault = manufacturerDao.create(
                Manufacturer.of(null ,"Renault","France"));
        Manufacturer volkswagen = manufacturerDao.create(
                Manufacturer.of(null,"Volkswagen", "undefined"));
        Manufacturer volvo = manufacturerDao.create(
                Manufacturer.of(null,"Volvo", "Sweden"));
        manufacturerDao.delete(renault.getId());
        Optional<Manufacturer> volvoOptional = manufacturerDao.get(volvo.getId());
        System.out.println(volvoOptional.orElseThrow());
        volkswagen.setCountry("Germany");
        manufacturerDao.update(volkswagen);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers);
        manufacturerDao.update(renault);
        System.out.println(manufacturerDao.get(120L).orElseThrow());
    }
}
