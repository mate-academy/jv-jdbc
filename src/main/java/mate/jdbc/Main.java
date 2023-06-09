package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer fordManufacturer = new Manufacturer("Ford","USA");
        Manufacturer ford = manufacturerDao.create(fordManufacturer);
        Manufacturer dodgeManufacturer = new Manufacturer("Dodge","USA");
        Manufacturer dodge = manufacturerDao.create(dodgeManufacturer);
        manufacturerDao.delete(ford.getId());
        Optional<Manufacturer> resultOfDodgeQuerry = manufacturerDao.get(dodge.getId());
        System.out.println(resultOfDodgeQuerry);
        manufacturerDao.update(new Manufacturer(dodge.getId(),"Cherry", "China"));
        manufacturerDao.create(dodgeManufacturer);
        System.out.println(manufacturerDao.getAll());
    }

}
