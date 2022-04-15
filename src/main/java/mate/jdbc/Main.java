package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer spaceX = new Manufacturer();
        spaceX.setName("SpaceX");
        spaceX.setCountry("USA");

        Manufacturer tesla = new Manufacturer();
        tesla.setName("Tesla");
        tesla.setCountry("USA");

        Manufacturer nvidia = new Manufacturer();
        nvidia.setName("Nvidia");
        nvidia.setCountry("USA");

        Manufacturer amd = new Manufacturer();
        amd.setName("AMD");
        amd.setCountry("USA");

        Manufacturer intel = new Manufacturer();
        intel.setName("Intel");
        intel.setCountry("USA");

        //Create
        manufacturerDao.create(spaceX);

        //Delete
        manufacturerDao.delete(spaceX.getId());

        //Create
        manufacturerDao.create(tesla);
        manufacturerDao.create(nvidia);

        //Read
        System.out.println("Get Nvidia " + manufacturerDao
                .get(nvidia.getId()).orElse(nvidia));

        //Update
        tesla.setCountry("Poland");
        manufacturerDao.update(tesla);
        System.out.println("Updated Tesla " + tesla);

        //Create
        manufacturerDao.create(amd);
        manufacturerDao.create(intel);

        //Update
        amd.setCountry("Ukraine");
        manufacturerDao.update(amd);
        System.out.println("Updated AMD " + amd);

        //Delete
        manufacturerDao.delete(intel.getId());

        //Read
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Get AMD " + manufacturerDao.get(amd.getId()).orElse(amd));
    }
}
