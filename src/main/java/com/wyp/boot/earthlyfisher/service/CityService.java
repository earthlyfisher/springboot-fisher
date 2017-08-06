package com.wyp.boot.earthlyfisher.service;

import com.wyp.boot.earthlyfisher.pojo.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {

    void saveCity4Jpa(City city);

    void saveCity4Jdbc(City city);

    int updateCity(String name, Long id);

    Page<City> findAll(Pageable pageable);

    List<City> queryCityByNameq(String queryTerm);
}
