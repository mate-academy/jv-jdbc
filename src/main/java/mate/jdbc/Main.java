package mate.jdbc;

import mate.jdbc.entity.Manufacturer;
import mate.jdbc.lib.Injector;
import mate.jdbc.repository.Repository;
import mate.jdbc.repository.impl.RepositoryManufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        RepositoryManufacturer manufacturerDao
                = (RepositoryManufacturer) injector.getInstance(Repository.class);
        Manufacturer manufacturer1 = new Manufacturer("bmv", "germany");
        Manufacturer manufacturer2 = new Manufacturer("honda", "japan");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.delete(1L);
        manufacturerDao.update(new Manufacturer(1L, "new_name", "new_country"));
        manufacturerDao.update(new Manufacturer(2L, "new_name", "new_country"));
        System.out.println(manufacturerDao.getAll());
    }
}
