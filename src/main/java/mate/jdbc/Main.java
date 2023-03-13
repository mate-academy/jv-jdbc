package mate.jdbc;

import java.util.Arrays;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final long ID_FOR_UPDATE_ELEMENT = 13L;
    private static final String NAME_FOR_UPDATE_ELEMENT = "BMW";
    private static final String COUNTRY_FOR_UPDATE_ELEMENT = "Germany";
    private static final long ID_TO_GET_ELEMENT = 5L;
    private static final String NAME_FOR_CREATE_ELEMENT = "BMW";
    private static final String COUNTRY_FOR_CREATE_ELEMENT = "Germany";
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //test getAll
        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(Arrays.toString(all.toArray()));
        //test create
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(NAME_FOR_CREATE_ELEMENT);
        manufacturer.setCountry(COUNTRY_FOR_CREATE_ELEMENT);
        System.out.println(manufacturerDao.create(manufacturer));
        List<Manufacturer> listWithNewElement = manufacturerDao.getAll();
        System.out.println(Arrays.toString(listWithNewElement.toArray()));
        //test delete
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        List<Manufacturer> listWithoutNewElement = manufacturerDao.getAll();
        System.out.println(Arrays.toString(listWithoutNewElement.toArray()));
        //test update
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setId(ID_FOR_UPDATE_ELEMENT);
        manufacturer1.setName(NAME_FOR_UPDATE_ELEMENT);
        manufacturer1.setCountry(COUNTRY_FOR_UPDATE_ELEMENT);
        manufacturerDao.update(manufacturer1);
        List<Manufacturer> listWithUpdateElement = manufacturerDao.getAll();
        System.out.println(Arrays.toString(listWithUpdateElement.toArray()));
        //test get
        System.out.println(manufacturerDao.get(ID_TO_GET_ELEMENT));
    }
}
