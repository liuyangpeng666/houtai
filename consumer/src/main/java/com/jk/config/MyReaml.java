package com.jk.config;

import com.jk.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jk.pojo.UserBean;
import java.util.List;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-29 16:32
 */
public class MyReaml extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取登录信息
        UserBean user = (UserBean) principalCollection.getPrimaryPrincipal();

        //2.根据用户id查询用户拥有的权限集合
        //shiro路径（权限）的命名规则： 类路径：方法路径
        //login：queryTree  login: findRole
        UserService userService= SpringBeanFactoryUtils.getBean("userService", UserService.class);
        List<String> list= userService.findPowerByUserId(user.getId());
        //3.把权限数据交给shiro
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        sai.addStringPermissions(list);
        //根据用户id查询拥有的角色集合
        //把角色数据交给shiro
        List<String> list1=userService.findRoleByUserId(user.getId());
        sai.addRoles(list1);
        return sai;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户登录用户名
        String userName = (String) authenticationToken.getPrincipal();
        //通过工具类获取userService
        UserService userService= SpringBeanFactoryUtils.getBean("userService", UserService.class);
        //从数据库获取用户信息
        UserBean user =userService.findUserByName(userName);
        if(user==null){
            return null;
        }
        //用户名（存session）密码当前类名
        SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user,user.getUserPwd(),this.getName());
        return sai;
    }
}
