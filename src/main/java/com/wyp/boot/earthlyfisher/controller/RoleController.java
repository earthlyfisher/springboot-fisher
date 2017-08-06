package com.wyp.boot.earthlyfisher.controller;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.pojo.Menu;
import com.wyp.boot.earthlyfisher.pojo.Role;
import com.wyp.boot.earthlyfisher.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by earthlyfisher on 2017/5/12.
 */

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Role getRole(@PathVariable long id) {
        return roleService.getRole(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateRole(@PathVariable long id, @RequestBody Role role) {
        role.setId(id);
        return roleService.updateRole(role);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRole(@PathVariable long id) {
        return roleService.deleteRole(id);
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.PUT)
    public ResponseEntity updateRolePermission(@PathVariable long id, @RequestBody List<Long> menuIds) {
        return roleService.updatePermissions(id, menuIds);
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
    public Set<Menu> getRolePermission(@PathVariable long id) {
        return roleService.getRole(id).getRoleMenus();
    }

}
