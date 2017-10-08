package com.hewking.androidview.chronomoter;

import java.lang.reflect.Field;

public class T {

    private int id = 50;

    public static void main(String[] args) {

        try {
            //创建类
//            Class<?> class1 = Class.forName("com.app.Person");
//
//            //创建实例
//            Object person = class1.newInstance();

            T t = new T();

            //获得id 属性
            Field idField = t.getClass().getDeclaredField("id") ;

            //打破封装  实际上setAccessible是启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问
            //由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
            idField.setAccessible( true );

            //给id 属性赋值
//            idField.set(  t , 100) ;

            //打印 person 的属性值
            System.out.println( idField.get( t ));

        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace() ;
        }

    }
}