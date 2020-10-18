package org.imzdong.study.msb.day_11_jvm;

import com.imzdong.jvm.ObjectSizeAgent;

/**
 * -javaagent:object_size.jar
 * java -XX:+PrintCommandLineFlags -version
 */
public class ObjectSizeDemo {

    /**
     * openjdk version "1.8.0_252"
     * -XX:+UseCompressedClassPointers 默认开启类指针压缩
     * -XX:+UseCompressedOops 默认开启引用变量压缩
     * @param args
     */
    public static void main(String[] args) {
        demoSize();
        System.out.println("boolean(1位):"+ObjectSizeAgent.sizeOf(true));
        byte b = 1;
        System.out.println("byte("+Byte.SIZE+"位):"+ObjectSizeAgent.sizeOf(b));
        System.out.println("char("+Character.SIZE+"位):"+ObjectSizeAgent.sizeOf('1'));
        int i = 1;
        System.out.println("int("+Integer.SIZE+"位):"+ObjectSizeAgent.sizeOf(i));
        System.out.println("long("+Long.SIZE+"位):"+ObjectSizeAgent.sizeOf(1L));
        System.out.println("double("+Double.SIZE+"位):"+ObjectSizeAgent.sizeOf(1.0));
        System.out.println("float("+Float.SIZE+"位):"+ObjectSizeAgent.sizeOf(1.0F));
        short s = 1;
        System.out.println("short("+Short.SIZE+"位):"+ObjectSizeAgent.sizeOf(s));
    }

    private static void demoSize() {
        String ss = "123";////8 _markword + 4 classpointer+4+4=24
        System.out.println("123.size:"+ ObjectSizeAgent.sizeOf(ss));
        String ss2 = new String("123");
        System.out.println(ObjectSizeAgent.sizeOf(ss2));
        System.out.println();
        byte[] bytes = ss.getBytes();
        System.out.println(bytes);
        //8+4+4+3*2=22+2（对齐）=24
        System.out.println("123.bytes:"+ObjectSizeAgent.sizeOf(bytes));//24
        //8(markword)+4(classpointer)+0类+4对齐
        System.out.println("object:"+ObjectSizeAgent.sizeOf(new Object()));//16
        //8(markword)+4(classpointer 默认压缩)+size int(4)+0数+0对齐
        System.out.println("int[]:"+ObjectSizeAgent.sizeOf(new int[] {}));//16
        int[] ints = {1,2,3};
        //8+4+4+3*4=28+4(对齐)=32
        System.out.println("ints:"+ObjectSizeAgent.sizeOf(ints));//32
        //8+4 =12
        System.out.println(ObjectSizeAgent.sizeOf(new P()));
    }

    /**
     * boolean 布尔型              1/8  
     * byte 字节类型               1 
     * char 字符型                 2  一个字符能存储一个中文汉字 
     * short 短整型                2 
     * int 整数类型                4 
     * float 浮点类型（单精度）    4 
     * long 长整形                 8 
     * double 双精度类型（双精度） 8 
     *
     * 非基本类型都是引用类型长度4字节
     * 基本类型按照基本类型的位数
     */
    private static class P{
        private char[] value = {'1','2','3'}; //4
        private String a = "123"; //4
        private String b = "123232323"; //4
        //private char[] value2 = {'1','2','3'}; //4
        //12+16=28
        //private int i = 1;//4 =32
        //private long l =1;//8 = 40
        //private float f = 1;//4 = 32
        //private boolean bt = true;

        private static int i = 1;

    }
}
