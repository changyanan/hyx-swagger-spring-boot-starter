package com.github.changyanan.hyx.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changyanan1
 * @version 1.0.0
 */
@Configuration
public class SwaggerAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);
    @RestController
    @Conditional(ConditionNotApi.class)
    public static class PreventSwaggerUi extends PreventSwaggerResourcesController {
        {
            log.debug("启用了swagger文档11");

        }
    }
    @Configuration
    @Conditional(ConditionApi.class)
    public static class SwaggerDocket extends SwaggerDocketConfiguration {
        {
            log.debug("禁用了swagger文档html页面‘/swagger-ui.html’和其他资源的访问");
        }
    }


}
