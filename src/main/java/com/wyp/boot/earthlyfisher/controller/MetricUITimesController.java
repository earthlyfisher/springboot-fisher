package com.wyp.boot.earthlyfisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * CounterService借助于此接口，实现对某rest的访问数量统计.
 * <p>
 * Created by earthlyfisher on 2017/2/3.
 */
@RestController
public class MetricUITimesController {

    @Autowired
    private CounterService countService;

    @RequestMapping(value = "/uiTimes", method = RequestMethod.GET)
    public String testUITimes() {
        countService.increment("MetricUITimesController-uiTimes");
        return "uiTimes";
    }

}
