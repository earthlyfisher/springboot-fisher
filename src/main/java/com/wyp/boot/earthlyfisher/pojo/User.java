package com.wyp.boot.earthlyfisher.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by earthlyfisher on 2017/3/27.
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable, Cloneable {

    /**
     * 静态long类型常量serialVersionUID的作用：
     * <p>
     * 显示的设置serialVersionUID值就可以保证版本的兼容性，如果你在类中写上了这个值，就算类变动了，
     * 它反序列化的时候也能和文件中的原值匹配上。而新增的值则会设置成零值，删除的值则不会显示。
     */
    private static final long serialVersionUID = -8220100956296048447L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 32)
    private String password;

    @Column(length = 32)
    private String salt;

    @Column(precision = 3)
    private int age;

    /**
     * 关系维护端，负责多对多关系的维护
     *
     * @JoinTable 表示关联表的信息，其中：
     * 1.name 表示关联表的名字
     * 2.joinColumns 指定外键的名字，关联到关系维护端Role
     * 3.inverseJoinColumns 指定外键的名字，要关联的关系被维护端
     * 以上完全可以默认，默认情况下：
     * 1.name 主表名_从表名
     * 2.joinColumns 主表_id
     * 3.inverseJoinColumns 从表_id
     */
    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * 关系维护端，负责多对多关系的维护
     *
     * @JoinTable 表示关联表的信息，其中：
     * 1.name 表示关联表的名字
     * 2.joinColumns 指定外键的名字，关联到关系维护端Role
     * 3.inverseJoinColumns 指定外键的名字，要关联的关系被维护端
     * 以上完全可以默认，默认情况下：
     * 1.name 主表名_从表名
     * 2.joinColumns 主表_id
     * 3.inverseJoinColumns 从表_id
     */
    @ManyToMany
    @JoinTable(name = "t_user_menu", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<Menu> userMenus = new HashSet<Menu>();

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, String password, String salt, int age) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Menu> getUserMenus() {
        return userMenus;
    }

    public void setUserMenus(Set<Menu> userMenus) {
        this.userMenus = userMenus;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (this.name != other.getName())
            return false;
        return true;
    }

    /**
     * 将数据库用户转为rest输出用户
     *
     * @param users
     * @return
     */
    public static List<User> convertDbUser2OutUser(List<User> users) {
        List<User> outUsers = new ArrayList<>(users.size());
        try {
            for (int i = 0; i < users.size(); i++) {
                outUsers.add(users.get(i).clone());
            }
        } catch (CloneNotSupportedException e) {

        }
        return outUsers;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User user = new User(this.getName(), this.getAge());
        user.setId(this.getId());
        user.setRoles(this.getRoles());
        user.setUserMenus(this.getUserMenus());
        return user;
    }
}
