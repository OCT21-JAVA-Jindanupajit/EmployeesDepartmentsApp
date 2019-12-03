package com.jindanupajit.starter.util.thymeleaf;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInput {
    int Ordinal() default 0;
    String Label();
    String PlaceHolder();
}
