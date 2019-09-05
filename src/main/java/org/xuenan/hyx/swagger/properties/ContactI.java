package org.xuenan.hyx.swagger.properties;

import springfox.documentation.service.Contact;

import java.io.Serializable;

/**
 * @author changyanan1
 * @version 1.0.0
 * @Description TODO
 * @date 2019年09月05日 12:05:00
 */
public class ContactI implements Serializable {
    /**
     * 联系的名字
     */
    private String name;
    /**
     * 联系的邮箱
     */
    private String email;
    /**
     * 联系的地址
     */
    private String url;

    public Contact toContact() {
        return new Contact(name, url, email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
