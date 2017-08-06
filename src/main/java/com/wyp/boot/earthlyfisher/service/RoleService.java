package com.wyp.boot.earthlyfisher.service;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.pojo.Role;

import java.util.List;

/**
 * Created by earthlyfisher on 2017/5/12.
 */
public interface RoleService {

    List<Role> getRoles();

    Role getRole(long id);

    ResponseEntity saveRole(Role role);

    ResponseEntity updateRole(Role role);

    ResponseEntity deleteRole(long id);

    ResponseEntity updatePermissions(long id, List<Long> menuIds);
}
