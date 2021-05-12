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
        Manufacturer savedSpaceX = manufacturerDao.create(spaceX);

        //Delete
        manufacturerDao.delete(savedSpaceX.getId());

        //Create
        Manufacturer savedTesla = manufacturerDao.create(tesla);
        Manufacturer savedNvidia = manufacturerDao.create(nvidia);

        //Read
        System.out.println("Get Nvidia " + manufacturerDao
                .get(savedNvidia.getId()).orElse(savedNvidia));

        //Update
        savedTesla.setCountry("Poland");
        Manufacturer updatedTesla = manufacturerDao.update(savedTesla);
        System.out.println("Updated Tesla " + updatedTesla);

        //Create
        Manufacturer savedAmd = manufacturerDao.create(amd);
        Manufacturer savedIntel = manufacturerDao.create(intel);

        //Update
        savedAmd.setCountry("Ukraine");
        Manufacturer updatedAmd = manufacturerDao.update(savedAmd);
        System.out.println("Updated AMD " + updatedAmd);

        //Delete
        manufacturerDao.delete(savedIntel.getId());

        //Read
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Get AMD " + manufacturerDao.get(savedAmd.getId()).orElse(savedAmd));
    }
}
