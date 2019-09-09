package com.github.changyanan.hyx.swagger.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 *  select FROM_UNIXTIME((1288711299999+(r.`request_id`>>22))/1000),request_id from biz_info_20180722 r LIMIT 1000; 反编译
 * @author changyanan1
 * @version 1.0.0
 */
@ConfigurationProperties(SwaggerProperties.prefix)
public class SwaggerProperties {

    public static final String prefix = "spring.swagger";
    /**
     * 启用swagger，如果不配置，spring.profiles中包含api依然会启用，如果配置为false，swagger不会启用，如果配置为true，swagger会启用
     */
    private Boolean enabled;

    /**
     * swagger2组配置
     */
    private Map<String, SwaggerGroupProperties> group;

    public static String getPrefix() {
        return prefix;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, SwaggerGroupProperties> getGroup() {
        return group;
    }

    public void setGroup(Map<String, SwaggerGroupProperties> group) {
        this.group = group;
    }
}
