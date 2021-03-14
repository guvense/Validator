package org.validator.annotation;

import org.validator.generator.constant.ConditionRule;
import org.validator.generator.constant.Pattern;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@Repeatable(Valids.class)
public @interface Valid {
    String source();
    ConditionRule condition() default ConditionRule.NOOP;
    Pattern pattern() default Pattern.NOOP;
    Class<? extends Throwable> targetException() default Exception.class;
    String errorMessage() default "";
}
