package com.hewking.custom.util;

import java.lang.reflect.Field;

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/9 0009
 * 修改人员：hewking
 * 修改时间：2019/1/9 0009
 * 修改备注：
 * Version: 1.0.0
 */
public class ReflectUtil {

    public ReflectUtil() throws IllegalAccessException {
        throw new IllegalAccessException("can't instiant the object");
    }

    public static void setField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        A a = new A();
        a.name = "hhh";
        ReflectUtil.setField(a, "name", "diaodiaodiao");
        System.out.println(a.name);
        System.out.println(ReflectUtil.getField(a, "name"));
    }

    static class A {
        private String name;
    }

}
