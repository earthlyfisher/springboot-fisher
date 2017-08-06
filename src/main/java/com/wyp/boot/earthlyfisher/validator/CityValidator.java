package com.wyp.boot.earthlyfisher.validator;

import com.wyp.boot.earthlyfisher.pojo.City;
import com.wyp.boot.earthlyfisher.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * rest参数校验器
 * Created by earthlyfisher on 2017/2/21.
 */
@Component
public class CityValidator implements Validator {

    private final CityService cityService;

    @Autowired
    public CityValidator(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return City.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        City city = (City) target;
        if (city.getName().trim().equals("")) {
            errors.rejectValue("name", "city.name.invalid", "city name is invalid");
        }
    }
}
