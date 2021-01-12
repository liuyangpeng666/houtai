package com.jk.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-29 16:00
 */
@Data
public class UserBean implements Serializable {
    private static final long serialVersionUID = 4726335016521237809L;
    private Integer id;
    private String userName;
    private String userPwd;

    //业务字段
    private String rolename;//角色
    private String roleid;
}
