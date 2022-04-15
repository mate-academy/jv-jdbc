package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer sanos = new Manufacturer();
        sanos.setName("Sanos");
        sanos.setCountry("North Macedonia");

        Manufacturer skoda = new Manufacturer();
        skoda.setName("Skoda");
        skoda.setCountry("Czech Republic");

        Manufacturer acura = new Manufacturer();
        acura.setName("Acura");
        acura.setCountry("Japan");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer sanosManufacturer = manufacturerDao.create(sanos);
        Manufacturer skodaManufacturer = manufacturerDao.create(skoda);
        Manufacturer acuraManufacturer = manufacturerDao.create(acura);
        System.out.println("Created manufacturers: " + sanosManufacturer
                + " " + skodaManufacturer + " " + acuraManufacturer);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println("Get all manufacturers: " + allManufacturers);
        Optional<Manufacturer> getAcuraManufacturer = manufacturerDao.get(acura.getId());
        System.out.println("Get Acura manufacturer: " + getAcuraManufacturer.get());

        skoda.setName("Škoda");
        Manufacturer updateSkodaManufacturer = manufacturerDao.update(skoda);
        System.out.println("Update Škoda manufacturer: " + updateSkodaManufacturer);

        boolean deleteAcuraManufacturer = manufacturerDao.delete(acura.getId());
        System.out.println("Delete manufacturer from DB: " + deleteAcuraManufacturer);
    }
}
