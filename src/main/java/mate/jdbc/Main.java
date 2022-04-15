package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufactureDao manufactureDao =
                (ManufactureDao) injector.getInstance(ManufactureDao.class);

        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");
        Manufacturer fordManufacturer = new Manufacturer("Ford", "USA");
        Manufacturer miniManufacturer = new Manufacturer("Mini", "England");
        Manufacturer renaultManufacturer = new Manufacturer("Renault", "France");
        Manufacturer pegoManufacturer = new Manufacturer("Pego", "France");

        manufactureDao.create(toyotaManufacturer);
        manufactureDao.create(fordManufacturer);
        manufactureDao.create(miniManufacturer);
        manufactureDao.create(renaultManufacturer);
        manufactureDao.create(pegoManufacturer);

        List<Manufacturer> list = manufactureDao.getAll();
        System.out.println(list);

        pegoManufacturer.setName("Citroen");
        System.out.println(manufactureDao.update(pegoManufacturer));

        Optional<Manufacturer> renaulrOptional =
                manufactureDao.get(renaultManufacturer.getId());
        System.out.println(renaulrOptional.get());

        boolean isDeleted = manufactureDao.delete(miniManufacturer.getId());
        System.out.println(isDeleted);
    }
}
