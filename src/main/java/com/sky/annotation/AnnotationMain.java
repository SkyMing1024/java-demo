package com.sky.annotation;

import java.lang.annotation.Annotation;

public class AnnotationMain {

    public static void main(String[] args) {
        TestAnnotation annotation = AnnotationMain.class.getAnnotation(TestAnnotation.class);
        annotation.value();
    }

}
