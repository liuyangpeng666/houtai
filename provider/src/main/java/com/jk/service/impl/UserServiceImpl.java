package com.jk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jk.dao.UserDao;
import com.jk.pojo.RoleBean;
import com.jk.pojo.TreeBean;
import com.jk.pojo.UserBean;
import com.jk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-29 16:02
 */
@Service
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserBean findUserByName(String userName) {
        return userDao.findUserByName(userName);
    }

    @Override
    public List<UserBean> findUser() {
        return userDao.findUser();
    }

    @Override
    public List<RoleBean> findRole() {
        return userDao.findRole();
    }

    @Override
    public void addUser(UserBean user) {
        Integer id = user.getId();
        if(id!=null){
            userDao.updUser(user);
            userDao.delRole(id);
        }else{
            userDao.addUser(user);
        }
        String roleid = user.getRoleid();
        String[] roldidarr = roleid.split(",");
        userDao.addUserRole(roldidarr,user.getId());
    }

    @Override
    public UserBean findUserById(Integer id) {
        UserBean user =userDao.findUserById(id);
        return user;
    }

    @Override
    public List<TreeBean> DiGuiTree(Integer userId) {
        int pid=0;
        List<TreeBean> list =findNode(pid,userId);
        return list;
    }

    private List<TreeBean> findNode(int pid, Integer userId) {
        List<TreeBean> list=userDao.DongTaiTree(pid,userId);
        for (TreeBean tree : list) {
            Integer id = tree.getId();
            List<TreeBean> list2 = findNode(id,userId);
            tree.setChildren(list2);
        }
        return list;
    }

    @Override
    public List<TreeBean> queryTree(Integer roleid) {
        List<TreeBean> rolepowerlist = userDao.findPowerByRoleid(roleid);
        int pid = 0;
        //查询一级节点
        //提取公共方法的快捷键：alt+shift+m
        List<TreeBean> list = findNodes(pid,rolepowerlist);

        //根节点对象
        TreeBean tree = new TreeBean();
        tree.setId(0);
        tree.setText("根节点");
        tree.setChildren(list);

        List<TreeBean> list2 = new ArrayList<TreeBean>();
        list2.add(tree);
        return list2;
    }

    private List<TreeBean> findNodes(int pid, List<TreeBean> rolepowerlist) {
        List<TreeBean> list = userDao.DongTaiTrees(pid);
        for (TreeBean treeBean : list) {
            Integer id = treeBean.getId();
            //查询对应的子节点
            List<TreeBean> nodes = findNodes(id,rolepowerlist);//递归：自己调自己
            treeBean.setChildren(nodes);
            for (TreeBean power : rolepowerlist) {
                if(nodes.size()<=0&&power.getId()==id){//没有子节点
                    treeBean.setChecked(true);
                }
            }
        }
        return list;
    }

    @Override
    public void saveRolePower(Integer roleid, String[] treeids) {
        userDao.delById(roleid);
        userDao.saveRolePower(roleid,treeids);
    }

    @Override
    public List<String> findPowerByUserId(Integer id) {
        List<String> list=userDao.findPowerByUserId(id);
        return list;
    }

    @Override
    public List<String> findRoleByUserId(Integer id) {
        List<String> list1= userDao.findRoleByUserId(id);
        return list1;
    }

    @Override
    public void deleteById(Integer id) {
        userDao.delUserById(id);
        userDao.delRoleUserByUserId(id);
    }

}
