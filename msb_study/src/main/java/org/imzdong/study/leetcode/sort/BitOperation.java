package org.imzdong.study.leetcode.sort;

public class BitOperation {

    public static void main(String[] args) {
        //与：0（false），1（true）
        int andOperate = 3&2; //28个0011 & 28个0010 = 28个0010 = 2
        System.out.println("3&2:"+andOperate);
        //或：0（false），1（true） |1相当于+1
        int orOperate =  5|2; //101  | 010 = 111 = 7
        System.out.println("5|2:"+orOperate);
        System.out.println("5|6:"+(5|6));//101 | 110 = 111 = 7
        //异或 无进位相加
        int yhOperate = 6^4; //110 ^ 100 = 010 = 2
        System.out.println("6^4:"+yhOperate);
        System.out.println("3^3:"+(3^3));//0
        System.out.println("3^4^3:"+(3^4^3));//4

        System.out.println(8>>1);//除以2 4
        System.out.println(9>>1);//除以2 4

        System.out.println(8<<1);//乘以2 16
        System.out.println(9<<1);//乘以2 18

        int x= 3, y=4;
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        System.out.println("x:"+x+";y:"+y);
        //x:4;y:3
    }
}
