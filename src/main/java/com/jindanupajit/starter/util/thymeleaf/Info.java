package com.jindanupajit.starter.util.thymeleaf;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Info {
    public static final String expressionObjectName = "info";


        public static final ActionType PERSIST = ActionType.PERSIST;
        public static final ActionType MERGE = ActionType.MERGE;
        public static final ActionType DELETE = ActionType.DELETE;


    public String classNameOf(Object o) {
        return o.getClass().getName();
    }

    public List<FieldInfo> getAllSettableFieldOf(Object o) {
        HashSet<FieldInfo> settableFields = new HashSet<>();
        for(Method method : o.getClass().getDeclaredMethods()){
            String name = method.getName();

            if (!name.startsWith("set") || (name.length() < 4) || (method.getParameterTypes().length != 1))
                continue;

            Class type = method.getParameterTypes()[0];

            String fieldName = name.substring(3, 4).toLowerCase() + name.substring(4);

            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                if (field.getType() != type)
                    continue;

                System.out.println(fieldName);
                if (Modifier.isPublic(method.getModifiers())) {
                    FieldInfo fieldInfo = new FieldInfo(fieldName, type.getName().replace(".","-"));

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

                    if (field.isAnnotationPresent(UserInput.class)) {
                        UserInput userInput = field.getDeclaredAnnotation(UserInput.class);
                        fieldInfo.setOrdinal(userInput.Ordinal());
                        fieldInfo.setLabel(userInput.Label());
                        fieldInfo.setPlaceHolder(userInput.PlaceHolder());
                    }

                    settableFields.add(fieldInfo);
                }
            } catch (NoSuchFieldException e) {
                continue;
            }
        }

        ArrayList<FieldInfo> sortedSettableFields = new ArrayList<>(settableFields);
        Collections.sort(sortedSettableFields);
        return sortedSettableFields;
    }

    public ActionMapping getActionHandler(Object obj, ActionType actionType) {
        if (obj == null)
            return null;
        ActionMapping[] actionMappings = obj.getClass().getDeclaredAnnotationsByType(ActionMapping.class);
        System.out.println("Get ActionHandler of " + obj.getClass().getName());
        System.out.println("Contain "+actionMappings.length);
        for (ActionMapping actionMapping : actionMappings) {
            System.out.print(actionMapping.Url());
            List<ActionType> actionTypes = Arrays.asList(actionMapping.Action());
            if (actionTypes.contains(actionType)) {
                System.out.println(" yes");
                return actionMapping;
            }
            System.out.println(" no");
        }

        return null;
    }

}
