package org.imzdong.study.leetcode.sort;

/**
 * ^使用场景
 * 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 * @author Winter
 * @since 2020年11月26日, 0026 12:41
 */
public class EvenTimesOddTimes {

    public static void main(String[] args) {
        //rightOne();
        int[] ints = {1,2,3,4,5,6,1,3,5,6};
        int eor = 0;
        for (int num:ints){
            eor^=num;
        }
        int rightOne = eor & (-eor); // 提取出最右的1
        int onlyOne = 0; // eor'
        for (int i = 0 ; i < ints.length;i++) {
            //  arr[1] =  111100011110000
            // rightOne=  000000000010000
            if ((ints[i] & rightOne) != 0) {
                onlyOne ^= ints[i];
            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

    /**
     * 1.先将-5的绝对值转换成二进制，即为0000 0101；
     * 2.然后求该二进制的反码，即为 1111 1010；
     * 3.最后将反码加1，即为：1111 1011
     */
    private static void rightOne(){
        //0000 0000 0000 0000 0000 0000 0000 0110
        int num = 6;
        System.out.println(-num);
        /**
         * 0000 0000 0000 0000 0000 0000 0000 0110
         * 取反码
         * 1111 1111 1111 1111 1111 1111 1111 1001
         * +1
         * 1111 1111 1111 1111 1111 1111 1111 1010
         */
        System.out.println(Integer.toBinaryString(-num));
        /**
         * 0000 0000 0000 0000 0000 0000 0000 0110
         * &
         * 1111 1111 1111 1111 1111 1111 1111 1010
         * =
         * 0000 0000 0000 0000 0000 0000 0000 0010
         */
        int rightOne = num & (-num);
        System.out.println(rightOne);
        //-6  1000 0000 0000 0000 0000 0000 0000 0110

    }
}
