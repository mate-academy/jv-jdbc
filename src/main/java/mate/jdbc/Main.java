package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer productGermany = new Manufacturer();
        productGermany.setName("product of Germany");
        productGermany.setCountry("Germany");
        manufacturerDao.create(productGermany);
        Manufacturer productJapan = new Manufacturer();
        productJapan.setName("product of Japan");
        productJapan.setCountry("Japan");
        manufacturerDao.create(productJapan);
        Manufacturer productUsa = new Manufacturer();
        productUsa.setName("product of USA");
        productUsa.setCountry("USA");
        manufacturerDao.create(productUsa);

        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer productItaly = new Manufacturer();
        productItaly.setName("firstProduct of Italy");
        productItaly.setCountry("Italy");
        manufacturerDao.create(productItaly);
        productItaly.setName("secondProduct of Italy");
        manufacturerDao.update(productItaly);

        Optional<Manufacturer> productItalyOptional = manufacturerDao.get(productItaly.getId());
        System.out.println(productItalyOptional.toString());

        System.out.println(manufacturerDao.delete(productUsa.getId()));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
