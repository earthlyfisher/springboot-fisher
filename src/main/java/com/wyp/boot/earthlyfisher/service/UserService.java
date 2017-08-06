package com.wyp.boot.earthlyfisher.service;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.domain.UserDomain;
import com.wyp.boot.earthlyfisher.pojo.Menu;
import com.wyp.boot.earthlyfisher.pojo.User;

import java.util.List;

/**
 * Created by earthlyfisher on 2017/4/4.
 */
public interface UserService {

    List<User> getUsers();

    User getUser(Long id);

    ResponseEntity addUser(UserDomain domain);

    ResponseEntity loginUser(User user);

    ResponseEntity updateUser(UserDomain domain);

    ResponseEntity updatePassword(UserDomain domain);

    ResponseEntity updateUserRoles(UserDomain domain);

    ResponseEntity updateUserMenus(UserDomain domain);

    ResponseEntity deleteUser(long id);

    List<Menu> getUserMenus(String name);

    ResponseEntity updatePermissions(long id, List<Long> menuIds);
}
