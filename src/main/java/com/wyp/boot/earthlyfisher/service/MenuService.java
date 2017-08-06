package com.wyp.boot.earthlyfisher.service;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.pojo.Menu;

import java.util.List;
import java.util.Set;

/**
 * Created by earthlyfisher on 2017/4/25.
 */
public interface MenuService {

    List<Menu> getMenus();

    List<Menu> getMenuTrees();

    List<Menu> getStructMenus(List<Menu> srcLst);

    List<Menu> findParentMenus();

    Set<Menu> findMenuAndParant(List<Long> menuIds);

    Menu saveMenu(Menu menu);

    Menu updateMenu(Menu menu);

    Menu getMenu(long id);

    ResponseEntity deleteMenu(long id);
}
