package com.wyp.boot.earthlyfisher.service.impl;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.dao.jpa.MenuRepository;
import com.wyp.boot.earthlyfisher.dao.jpa.RoleRepository;
import com.wyp.boot.earthlyfisher.dao.jpa.UserRepository;
import com.wyp.boot.earthlyfisher.domain.UserDomain;
import com.wyp.boot.earthlyfisher.pojo.Menu;
import com.wyp.boot.earthlyfisher.pojo.Role;
import com.wyp.boot.earthlyfisher.pojo.User;
import com.wyp.boot.earthlyfisher.service.MenuService;
import com.wyp.boot.earthlyfisher.service.UserService;
import com.wyp.boot.earthlyfisher.util.CollectionsUtils;
import com.wyp.boot.earthlyfisher.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by earthlyfisher on 2017/4/4.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Override
    public List<User> getUsers() {
        return User.convertDbUser2OutUser((List<User>) userRepository.findAll());
    }

    @Override
    public User getUser(Long id) {
        User user = null;
        try {
            user = userRepository.findOne(id).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 添加用户
     *
     * @param domain
     * @return
     */
    @Transactional
    @Override
    public ResponseEntity addUser(UserDomain domain) {

        User user = domain.getUser();
        //1.get salt
        String seed = PasswordUtil.randomSalt();
        user.setSalt(seed);

        //2.encode password
        String encryPassword = PasswordUtil.generatePassword(domain.getUser().getPassword(), seed);

        //3.save user info
        user.setPassword(encryPassword);
        user.setRoles(new HashSet((List<Role>) roleRepository.findAll(domain.getRoleIdLst())));
        userRepository.save(user);
        return ResponseEntity.getSuccessResponse("");
    }

    /**
     * 修改用户
     *
     * @param domain
     * @return
     */
    @Transactional
    @Override
    public ResponseEntity updateUser(UserDomain domain) {
        User user = domain.getUser();
        User originUser = userRepository.findOne(user.getId());
        //没有更改的属性直接跳过
        if (user.getAge() != originUser.getAge()) {
            originUser.setAge(user.getAge());
        }

        //1.添加用户角色
        if (!CollectionsUtils.isEmpty(domain.getRoleIdLst())) {
            List<Role> roles = (List<Role>) roleRepository.findAll(domain.getRoleIdLst());
            originUser.setRoles(new HashSet<>(roles));
        }

        userRepository.save(originUser);
        return ResponseEntity.getSuccessResponse("");
    }

    /**
     * 修改用户密码
     *
     * @param domain
     * @return
     */
    @Override
    public ResponseEntity updatePassword(UserDomain domain) {
        ResponseEntity response = new ResponseEntity();

        //如果输入框新旧密码同，则报错
        if (domain.getOriginPwd().equals(domain.getUser().getPassword())) {
            response.setFlag("false");
            response.setErrCode("old&new is sample");
            return response;
        }

        //如果旧密码和数据库中的不一致，则报错
        User originUser = userRepository.findOne((long) domain.getUser().getId());
        String oldPwd = PasswordUtil.generatePassword(domain.getOriginPwd(), originUser.getSalt());
        if (!oldPwd.equals(originUser.getPassword())) {
            response.setFlag("false");
            response.setErrCode("old password is error");
            return response;
        }

        //update user
        String newPwd = PasswordUtil.generatePassword(domain.getUser().getPassword(), originUser.getSalt());
        originUser.setPassword(newPwd);
        userRepository.save(originUser);
        return ResponseEntity.getSuccessResponse("");
    }

    /**
     * 修改用户角色
     *
     * @param domain
     * @return
     */
    @Transactional
    @Override
    public ResponseEntity updateUserRoles(UserDomain domain) {
        List<Role> roles = (List<Role>) roleRepository.findAll(domain.getRoleIdLst());
        User user = domain.getUser();
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
        return ResponseEntity.getSuccessResponse("");
    }

    /**
     * 修改用户菜单
     *
     * @param domain
     * @return
     */
    @Transactional
    @Override
    public ResponseEntity updateUserMenus(UserDomain domain) {
        List<Menu> menus = (List<Menu>) menuRepository.findAll(domain.getMenuIdLst());
        User user = domain.getUser();
        user.setUserMenus(new HashSet<>(menus));
        userRepository.save(user);
        return ResponseEntity.getSuccessResponse("");
    }

    /**
     * 返回用户的所有菜单(自己的以及所属角色下的)
     *
     * @param name
     * @return
     */
    public List<Menu> getUserMenus(String name) {
        User user = userRepository.findByName(name);
        Set<Menu> menus = new HashSet<>();
        menus.addAll(user.getUserMenus());
        for (Role role : user.getRoles()) {
            menus.addAll(role.getRoleMenus());
        }
        return menuService.getStructMenus(new ArrayList<>(menus));
    }

    @Override
    public ResponseEntity deleteUser(long id) {
        userRepository.delete(id);
        return ResponseEntity.getSuccessResponse("");
    }

    @Override
    public ResponseEntity loginUser(User user) {
        ResponseEntity response = new ResponseEntity();
        boolean loginSuccess = authUser(user);
        if (loginSuccess) {
            response.setFlag("true");
        } else {
            response.setFlag("false");
            response.setErrCode("用户名或密码错误!");
        }
        return response;
    }

    @Override
    public ResponseEntity updatePermissions(long id, List<Long> menuIds) {
        User user = userRepository.findOne(id);
        user.setUserMenus(menuService.findMenuAndParant(menuIds));
        userRepository.save(user);
        return ResponseEntity.getSuccessResponse("");
    }

    private boolean authUser(User user) {
        User dbUser = userRepository.findByName(user.getName());
        if (dbUser == null) {
            return false;
        }

        String pwd = PasswordUtil.generatePassword(user.getPassword(), dbUser.getSalt());
        if (pwd.equals(dbUser.getPassword())) {
            return true;
        }
        return false;
    }
}
