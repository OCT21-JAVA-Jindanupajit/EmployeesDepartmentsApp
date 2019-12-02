package com.jindanupajit.starter.util.thymeleaf;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

public class Info {
    public static final String expressionObjectName = "info";

    public String classNameOf(Object o) {
        return o.getClass().getName();
    }

    public Iterable<FieldInfo> getAllSettableFieldOf(Object o) {
        HashSet<FieldInfo> settableFields = new HashSet<>();
        for(Field field : o.getClass().getDeclaredFields()){
            Class type = field.getType();
            String name = field.getName();
            String setter = "set"+name.substring(0, 1).toUpperCase() + name.substring(1);
            try {
                Method method = o.getClass().getDeclaredMethod(setter, type);
                if (Modifier.isPublic(method.getModifiers())) {
                    FieldInfo fieldInfo = new FieldInfo(name, type.getName().replace(".","-"));

                    // JPA
                    fieldInfo.setId(field.isAnnotationPresent(Id.class));
                    fieldInfo.setGeneratedValue(field.isAnnotationPresent(GeneratedValue.class));

                    // Validation
                    fieldInfo.setNotNull(field.isAnnotationPresent(NotNull.class));
                    fieldInfo.setNotEmpty(field.isAnnotationPresent(NotEmpty.class));
                    fieldInfo.setNotBlank(field.isAnnotationPresent(NotBlank.class));

                    if  (field.isAnnotationPresent(Min.class)) {
                        fieldInfo.setMin(true);
                        fieldInfo.setMinValue(field.getDeclaredAnnotation(Min.class).value());
                    }

                    if  (field.isAnnotationPresent(Max.class)) {
                        fieldInfo.setMax(true);
                        fieldInfo.setMaxValue(field.getDeclaredAnnotation(Max.class).value());
                    }

                    if  (field.isAnnotationPresent(Size.class)) {
                        fieldInfo.setSize(true);
                        fieldInfo.setSizeMinValue(field.getDeclaredAnnotation(Size.class).min());
                        fieldInfo.setSizeMaxValue(field.getDeclaredAnnotation(Size.class).max());
                    }

                    settableFields.add(fieldInfo);
                }
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return settableFields;
    }

}
