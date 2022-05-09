package mate.jdbc;

import mate.jdbc.dao.ManufacturerDaoService;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDaoService daoService = (ManufacturerDaoService) injector
                .getInstance(ManufacturerDaoService.class);
        Manufacturer bmw = daoService.create(new Manufacturer("BMW", "Germany"));
        Manufacturer mercedes = daoService.create(new Manufacturer("Mercedes", "Germany"));
        Manufacturer fiat = daoService.create(new Manufacturer("Fiat", "France"));
        System.out.println(daoService.delete(fiat.getId()));
        daoService.getAll().forEach(System.out::println);
        System.out.println(daoService.get(bmw.getId()));
        bmw.setName("Porsche");
        Manufacturer porsche = daoService.update(bmw);
        System.out.println(daoService.get(porsche.getId()));
    }
}
