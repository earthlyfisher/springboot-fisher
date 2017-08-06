package com.wyp.boot.earthlyfisher.dao.jpa;

import com.wyp.boot.earthlyfisher.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by earthlyfisher on 2017/4/4.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * 此处根据JPA规范而来.
     *
     * @param name
     * @return
     */
    User findByName(String name);
}
