package org.imzdong.study.leetcode.sort;

/**
 * 二分查找是否存在
 * @author dong
 * @since 2020/11/17 上午8:13
 */
public class BinarySearchExist {

    public static void main(String[] args) {
        int[] ints = {1,3,5,6,8,46,70,77,324,567,789};
        int exist = 70;
        int start = 0, end = ints.length;
        for (int i = (end-start)>>1;;i = start+(end-start)>>1) {
            if(ints[i] == exist){
                System.out.println(exist+" index of ints :"+i);
                break;
            }
            System.out.println(String.format("i:%s,start:%s,end:%s",i,start,end));
            /*if(i==start||i==end){
                System.out.println(exist + " not in ints");
                break;
            }*/
            if(ints[i] > exist){
                end = i;
            }else {
                start = i;
            }
        }
    }



}
