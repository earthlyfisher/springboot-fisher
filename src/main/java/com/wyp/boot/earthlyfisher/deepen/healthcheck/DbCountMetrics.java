package com.wyp.boot.earthlyfisher.deepen.healthcheck;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by earthlyfisher on 2017/5/22.
 */
public class DbCountMetrics implements PublicMetrics {

    private Collection<CrudRepository> repositories;


    public DbCountMetrics(Collection<CrudRepository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public Collection<Metric<?>> metrics() {
        List<Metric<?>> metrics=new LinkedList<>();
       /* for(CrudRepository repository:repositories){
            String metricName="counter.dataSource."+repository;
            metrics.add(new Metric<Number>(metricName,repository.count()));
        }*/
        return metrics;
    }
}
