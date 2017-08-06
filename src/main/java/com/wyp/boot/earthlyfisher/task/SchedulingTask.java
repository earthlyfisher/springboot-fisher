package com.wyp.boot.earthlyfisher.task;

import com.wyp.boot.earthlyfisher.dao.jpa.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 任务调度.
 *
 * @author earthlyfisher
 */
@Component
@EnableScheduling
public class SchedulingTask {

    private static final Logger LOGGER= LoggerFactory.getLogger(SchedulingTask.class);

    @Autowired
    private CityRepository CityRepository;

    // @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        System.out.println(">>>>>>>>> SchedulingConfig.scheduler()");
    }

    //@Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void run() {
        LOGGER.info("Number of books: " + CityRepository.count());
    }
}
