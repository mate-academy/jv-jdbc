package mate.jdbc;

import model.dao.ManufacturerDao;
import model.entity.Manufacturer;

public class Main {
    public static void main(String[] args) {
        System.out.println("create manufacturer");
        //create manufacturer1
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("name1").setCountry("country1");
        ManufacturerDao.getInstance().create(manufacturer1);
        //create manufacturer2
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("name2").setCountry("country2");
        ManufacturerDao.getInstance().create(manufacturer2);
        //getAll
        System.out.println("getAll");
        ManufacturerDao.getInstance().getAll().forEach(System.out::println);
        //update
        System.out.println("update");
        manufacturer1.setName("updateName1").setCountry("updateCounty1");
        ManufacturerDao.getInstance().update(manufacturer1);
        //getAll
        System.out.println("getAll");
        ManufacturerDao.getInstance().getAll().forEach(System.out::println);
        //deleted
        System.out.println("deleted");
        ManufacturerDao.getInstance().delete(manufacturer1.getId());
        //getAll
        System.out.println("getAll");
        ManufacturerDao.getInstance().getAll().forEach(System.out::println);
    }
}
