package org.imzdong.study.msb.day_11_jvm;

public class StackDemo01 {

    public static void main(String[] args) {
        /**
         *  0 new #2 <org/imzdong/study/msb/day_11_jvm/Stack01> 新建对象并把地址压栈
         *  3 dup Duplicate the top operand stack value（复制栈顶值） 栈就有两个变量指向对象
         *  4 invokespecial #3 <org/imzdong/study/msb/day_11_jvm/Stack01.<init>> 取出栈顶的变量初始化
         *  7 astore_1 复制栈内值给局部变量表下表1的变量stack01
         *  8 aload_1 stack01入栈
         *  9 invokevirtual #4 <org/imzdong/study/msb/day_11_jvm/Stack01.m>
         * 12 return
         */
        Stack01 stack01 = new Stack01();
        stack01.m();
    }


}
class Stack01{

    public void m(){

    }
}
