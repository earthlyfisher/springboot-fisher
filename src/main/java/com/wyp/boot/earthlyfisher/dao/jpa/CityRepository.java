package com.wyp.boot.earthlyfisher.dao.jpa;

import com.wyp.boot.earthlyfisher.pojo.City;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {

    /**
     * spring data支持自定义sql，在SQL的查询方法上面使用@Query注解，如涉及到删除和修改在需要加上@Modifying.
     * Transactional也可以根据需要添加 @Transactional 对事务的支持，查询超时的设置等
     * 对于这种修改的需要事务的支持，上层service加了@Transactional注解
     *
     * @param cityName
     * @param id
     * @return
     */
    @Modifying
    @Query("update City t set t.name=?1 where t.id=?2")
    int modifyById(String cityName, Long id);


    @Transactional(timeout = 10)
    @Modifying
    @Query("delete from City t where t.name=?1")
    int deleteByName(String cityName);

    /**
     * 模糊查询
     *
     * @param cityName
     * @return
     */
    @Query("select t.id,t.name,t.state from City t where UPPER(t.name) like UPPER(?1) or LOWER(t.name) like LOWER(?1) ")
    List<City> searchByName(String cityName);

    /**
     * 此处根据JPA规范而来.
     *
     * @param name
     * @return
     */
    City findByName(String name);
}
