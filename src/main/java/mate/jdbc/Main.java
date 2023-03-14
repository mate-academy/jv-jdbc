package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        List<Manufacturer> manufacturers = getManufacturers();
        for (Manufacturer manufacturer : manufacturers) {
            manufacturerDao.create(manufacturer);
        }

        Manufacturer getManufacturer = manufacturerDao.get(1L).orElseThrow();
        System.out.println(getManufacturer);

        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        System.out.println(allManufacturer);

        Manufacturer gmc = new Manufacturer();
        gmc.setId((long) allManufacturer.size());
        gmc.setName("GMC");
        gmc.setCountry("USA");
        manufacturerDao.update(gmc);
        System.out.println(gmc);

        boolean isDeletedManufacturer = manufacturerDao.delete((long) allManufacturer.size());
        System.out.println(isDeletedManufacturer);
    }

    private static List<Manufacturer> getManufacturers() {
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        Manufacturer honda = new Manufacturer();
        honda.setName("Honda");
        honda.setCountry("Japan");
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(audi);
        manufacturers.add(honda);
        manufacturers.add(ford);
        return manufacturers;
    }
}
