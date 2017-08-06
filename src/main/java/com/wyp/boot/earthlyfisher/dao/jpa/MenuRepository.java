package com.wyp.boot.earthlyfisher.dao.jpa;

import com.wyp.boot.earthlyfisher.pojo.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by earthlyfisher on 2017/4/25.
 */
@Repository
public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {

    /**
     * 根据父菜单ID获取所有下属菜单
     *
     * @param parantId
     * @return
     */
    List<Menu> findByParantId(long parantId);

    /**
     * in查询所有菜单，包括父级菜单
     *
     * @param ids
     * @return
     */
    @Query("SELECT menu.id, menu.enabled, menu.menuDesc, menu.menuName, menu.parantId, menu.priority, menu.staticIndex FROM Menu menu WHERE menu.id IN (?1) or menu.id IN (SELECT t.parantId FROM Menu t WHERE t.id IN (?1))")
    List<Menu> findAllMenus(List<Long> ids);
}
