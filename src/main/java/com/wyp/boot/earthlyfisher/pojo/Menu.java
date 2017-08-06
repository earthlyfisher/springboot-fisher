package com.wyp.boot.earthlyfisher.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by earthlyfisher on 2017/4/24.
 */
@Entity
@Table(name = "t_menu")
public class Menu implements Comparable<Menu> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String menuName;
    private String menuDesc;
    private int priority;
    private String staticIndex;
    private long parantId;
    @Transient
    private String parantName;
    @JsonFormat
    private boolean enabled;
    @Transient
    private List<Menu> children;

    //菜单所属role
    @JsonIgnore
    @ManyToMany(mappedBy = "roleMenus", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    //菜单所属role
    @JsonBackReference
    @ManyToMany(mappedBy = "userMenus", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Menu() {
    }

    public Menu(long id, String menuName, String menuDesc, int priority, String staticIndex, long parantId, boolean enabled) {
        this.id = id;
        this.menuName = menuName;
        this.menuDesc = menuDesc;
        this.priority = priority;
        this.staticIndex = staticIndex;
        this.parantId = parantId;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStaticIndex() {
        return staticIndex;
    }

    public void setStaticIndex(String staticIndex) {
        this.staticIndex = staticIndex;
    }

    public long getParantId() {
        return parantId;
    }

    public void setParantId(long parantId) {
        this.parantId = parantId;
    }

    public String getParantName() {
        return parantName;
    }

    public void setParantName(String parantName) {
        this.parantName = parantName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return this.getMenuName().hashCode();
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
        if (id != other.getId())
            return false;
        return true;
    }

    @Override
    public int compareTo(Menu o) {
        return (int) (this.getId() - o.getId());
    }
}

