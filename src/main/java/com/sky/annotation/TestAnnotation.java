package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface TestAnnotation {
    String value();
    String name() default "默认值";
}
