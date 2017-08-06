package com.wyp.boot.earthlyfisher.dao.jpa;

import com.wyp.boot.earthlyfisher.pojo.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by earthlyfisher on 2017/4/4.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * in查询
     *
     * @param ids
     * @return
     */
    @Query("select t.id,t.roleName,t.roleDescription,t.enabled from Role t where t.id in (?1)")
    List<Role> findSomeRoles(List<Integer> ids);
}
