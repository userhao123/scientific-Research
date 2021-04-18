package com.hao.scientificresearch.aspect;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginLogAnno {
    String desc() default "";
}
