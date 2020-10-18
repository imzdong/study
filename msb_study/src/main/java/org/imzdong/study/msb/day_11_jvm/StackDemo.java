package org.imzdong.study.msb.day_11_jvm;

public class StackDemo {

    public static void main(String[] args) {
        /**
         *  0 bipush 100  压100入栈
         *  2 istore_1    取出100给i
         *  3 iload_1     把100压栈
         *  4 iinc 1 by 1   i操作加1为101，此时操作栈为变更
         *  7 istore_1      取出栈的100复制i
         *  8 getstatic #2 <java/lang/System.out> 调用out方法
         * 11 iload_1 压i（100）入栈
         * 12 invokevirtual #3 <java/io/PrintStream.println> 100
         * 15 return
         */
        /*int i = 100;
        i = i++;
        System.out.println(i);*/

        /**
         *  0 bipush 100
         *  2 istore_1
         *  3 iinc 1 by 1
         *  6 iload_1
         *  7 istore_1
         *  8 getstatic #2 <java/lang/System.out>
         * 11 iload_1
         * 12 invokevirtual #3 <java/io/PrintStream.println>
         * 15 return
         */

        int i = 100;
        i = ++i;
        System.out.println(i);


    }
}
