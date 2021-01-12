package com.jk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jk.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jk.pojo.RoleBean;
import com.jk.pojo.TreeBean;
import com.jk.pojo.UserBean;
import java.util.List;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-29 16:01
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    @Bean(value = "userService")
    public UserService getUserService(){
        return userService;
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("toIndex")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("show")
    @RequiresPermissions("user:show")
    public String show(){
        return "show";
    }

    @RequestMapping("show1")
    //如果有多个权限/角色验证的时候中间用“，”隔开，默认是所有列出的权限/角色必须同时满足才生效。
    // 但是在注解中有logical = Logical.OR这块。这里可以让权限控制更灵活些。
    //如果将这里设置成OR，表示所列出的条件只要满足其中一个就可以，
    // 如果不写或者设置成logical = Logical.AND，表示所有列出的都必须满足才能进入方法。
    @RequiresRoles(value={"svip","vip"},logical = Logical.OR)
    //@RequiresPermissions(value={"svip","vip"},logical = Logical.AND)
    public String show1(){
        return "show1";
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public String login(UserBean user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken upt = new UsernamePasswordToken(user.getUserName(), user.getUserPwd());
        try {
            subject.login(upt);
            return "登录成功";
        }catch (UnknownAccountException e){
            return "账号不存在";
        }catch (IncorrectCredentialsException e){
            return "密码错误";
        }catch (AuthenticationException e){
            return "用户认证失败";
        }
    }

    /**
     * 查询用户角色
     * @return
     */
    @RequestMapping("findUser")
    @ResponseBody
    public List<UserBean> findUser(){
        return userService.findUser();
    }

    /**
     * 查询角色表
     * @return
     */
    @RequestMapping("findRole")
    @ResponseBody
    public List<RoleBean> findRole(){
        return userService.findRole();
    }

    /**
     * 用户附角色
     * @param user
     */
    @RequestMapping("addUser")
    @ResponseBody
    public void addUser(UserBean user){
        userService.addUser(user);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @RequestMapping("findUserById")
    @ResponseBody
    public UserBean findUserById(Integer id){
        return userService.findUserById(id);
    }

    /**
     * 树
     * @return
     */
    @RequestMapping("DiGuiTree")
    @ResponseBody
    public List<TreeBean>DiGuiTree(){
        Subject subject = SecurityUtils.getSubject();
        UserBean user = (UserBean) subject.getPrincipal();
        return userService.DiGuiTree(user.getId());
    }

    /**
     * 权限树
     * @param roleid
     * @return
     */
    @RequestMapping("queryTree")
    @ResponseBody
    public List<TreeBean>queryTree(Integer roleid){
        return userService.queryTree(roleid);
    }

    /**
     * 修改用户权限
     * @param roleid
     * @param treeids
     */
    @RequestMapping("saveRolePower")
    @ResponseBody
    public void saveRolePower(Integer roleid,String []treeids ){
        userService.saveRolePower(roleid,treeids);
    }

    /**
     * 删除用户
     * @param id
     */
    @RequestMapping("deleteById")
    @ResponseBody
    public void deleteById(Integer id){
        userService.deleteById(id);
    }

}
