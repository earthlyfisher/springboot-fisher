package com.wyp.boot.earthlyfisher.service.impl;

import com.wyp.boot.earthlyfisher.common.CommonConstant;
import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.dao.jpa.MenuRepository;
import com.wyp.boot.earthlyfisher.pojo.Menu;
import com.wyp.boot.earthlyfisher.service.MenuService;
import com.wyp.boot.earthlyfisher.util.CollectionsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by earthlyfisher on 2017/4/25.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuDao;

    @Override
    public List<Menu> getMenus() {
        Sort sort = new Sort(Sort.Direction.ASC, "id", "priority");
        List<Menu> menus = (List<Menu>) menuDao.findAll(sort);
        for (int i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            if (menu.getParantId() == 0) {
                menu.setParantName("");
                continue;
            }

            for (int j = 0; j < menus.size(); j++) {
                if (menu.getParantId() == menus.get(j).getId()) {
                    menu.setParantName(menus.get(j).getMenuName());
                }
            }
        }
        return menus;
    }

    /**
     * get menu trees
     *
     * @return List<Menu>
     */
    @Override
    public List<Menu> getMenuTrees() {
        Sort sort = new Sort(Sort.Direction.ASC, "id", "priority");
        List<Menu> menus = (List<Menu>) menuDao.findAll(sort);
        return getStructMenus(menus);
    }


    /**
     * get menu trees
     *
     * @return List<Menu>
     */
    @Override
    public List<Menu> getStructMenus(List<Menu> srcLst) {
        List<Menu> menuLst = new ArrayList<>();
        Collections.sort(srcLst, MenuTreesComparator.getComparator());
        if (!CollectionsUtils.isEmpty(srcLst)) {
            Iterator<Menu> iterator = srcLst.iterator();
            while (iterator.hasNext()) {
                Menu menu = iterator.next();
                structMenus(menu, menuLst);
            }
        }
        return menuLst;
    }

    /**
     * get root menus
     *
     * @return
     */
    @Override
    public List<Menu> findParentMenus() {
        return menuDao.findByParantId(CommonConstant.PARENT_MENU_ID);
    }

    @Override
    public Set<Menu> findMenuAndParant(List<Long> menuIds) {
        Set<Menu> menus = new HashSet<>();
        List<Long> parantIds = new ArrayList<>();

        Iterator<Menu> it = menuDao.findAll(menuIds).iterator();
        while (it.hasNext()) {
            Menu menu = it.next();
            menus.add(menu);
            parantIds.add(menu.getParantId());
        }
        menus.addAll((List<Menu>) menuDao.findAll(parantIds));
        return menus;
    }

    @Override
    public Menu getMenu(long id) {
        return menuDao.findOne(id);
    }

    @Override
    public Menu saveMenu(Menu menu) {
        return menuDao.save(menu);
    }

    @Override
    public Menu updateMenu(Menu menu) {
        return menuDao.save(menu);
    }

    @Override
    public ResponseEntity deleteMenu(long id) {
        ResponseEntity response = new ResponseEntity();

        //查询有无子目录
        List<Menu> menus = menuDao.findByParantId(id);
        if (menus.size() > 0) {
            response.setFlag("false");
            response.setErrCode("有子栏目，不能删除！");
            return response;
        }

        //有用户角色引用
        Menu menu = menuDao.findOne(id);
        if (menu.getUsers().size() > 0 || menu.getRoles().size() > 0) {
            response.setFlag("false");
            response.setErrCode("有用户或者角色使用该菜单，不能删除！");
            return response;
        }

        menuDao.delete(id);
        response.setFlag("true");
        return response;
    }

    private void structMenus(Menu menu, List<Menu> menuLst) {
        if (!menu.isEnabled()) {
            return;
        }

        //默认排序后第一条就是首页选项
        if (menuLst.size() == 0) {
            menuLst.add(menu);
            return;
        }

        if (menu.getParantId() == 0) {
            menuLst.add(menu);
            return;
        }

        for (Menu menuParent : menuLst) {
            if (menuParent.getId() == menu.getParantId()) {
                if (CollectionsUtils.isEmpty(menuParent.getChildren())) {
                    List<Menu> childLst = new ArrayList<>();
                    childLst.add(menu);
                    menuParent.setChildren(childLst);
                } else {
                    menuParent.getChildren().add(menu);
                }
                return;
            }
        }
    }

}


/**
 * 结构化menu树比较器
 */
class MenuTreesComparator implements Comparator<Menu> {

    private static class Holder {
        public static MenuTreesComparator comparator = new MenuTreesComparator();
    }

    @Override
    public int compare(Menu o1, Menu o2) {
        if (o1 == o2) {
            return 0;
        }

        if (o1 == null) {
            return 1;
        }

        if (o2 == null) {
            return -1;
        }
        return (int) (o1.getParantId() - o2.getParantId());
    }

    public static MenuTreesComparator getComparator() {
        return Holder.comparator;
    }
}