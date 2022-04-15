package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer astraZeneca = new Manufacturer();
        astraZeneca.setName("AstraZeneca");
        astraZeneca.setCountry("UnitedKingdom");
        manufacturerDao.create(astraZeneca);

        Manufacturer coronaVac = new Manufacturer();
        coronaVac.setName("CoronaVac");
        coronaVac.setCountry("China");
        manufacturerDao.create(coronaVac);

        Manufacturer pfizer = new Manufacturer();
        pfizer.setName("Pfizer");
        pfizer.setCountry("USA");
        manufacturerDao.create(pfizer);

        astraZeneca.setCountry("India");
        manufacturerDao.update(astraZeneca);

        System.out.println(manufacturerDao.get(pfizer.getId()));
        System.out.println(manufacturerDao.delete(astraZeneca.getId()));
        System.out.println(manufacturerDao.getAll());
    }
}

