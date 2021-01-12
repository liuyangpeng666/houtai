package com.jk.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-29 21:39
 */
@Data
public class RoleBean implements Serializable {
    private static final long serialVersionUID = 1596803269978053618L;
    private Integer roleid;
    private String rolename;
    private String rank;
}
