package org.validator.annotation;

import org.validator.constant.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Valid {
    String source() default "";
    Condition condition() default Condition.NOOP;
    Class<? extends Throwable> targetException() default Exception.class;
    String errorMessage() default "";
}
