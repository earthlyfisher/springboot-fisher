package com.wyp.boot.earthlyfisher.service.impl;

import com.wyp.boot.earthlyfisher.dao.jdbc.CityDao;
import com.wyp.boot.earthlyfisher.dao.jpa.CityRepository;
import com.wyp.boot.earthlyfisher.pojo.City;
import com.wyp.boot.earthlyfisher.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("cityService")
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityDao cityDao;

    @Override
    @Transactional
    public void saveCity4Jpa(City city) {
        cityRepository.save(city);
    }

    @Override
    @Transactional
    public void saveCity4Jdbc(City city) {
        cityDao.saveCity(city);
    }

    @Transactional
    @Override
    public int updateCity(String name, Long id) {
        return cityRepository.modifyById(name, id);
    }

    @Override
    public Page<City> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    @Override
    public List<City> queryCityByNameq(String queryTerm) {
        String qTerm = "%" + queryTerm + "%";
        List<City> list = cityRepository.searchByName(qTerm);
        return list == null ? new ArrayList<City>() : list;
    }
}
