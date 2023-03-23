package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    private static final Injector injector = Injector.getInstance(Main.class.getPackageName());
    private static final String NAME_FOR_CREATE = "Bolt";
    private static final String COUNTRY_FOR_CREATE = "Canada";
    private static final Long ID_FOR_GET = 2L;
    private static final Long ID_FOR_DELETE = 5L;
    private static final Long ID_FOR_UPDATE = 3L;
    private static final String NAME_FOR_UPDATE = "Bogdan Taxi";
    private static final String COUNTRY_FOR_UPDATE = "Ukraine";

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("INFO           DB: " + ConnectionUtil.getDbUrl());
        System.out.println("INFO         User: " + ConnectionUtil.getUserName());
        System.out.println("\n########################### ALL ##################################");
        manufacturerDao.getAll().forEach(System.out::println);
        //test create
        System.out.println("\n########################### CREATE ###############################");
        Manufacturer testingCreate = new Manufacturer(null, NAME_FOR_CREATE, COUNTRY_FOR_CREATE);
        System.out.println("INFO      create(" + testingCreate + ")");
        System.out.println(manufacturerDao.create(testingCreate));
        //test get
        System.out.println("\n########################### GET ##################################");
        System.out.println("INFO      get(id: " + ID_FOR_GET + ")");
        System.out.println(manufacturerDao.get(ID_FOR_GET));
        //test update
        System.out.println("\n########################### UPDATE ###############################");
        Manufacturer testingUpdate = new Manufacturer(
                ID_FOR_UPDATE,
                NAME_FOR_UPDATE,
                COUNTRY_FOR_UPDATE
        );
        System.out.println("INFO      update(id: " + ID_FOR_UPDATE + ")");
        System.out.println("Before: " + manufacturerDao.get(ID_FOR_UPDATE));
        System.out.println("After:  " + manufacturerDao.update(testingUpdate));
        //test delete
        System.out.println("\n########################### DELETE ###############################");
        System.out.println("INFO      delete(id: " + ID_FOR_DELETE + ")");
        System.out.println(manufacturerDao.delete(ID_FOR_DELETE));
        //test getAll
        System.out.println("\n########################### GET ALL ##############################");
        System.out.println("INFO      getAll()");
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
