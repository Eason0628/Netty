package com.intesim.entity;

import java.util.Map;

/**
 * 计算端节点
 *
 */
public class HostDispatcherNode extends DispatcherNode {

    /**
     * 用户登录信息
     */
    private Map<String, Object> auth;
    /**
     * 共享目录关键字
     */
    private String shareKey;

    public Map<String, Object> getAuth() {
        return auth;
    }

    public void setAuth(Map<String, Object> auth) {
        this.auth = auth;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }
}
