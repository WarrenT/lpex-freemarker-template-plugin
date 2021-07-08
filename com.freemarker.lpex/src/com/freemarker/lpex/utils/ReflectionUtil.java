package com.freemarker.lpex.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.freemarker.lpex.userdefined.IUserType;
import com.freemarker.lpex.userdefined.Parameter;

public final class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static void setParameterValues(IUserType anObject, Map<String, Object> aValues) throws NoSuchMethodException, IllegalAccessException,
        InvocationTargetException {
        List<String> tAttributes = getParameterAttributes(anObject);
        if (tAttributes.size() == 0) {
            return;
        }

        for (String tAttribute : tAttributes) {
            if (aValues.containsKey(tAttribute)) {
                Object tValue = aValues.get(tAttribute);
                setAttribute(anObject, tAttribute, tValue);
            }
        }
    }

    public static List<String> getParameterAttributes(IUserType anObject) {

        List<String> tAttributes = new ArrayList<String>();

        Class<?> tClass = anObject.getClass();
        Field tFields[] = tClass.getDeclaredFields();
        for (Field tField : tFields) {
            Parameter tAnnotation = tField.getAnnotation(Parameter.class);
            if (tAnnotation != null) {
                tAttributes.add(tField.getName());
            }
        }
        return tAttributes;
    }

    public static void setAttribute(IUserType anObject, String anAttribute, Object aValue) throws NoSuchMethodException, IllegalAccessException,
        InvocationTargetException {
        Class<?> tClass = anObject.getClass();
        String tMethodeName = "set" + anAttribute.substring(0, 1).toUpperCase();
        if (anAttribute.length() > 1) {
            tMethodeName = tMethodeName + anAttribute.substring(1);
        }

        Method[] tAllMethods = tClass.getDeclaredMethods();
        for (Method tMethod : tAllMethods) {
            if (!tMethodeName.equalsIgnoreCase(tMethod.getName()) || (tMethod.getGenericReturnType() != void.class)) {
                continue;
            }
            Type[] tParameterType = tMethod.getGenericParameterTypes();
            if ((tParameterType.length != 1) || !Object.class.isAssignableFrom(tParameterType[0].getClass())) {
                continue;
            }

            tMethod.setAccessible(true);
            tMethod.invoke(anObject, aValue);
        }
    }
}
