package com.qingchen.study.reflection;

import com.alibaba.fastjson.util.ASMClassLoader;
import org.junit.Test;
import org.springframework.aop.framework.AopContext;

import java.lang.reflect.*;

/**
 * @ClassName Reflection
 * @description:
 * @author: WangChen
 * @create: 2020-08-19 09:51
 **/
public class Reflection {


    /**
     * 通过反射获取类的所有变量
     */
    @Test
    public void printFields(){
        //1.获取并输出类的名称
        Class mClass = ReflectionObject.class;
        System.out.println("类的名称：" + mClass.getName());

        //2.1 获取所有 public 访问权限的变量
        // 包括本类声明的和从父类继承的
        //Field[] fields = mClass.getFields();

        //2.2 获取所有本类声明的变量（不问访问权限）
        Field[] fields = mClass.getDeclaredFields();

        //3. 遍历变量并输出变量信息
        for (Field field : fields) {
            //获取访问权限并输出
            int modifiers = field.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            //输出变量的类型及变量名
            System.out.println(field.getType().getName()
                    + " " + field.getName());
        }
    }

    /**
     * 通过反射获取类的所有方法
     */
    @Test
    public void printMethods(){
        //1.获取并输出类的名称
        Class mClass = ReflectionObject.class;
        System.out.println("类的名称：" + mClass.getName());

        //2.1 获取所有 public 访问权限的方法
        //包括自己声明和从父类继承的
        Method[] mMethods = mClass.getMethods();

        //2.2 获取所有本类的的方法（不问访问权限）
        //Method[] mMethods = mClass.getDeclaredMethods();

        //3.遍历所有方法
        for (Method method : mMethods) {
            //获取并输出方法的访问权限（Modifiers：修饰符）
            int modifiers = method.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            //获取并输出方法的返回值类型
            Class returnType = method.getReturnType();
            System.out.print(returnType.getName() + " "
                    + method.getName() + "( ");
            //获取并输出方法的所有参数
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter:
                    parameters) {
                System.out.print(parameter.getType().getName()
                        + " " + parameter.getName() + ",");
            }
            //获取并输出方法抛出的异常
            Class[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length == 0){
                System.out.println(" )");
            }
            else {
                for (Class c : exceptionTypes) {
                    System.out.println(" ) throws "
                            + c.getName());
                }
            }
        }
    }


    /**
     * 访问对象的私有方法
     * 为简洁代码，在方法上抛出总的异常，实际开发别这样
     */
    @Test
    public void getPrivateMethod() throws Exception{
        //1. 获取 Class 类实例
        ReflectionObject testClass = new ReflectionObject();
        Class mClass = testClass.getClass();

        //2. 获取私有方法
        //第一个参数为要获取的私有方法的名称
        //第二个为要获取方法的参数的类型，参数为 Class...，没有参数就是null
        //方法参数也可这么写 ：new Class[]{String.class , int.class}
        Method privateMethod = mClass.getDeclaredMethod("test03", String.class, String.class);

        //3. 开始操作方法
        if (privateMethod != null) {
            //获取私有方法的访问权
            //只是获取访问权，并不是修改实际权限
            privateMethod.setAccessible(true);

            //使用 invoke 反射调用私有方法
            //privateMethod 是获取到的私有方法
            //testClass 要操作的对象
            //后面两个参数传实参
            Object invoke = privateMethod.invoke(testClass, "123", "456");
            System.out.println(invoke);
        }
    }


    /**
     * 修改对象私有变量的值
     * 为简洁代码，在方法上抛出总的异常
     */
    @Test
    public void modifyPrivateFiled() throws Exception {
        //1. 获取 Class 类实例
        ReflectionObject testClass = new ReflectionObject();
        Class mClass = testClass.getClass();


        //2. 获取私有变量
        Field privateField = mClass.getDeclaredField("msg");

        //3. 操作私有变量
        if (privateField != null) {
            //获取私有变量的访问权
            privateField.setAccessible(true);

            //修改私有变量，并输出以测试
            System.out.println("Before Modify：MSG = " + testClass.getMsg());

            //调用 set(object , value) 修改变量的值
            //privateField 是获取到的私有变量
            //testClass 要操作的对象
            //"Modified" 为要修改成的值
            privateField.set(testClass, "Modified");
            System.out.println("After Modify：MSG = " + testClass.getMsg());
        }

    }


    /**
     * 修改对象私有常量的值
     * 为简洁代码，在方法上抛出总的异常，实际开发别这样
     */
    @Test
    public void modifyFinalFiled() throws Exception {
        //1. 获取 Class 类实例
        ReflectionObject testClass = new ReflectionObject();
        Class mClass = testClass.getClass();

        //2. 获取私有常量
        Field finalField = mClass.getDeclaredField("FINAL_VALUE");

        //3. 修改常量的值
        if (finalField != null) {

            //获取私有常量的访问权
            finalField.setAccessible(true);

            //调用 finalField 的 getter 方法
            //输出 FINAL_VALUE 修改前的值
            System.out.println("Before Modify：FINAL_VALUE = "
                    + finalField.get(testClass));

            //修改私有常量
            finalField.set(testClass, "Modified");

            //调用 finalField 的 getter 方法
            //输出 FINAL_VALUE 修改后的值
            System.out.println("After Modify：FINAL_VALUE = "
                    + finalField.get(testClass));

            //使用对象调用类的 getter 方法
            //获取值并输出
            System.out.println("Actually ：FINAL_VALUE = "
                    + testClass.getFinalValue());
        }
    }


    @Test
    public void declaredConstructor() throws Exception {
        //1. 获取 Class 类实例
        ReflectionObject testClass = new ReflectionObject();
        Class mClass = testClass.getClass();

        Constructor declaredConstructor = mClass.getDeclaredConstructor(String.class);

        declaredConstructor.setAccessible(true);
        Object o = declaredConstructor.newInstance("124");

        System.out.println(o.toString());


        Object o1 = AopContext.currentProxy();

    }

}
