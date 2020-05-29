package com.sun.crowd.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * menu类
 * @author sun
 */
public class Menu {
    /**
     *
     */
    private Integer id;

    /**
     * 父节点id
     */
    private Integer pid;

    private String name;

    /**
     * 节点附带的url地址
     */
    private String url;

    /**
     * 图标样式
     */
    private String icon;

    /**
     * 存储子节点的集合
     */
    private List<Menu> children = new ArrayList<>();

    /**
     * 控制节点是否为开
     */
    private Boolean open;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}