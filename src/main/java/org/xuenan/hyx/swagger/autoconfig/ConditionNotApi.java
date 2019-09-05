package org.xuenan.hyx.swagger.autoconfig;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author changyanan1
 * @version 1.0.0
 * @Description 条件
 * @date 2019年09月05日 14:14:00
 */
public class ConditionNotApi implements Condition {
    ConditionApi conditionApi = new ConditionApi();

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return conditionApi.matches(context, metadata);
    }
}
