package org.xuenan.hyx.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.xuenan.hyx.swagger.properties.SwaggerGroupProperties;
import org.xuenan.hyx.swagger.properties.SwaggerProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author changyanan1
 * @version 1.0.0
 */
@EnableSwagger2
public class SwaggerDocketConfiguration implements BeanFactoryPostProcessor, EnvironmentAware {
    private static final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SwaggerProperties swaggerProperties = getSwaggerProperties();
        List<String> pathUrls = new ArrayList<>();
        if (swaggerProperties.getGroup() != null && !swaggerProperties.getGroup().isEmpty()) {
            Iterator<Map.Entry<String, SwaggerGroupProperties>> it = swaggerProperties.getGroup().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, SwaggerGroupProperties> entry = it
                        .next();
                Docket swagger2Docket = this.getSwaggerDocket(entry.getValue(), pathUrls);
                beanFactory.registerSingleton(entry.getKey(), swagger2Docket);
            }
        }
        beanFactory.registerSingleton("other-api", this.getOtherSwaggerDocket(pathUrls));
    }

    /**
     * 解析配置文件
     *
     * @return
     */
    private SwaggerProperties getSwaggerProperties() {
        try {
            SwaggerProperties existingValue = Binder.get(environment).bind(SwaggerProperties.prefix, SwaggerProperties.class).get();
            return existingValue;
        } catch (Exception e) {
            return new SwaggerProperties();
        }
    }

    /**
     * 构建swagger对象
     *
     * @param groupProperties
     * @param pathUrls
     * @return
     */
    private Docket getSwaggerDocket(final SwaggerGroupProperties groupProperties, final List<String> pathUrls) {
        final String[] basePackages = this.splitBasePackages(groupProperties.getBasePackage());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title(groupProperties.getTitle())
                        .description(groupProperties.getDescription()).version(groupProperties.getVersion())
                        .license(groupProperties.getLicense()).licenseUrl(groupProperties.getLicenseUrl())
                        .termsOfServiceUrl(groupProperties.getTermsOfServiceUrl())
                        .contact(groupProperties.getContact().toContact()).build())
                // 最终调用接口后会和paths拼接在一起
                .groupName(groupProperties.getGroupName()).pathMapping(groupProperties.getPathMapping())
                .select().apis(input -> {
                    if (basePackages == null) {
                        return true;
                    }
                    String packageName = input.declaringClass().getName();
                    for (String basePackage : basePackages) {
                        if (packageName.startsWith(basePackage) || packageName.matches(basePackage + ".*")) {
                            return true;
                        }
                    }
                    return false;
                }).paths(input -> {
                    String pathRegex = groupProperties.getPathRegex();
                    if (StringUtils.isEmpty(pathRegex) || input.matches(pathRegex)) {
                        pathUrls.add(input);
                        return true;
                    } else {
                        return false;
                    }
                }).build();
    }

    /**
     * 分隔不同包
     *
     * @param basePackage
     * @return
     */
    private String[] splitBasePackages(String basePackage) {
        if (StringUtils.isEmpty(basePackage) || (basePackage = basePackage.trim()).isEmpty()) {
            return null;
        } else {
            return basePackage.split(",");
        }
    }

    /**
     * 未配置的api
     *
     * @return
     */
    private SwaggerGroupProperties otherSwagger() {
        SwaggerGroupProperties otherSwagger = new SwaggerGroupProperties();
        otherSwagger.setGroupName("other-api");
        otherSwagger.setDescription("以上api中未被包含进来得接口");
        otherSwagger.setPathMapping("/");
        otherSwagger.setVersion("");
        otherSwagger.setPathRegex(null);
        otherSwagger.setTitle("其它api");
        return otherSwagger;
    }

    private Docket getOtherSwaggerDocket(final List<String> pathUrls) {
        SwaggerGroupProperties otherSwagger = otherSwagger();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title(otherSwagger.getTitle()).description(otherSwagger.getDescription())
                        .termsOfServiceUrl(otherSwagger.getTermsOfServiceUrl()).version(otherSwagger.getVersion())
                        .contact(otherSwagger.getContact().toContact()).license(otherSwagger.getLicense())
                        .licenseUrl(otherSwagger.getLicense()).build())
                // 最终调用接口后会和paths拼接在一起
                .groupName(otherSwagger.getGroupName()).pathMapping(otherSwagger.getPathMapping())
                .select().apis(input -> input.isAnnotatedWith(GetMapping.class) || input.isAnnotatedWith(PostMapping.class)
                        || input.isAnnotatedWith(DeleteMapping.class) || input.isAnnotatedWith(PutMapping.class)
                        || input.isAnnotatedWith(RequestMapping.class)).paths(input -> !pathUrls.contains(input)).build();
    }
}
