package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer fiat = new Manufacturer();
        fiat.setName("Fiat");
        fiat.setCountry("Italy");
        System.out.println(manufacturerDao.create(fiat));
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        System.out.println(manufacturerDao.create(audi));
        System.out.println(audi.equals(manufacturerDao.get(audi.getId()).get()));
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer =
                manufacturerDao.getAll().stream()
                        .filter(m -> m.getName().equals("Audi"))
                        .findFirst()
                        .get();
        manufacturer.setName("Audi Q7");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
