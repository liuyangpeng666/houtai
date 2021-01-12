package com.jk.dao;

import com.jk.pojo.RoleBean;
import com.jk.pojo.TreeBean;
import com.jk.pojo.UserBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-29 16:02
 */
@Repository
public interface UserDao {
    @Select("select * from t_user where userName=#{userName}")
    UserBean findUserByName(String userName);

    @Select("select u.*,GROUP_CONCAT(r.rolename)as rolename from t_user u LEFT JOIN t_role_user ru on u.id=ru.userid LEFT JOIN t_role r on r.roleid=ru.roleid GROUP BY u.id")
    List<UserBean> findUser();

    @Select("select * from t_role")
    List<RoleBean> findRole();

    void delRole(Integer id);

    void addUser(UserBean user);

    void updUser(UserBean user);

    void addUserRole(@Param("arr") String[] roldidarr, @Param("userid") Integer id);

    UserBean findUserById(Integer id);

    List<TreeBean> DongTaiTree(@Param("pid") int pid, @Param("userid") Integer userId);

    List<TreeBean> findPowerByRoleid(Integer roleid);

    List<TreeBean> DongTaiTrees(int pid);

    void delById(Integer roleid);

    void saveRolePower(@Param("roleid")Integer roleid,@Param("treeid") String[] treeids);

    List<String> findPowerByUserId(Integer id);

    List<String> findRoleByUserId(Integer id);

    @Delete("delete from t_user where id=#{id}")
    void delUserById(Integer id);

    @Delete("delete from t_role_user where id=#{userid}")
    void delRoleUserByUserId(Integer id);
}
