package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufactureDao manufacturerServices =
                (ManufactureDao) injector.getInstance(ManufactureDao.class);

        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");
        Manufacturer fordManufacturer = new Manufacturer("Ford", "USA");
        Manufacturer miniManufacturer = new Manufacturer("Mini", "England");
        Manufacturer renaultManufacturer = new Manufacturer("Renault", "France");
        Manufacturer pegoManufacturer = new Manufacturer("Pego", "France");

        manufacturerServices.create(toyotaManufacturer);
        manufacturerServices.create(fordManufacturer);
        manufacturerServices.create(miniManufacturer);
        manufacturerServices.create(renaultManufacturer);
        manufacturerServices.create(pegoManufacturer);

        List<Manufacturer> list = manufacturerServices.getAll();
        System.out.println(list);

        pegoManufacturer.setName("Citroen");
        System.out.println(manufacturerServices.update(pegoManufacturer));

        Optional<Manufacturer> renaulrOptional =
                manufacturerServices.get(renaultManufacturer.getId());
        System.out.println(renaulrOptional.get());

        boolean isDeleted = manufacturerServices.delete(miniManufacturer.getId());
        System.out.println(isDeleted);
    }
}
