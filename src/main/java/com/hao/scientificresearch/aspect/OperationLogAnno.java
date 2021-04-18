package com.hao.scientificresearch.aspect;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogAnno {
    String desc() default "";
}
