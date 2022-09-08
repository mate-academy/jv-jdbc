package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
//        Manufacturer cadillac = new Manufacturer();
//        cadillac.setName("Cadillac");
//        cadillac.setCountry("USA");
//        manufacturerDao.create(cadillac);

//        Manufacturer audi = new Manufacturer();
//        audi.setName("Audi");
//        audi.setCountry("Germany");
//        manufacturerDao.create(audi);

//        Manufacturer honda = new Manufacturer();
//        honda.setName("Honda");
//        honda.setCountry("China");
//        manufacturerDao.create(honda);

//        manufacturerDao.update(new Manufacturer(honda.getId(),"Honda", "Japan"));
        manufacturerDao.delete(46L);




    }
}
