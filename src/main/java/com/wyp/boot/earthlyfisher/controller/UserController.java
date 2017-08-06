package com.wyp.boot.earthlyfisher.controller;

import com.wyp.boot.earthlyfisher.common.CommonConstant;
import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.domain.UserDomain;
import com.wyp.boot.earthlyfisher.pojo.Menu;
import com.wyp.boot.earthlyfisher.pojo.User;
import com.wyp.boot.earthlyfisher.service.UserService;
import com.wyp.boot.earthlyfisher.validator.UserCUValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by earthlyfisher on 2017/4/4.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Autowired
    private UserCUValidator userCUValidator;

    /**
     * 在Spring的数据绑定器中进行注册，而注册时机是特定于控制器的
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userCUValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody @Valid UserDomain domain) {
        ResponseEntity responseEntity = userService.addUser(domain);
        return responseEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDomain domain) {
        domain.getUser().setId(id);
        ResponseEntity responseEntity = userService.updateUser(domain);
        return responseEntity;
    }

    /**
     * modify password
     *
     * @param domain
     * @return
     */
    @RequestMapping(value = "/{id}", params = {"type=password"}, method = RequestMethod.PUT)
    public ResponseEntity updatePassword(@PathVariable Long id, @RequestBody @Valid UserDomain domain) {
        domain.getUser().setId(id);

        return userService.updatePassword(domain);
    }

    /**
     * modify password
     *
     * @param domain
     * @return
     */
    @RequestMapping(value = "/{id}", params = {"type=role"}, method = RequestMethod.PUT)
    public ResponseEntity updateRoles(@PathVariable Long id, @RequestBody UserDomain domain) {
        domain.getUser().setId(id);
        return userService.updateUserRoles(domain);
    }

    /**
     * modify password.
     *
     * @param domain
     * @return
     */
    @RequestMapping(value = "/{id}", params = {"type=menu"}, method = RequestMethod.PUT)
    public ResponseEntity updateMenus(@PathVariable Long id, @RequestBody UserDomain domain) {
        domain.getUser().setId(id);
        return userService.updateUserMenus(domain);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginUser(@RequestBody @Valid User user, HttpServletRequest request) {
        ResponseEntity responseEntity = userService.loginUser(user);
        if (Boolean.parseBoolean(responseEntity.getFlag())) {
            user.setPassword(null);
            HttpSession session = request.getSession();//sample as request.getSession(true)
            session.setAttribute(CommonConstant.SESSION_CURRENT_USER, user);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseEntity logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return ResponseEntity.getSuccessResponse("");
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public List<Menu> getUserMenus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute(CommonConstant.SESSION_CURRENT_USER);
            if (user != null) {
                return userService.getUserMenus(user.getName());
            }
        }
        return Collections.emptyList();
    }


    /**
     * 设置用户权限.
     *
     * @param id
     * @param domain
     * @return
     */
    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.PUT)
    public ResponseEntity updateUserPermission(@PathVariable long id, @RequestBody UserDomain domain) {
        return userService.updatePermissions(id, domain.getMenuIdLst());
    }

    /**
     * 获取用户权限.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
    public Set<Menu> getUserPermission(@PathVariable long id) {
        return userService.getUser(id).getUserMenus();
    }
}
