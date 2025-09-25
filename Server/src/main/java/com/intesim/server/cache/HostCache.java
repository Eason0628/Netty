package com.intesim.server.cache;

import com.intesim.entity.HostEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * host缓存
 *

 */
@Component("hostCache")
public class HostCache {

    private static final Set<HostEntity> hostCache = new HashSet<>();

    private static final Map<String,Object> hostInfoCache=new HashMap<>();

    public Map<String, Object> getHostInfoCache() {
        return hostInfoCache;
    }

    public void addHostInfoCache(String key, Object value) {
        hostInfoCache.put(key, value);
    }

    public void removeHostInfoCache(String key) {
        hostInfoCache.remove(key);
    }


    /**
     * 添加HOST
     *
     * @param host host相关数据实体
     */
    public void addHost(HostEntity host) {
        hostCache.add(host);
    }

    /**
     * 删除Host
     *
     * @param host host相关数据实体
     */
    public void removeHost(HostEntity host) {
        hostCache.remove(host);
    }

    /**
     * 获取Host列表
     *
     * @return 已注册Host集合
     */
    public Set<HostEntity> getHostList() {
        return hostCache;
    }

    /**
     * 根据IP地址获取Host端实体
     *
     * @param ipAddress IP地址
     * @return Host实体
     */
    public HostEntity getHostByIpAddress(@NonNull String ipAddress) {
        for (HostEntity temp : hostCache) {
            if (ipAddress.equals(temp.getIp())) {
                return temp;
            }
        }
        return null;
    }
}
