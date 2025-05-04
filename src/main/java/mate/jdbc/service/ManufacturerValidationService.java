package mate.jdbc.service;

import lombok.extern.log4j.Log4j2;
import mate.jdbc.exceptions.ValidationException;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.Inject;
import mate.jdbc.model.Manufacturer;

@Dao
@Inject
@Log4j2
public class ManufacturerValidationService {
    private Manufacturer manufacturer;

    public void validateBeforeCreate(Manufacturer manufacturer) {
        validateManufacturerName(manufacturer.getName());
        validateCountry(manufacturer.getCountry());
    }

    public void validateBeforeUpdate(Manufacturer manufacturer) {
        validateId(manufacturer.getId());
        validateManufacturerName(manufacturer.getName());
        validateCountry(manufacturer.getCountry());
    }

    private void validateId(Long id) {
        if (id == null || id < 1) {
            throw new ValidationException("Invalid manufacturer id");
        }
    }

    private void validateManufacturerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Manufacturer name cannot be empty");
        }
        if (name.length() > 255) {
            throw new ValidationException("Manufacturer name too long");
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new ValidationException("Country cannot be empty");
        }
    }
}
