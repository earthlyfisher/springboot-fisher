package com.wyp.boot.earthlyfisher.controller;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.pojo.Menu;
import com.wyp.boot.earthlyfisher.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by earthlyfisher on 2017/4/29.
 */
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单树
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Menu> getMenus() {
        return menuService.getMenus();
    }


    /**
     * 获取菜单树
     *
     * @return
     */
    @RequestMapping(value = "/trees", method = RequestMethod.GET)
    public List<Menu> getMenuTrees() {
        return menuService.getMenuTrees();
    }

    /**
     * 获取所有根menu
     *
     * @return
     */
    @RequestMapping(value = "/root", method = RequestMethod.GET)
    public List<Menu> getRootMenus() {
        return menuService.findParentMenus();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveMenu(@RequestBody Menu menu) {
        menuService.saveMenu(menu);
        return ResponseEntity.getSuccessResponse(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Menu getMenu(@PathVariable long id) {
        return menuService.getMenu(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateMenu(@PathVariable long id, @RequestBody Menu menu) {
        menu.setId(id);
        menuService.updateMenu(menu);
        return ResponseEntity.getSuccessResponse(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMenu(@PathVariable long id) {
        return menuService.deleteMenu(id);
    }

}
