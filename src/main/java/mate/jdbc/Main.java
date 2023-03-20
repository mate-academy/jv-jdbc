package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.LiteraryFormatDao;
import mate.jdbc.dao.LiteraryFormatDaoImpl;

public class Main {

    public static void main(String[] args) {
        LiteraryFormat format = new LiteraryFormat();
        format.setFormat("Prozac");
        LiteraryFormatDao literaryFormatDao = new LiteraryFormatDaoImpl();
        LiteraryFormat savedFormat = literaryFormatDao.create(format);
        System.out.println(savedFormat);


        List<LiteraryFormat> allFormat = literaryFormatDao.getAll();
        allFormat.forEach(System.out::println);
    }
}
