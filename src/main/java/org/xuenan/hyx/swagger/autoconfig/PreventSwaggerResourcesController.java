package org.xuenan.hyx.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author changyanan1
 * @version 1.0.0
 * @Description TODO
 * @date 2019年09月05日 14:23:00
 */
public class PreventSwaggerResourcesController {
    private static final Logger log = LoggerFactory.getLogger(PreventSwaggerResourcesController.class);

    @RequestMapping("/swagger-ui.html")
    public void swaggerIndex() {
        log.info("swagger api文档未开放,不允许访问");
    }

    @RequestMapping("/webjars/springfox-swagger-ui/**")
    public void swaggerResources() {
        log.info("swagger api文档未开放,不允许访问");
    }
}