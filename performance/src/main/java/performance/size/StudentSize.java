package performance.size;

import org.apache.lucene.util.RamUsageEstimator;

/**
 * @description:  对象大小：对象头（markword+class指针）+实例数据+对其填充
 *                数组需要：另+长度
 * @author: Winter
 * @time: 2020/1/8
 */
public class StudentSize {

    private int age;//12+4
    private String name;//12+4+4（引用类型占）+4（长度int）  24

    /**
     * 64位压缩 UseCompressedOops 对象头（12b）=运行时数据-markWord（4b）+class指针（8b）
     * 64位未压缩 对象头（16b）=运行时数据（8b）+class指针（8b）
     * 32位 对象头（8b）=运行时数据（4b）+class指针（4b）
     * @param args
     */
    public static void main(String[] args) {
        //intObject();
        String w = "winter";
        String humanSizeOf = RamUsageEstimator.humanSizeOf(w);
        long shallowSizeOf = RamUsageEstimator.shallowSizeOf(w);
        System.out.println("wHumanSizeOf:"+humanSizeOf);
        //humanSizeOf:56 bytes = 12+2*4+对齐（4）+（12+4+5*2+补齐（6））
        System.out.println("wShallowSizeOf:"+shallowSizeOf);
        //shallowSizeOf:24
    }
    /**
     * 空StudentSize对象大小算法：header=12b 规则一：对象需要补齐8字节对齐。所以是16b
     * int需要4位对齐，long需要8位
     */
    private static void emptyObject(){
        StudentSize studentSize = new StudentSize();
        String humanSizeOf = RamUsageEstimator.humanSizeOf(studentSize);
        long shallowSizeOf = RamUsageEstimator.shallowSizeOf(studentSize);
        System.out.println("humanSizeOf:"+humanSizeOf);//humanSizeOf:16 bytes
        System.out.println("shallowSizeOf:"+shallowSizeOf);//shallowSizeOf:16
    }

    /**
     * 空StudentSize对象大小算法：header=12b+int(4b) 不需要对齐。所以是16b
     */
    private static void intObject(){
        StudentSize studentSize = new StudentSize();
        String humanSizeOf = RamUsageEstimator.humanSizeOf(studentSize);
        long shallowSizeOf = RamUsageEstimator.shallowSizeOf(studentSize);
        System.out.println("intHumanSizeOf:"+humanSizeOf);//intHumanSizeOf:16 bytes
        System.out.println("intShallowSizeOf:"+shallowSizeOf);//intShallowSizeOf:16
    }
    /**
     * 规则二，类属性按照优先级进行排列：长字符和短整型；字节类型和布尔类型
     */
    /**
     * 规则三：优先按照规则一、规则二处理父类中的成员，接着是子类的成员
     * class A{byte a;}
     * class B extends A{byte b;}
     * B大小：A(12+1)+(对齐)3+B(1)+（对齐）7=24
     */
    /**
     * 规则四：当父类中最后一个成员和子类第一个的间隔不够4个字节的话，就必须扩展到4个字节的基单位。
     */
    /**
     * class A{char a;int b}
     * class B extends A{long c;short d;byte e}
     * B=12+b(4)+a(2)+对齐(4)+d(2)+e(1)+补齐（1）+c(8)=32
     * 规则五：如果子类第一个成员是一个双精度或者长整型，并且父类并没有用完8个字节，JVM会破坏规则2
     * ，按照int、short、byte、引用类型的顺序，向未填满的空间填充。
     */
    private static void guize5(){
        class A{char a;}
        class B extends A{long c;short d;byte e;}
        B b = new B();
        String strHumanSizeOf = RamUsageEstimator.humanSizeOf(b);
        long strShallowSizeOf = RamUsageEstimator.shallowSizeOf(b);
        System.out.println("bHumanSizeOf:"+strHumanSizeOf);//32
        System.out.println("bShallowSizeOf:"+strShallowSizeOf);//32
    }
}
