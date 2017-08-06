package com.wyp.boot.earthlyfisher.service.impl;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.dao.jpa.RoleRepository;
import com.wyp.boot.earthlyfisher.pojo.Role;
import com.wyp.boot.earthlyfisher.service.MenuService;
import com.wyp.boot.earthlyfisher.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

/**
 * Created by earthlyfisher on 2017/5/12.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuService menuService;

    @Override
    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Role getRole(long id) {
        return roleRepository.findOne(id);
    }

    @Transactional
    @Override
    public ResponseEntity saveRole(Role role) {
        roleRepository.save(role);
        return ResponseEntity.getSuccessResponse("");
    }

    @Transactional
    @Override
    public ResponseEntity updateRole(Role role) {
        roleRepository.save(role);
        return ResponseEntity.getSuccessResponse("");
    }

    @Override
    public ResponseEntity deleteRole(long id) {
        roleRepository.delete(id);
        return ResponseEntity.getSuccessResponse("");
    }

    @Override
    public ResponseEntity updatePermissions(long id, List<Long> menuIds) {
        Role role = roleRepository.findOne(id);
        role.setRoleMenus(menuService.findMenuAndParant(menuIds));
        roleRepository.save(role);
        return ResponseEntity.getSuccessResponse("");
    }
}
