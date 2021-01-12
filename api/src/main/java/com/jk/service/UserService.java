package com.jk.service;


import com.jk.pojo.RoleBean;
import com.jk.pojo.TreeBean;
import com.jk.pojo.UserBean;

import java.util.List;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-29 15:50
 */
public interface UserService {
    UserBean findUserByName(String userName);

    List<UserBean> findUser();

    List<RoleBean> findRole();

    void addUser(UserBean user);

    UserBean findUserById(Integer id);

    List<TreeBean> DiGuiTree(Integer userId);

    List<TreeBean> queryTree(Integer roleid);

    void saveRolePower(Integer roleid, String[] treeids);

    List<String> findPowerByUserId(Integer id);

    List<String> findRoleByUserId(Integer id);

    void deleteById(Integer id);
}
