package mate.jdbc;

import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.ManufactureServiceImpl;
import mate.jdbc.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = new ManufactureServiceImpl(new ManufacturerDaoImpl());

        Manufacturer tokyo = new Manufacturer();
        tokyo.setId(1L);
        tokyo.setName("Toyota");
        tokyo.setCountry("Japan");
        manufacturerService.createManufacturer(tokyo);

        Manufacturer volvo = new Manufacturer();

        volvo.setId(2L);
        volvo.setName("Volvo");
        volvo.setCountry("Sweden");

        manufacturerService.createManufacturer(volvo);

        Manufacturer bmw = new Manufacturer();
        bmw.setId(2L);
        bmw.setName("BMW");
        bmw.setCountry("German");

        manufacturerService.updateManufacturer(bmw);

        manufacturerService.getManufacturer(1L);

        manufacturerService.getAllManufacturer();

        manufacturerService.deleteManufacturer(2L);

    }
}
