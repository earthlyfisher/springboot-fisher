package com.wyp.boot.earthlyfisher.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_city")
public class City implements Serializable {
    /**
     * .
     */
    private static final long serialVersionUID = -626326854046453205L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;

    protected City() {
    }

    public City(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getState() {
        return this.state;
    }
}
