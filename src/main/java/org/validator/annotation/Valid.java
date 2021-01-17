package org.validator.annotation;

import org.validator.generator.constant.ConditionRule;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@Repeatable(Valids.class)
public @interface Valid {
    String source();
    ConditionRule condition();
    Class<? extends Throwable> targetException() default Exception.class;
    String errorMessage() default "";
}
