package com.wyp.boot.earthlyfisher.controller;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.pojo.City;
import com.wyp.boot.earthlyfisher.service.CityService;
import com.wyp.boot.earthlyfisher.validator.CityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Resource
    private CityService cityService;

    @Autowired
    private CityValidator cityValidator;

    /**
     * 在Spring的数据绑定器中进行注册，而注册时机是特定于控制器的
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(cityValidator);
    }

    @RequestMapping(params = "daoType=jpa", method = RequestMethod.POST)
    public ResponseEntity addCityByJpa(@RequestBody @Valid City city) {
        cityService.saveCity4Jpa(city);
        return ResponseEntity.getSuccessResponse(null);
    }

    @RequestMapping(params = "daoType=jdbc", method = RequestMethod.POST)
    public ResponseEntity addCityByJdbc(@RequestBody @Valid City city) {
        cityService.saveCity4Jdbc(city);
        return ResponseEntity.getSuccessResponse(null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity modifyCity(@RequestBody City city) {
        cityService.updateCity(city.getName(), city.getId());
        return ResponseEntity.getSuccessResponse(null);
    }

    /**
     * 分页获取
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<City> getAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                 @RequestParam(value = "count", defaultValue = "10", required = false) int count,
                                 @RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
                                 @RequestParam(value = "sort", defaultValue = "id", required = false) String sortProperty) {
        Sort sort = new Sort(direction, sortProperty);
        Pageable pageable = new PageRequest(page, count, sort);
        Page<City> pages = cityService.findAll(pageable);
        return pages.getContent();
    }

    /**
     * 模糊查询支持
     *
     * @param queryTerm
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<City> queryCityByNameq(@RequestParam("q") String queryTerm) {
        return cityService.queryCityByNameq(queryTerm);
    }

}
