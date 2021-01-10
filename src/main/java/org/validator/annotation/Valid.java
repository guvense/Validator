package org.validator.annotation;

import org.validator.generator.constant.ConditionRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Valid {
    String source() default "";
    ConditionRule condition() default ConditionRule.NOOP;
    Class<? extends Throwable> targetException() default Exception.class;
    String errorMessage() default "";
}
