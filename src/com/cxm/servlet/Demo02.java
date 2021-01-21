package com.cxm.servlet;

/**
 * 不是传递参数 所以没有创建副本
 * 改变的就是实体
 * 测试用xml来封装对象
 */
public class Demo02 {
    public boolean i;
    public int a= 3;
    public void f(){
        i=true;
        a++;
    }
    public void f2(){
        i=false;
    }

    public static void main(String[] args) {
        Demo02 demo02 = new Demo02();
        System.out.println(demo02.i);
        System.out.println(demo02.a);
        demo02.f();
        System.out.println(demo02.a);
        System.out.println(demo02.i);
    }
}
