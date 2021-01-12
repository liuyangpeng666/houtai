package com.jk.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: shiro-maven-lyp
 * @description:
 * @author: 刘洋朋
 * @create: 2020-12-30 11:42
 */
@Data
public class TreeBean implements Serializable {

    private static final long serialVersionUID = -7470589936391688460L;
    private Integer id;
    private String text;
    private Integer pid;
    private String url;
    private List<TreeBean> children;
    private boolean checked;
    private String permission;




}
