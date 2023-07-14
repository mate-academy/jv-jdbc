package mate.jdbc;

import java.util.*;

import mate.jdbc.model.*;

public class Main {
    //private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");
    //hw base

    public static void main(String[] args) {
        LiteraryFormat format = new LiteraryFormat();
        format.setFormat("proza");

        LiteraryFormatDao literaryFormatsDao = new LiteraryFormatDaoImpl();
        LiteraryFormat savedFormat = literaryFormatsDao.create(format);
        System.out.println(savedFormat);
        literaryFormatsDao.getAll().forEach(System.out::println);

        System.out.println(literaryFormatsDao.delete(savedFormat.getId()));

        //allFormats.iterator();  iterator in for



        /*ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
*/



    }
}
