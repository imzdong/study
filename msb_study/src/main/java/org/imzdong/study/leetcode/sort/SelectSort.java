package org.imzdong.study.leetcode.sort;

import java.util.Arrays;

/**
 * 选择排序
 * @author dong
 * @since 上午8:09
 */
public class SelectSort {
    /**
     * 从左到右，依次选择最小的交换到左侧。
     * 0  和1～n的所有比较最小值交换   (n-1)*常数时间+1（交换）
     * 1  和2～n...                (n-2)*常数时间+1（交换）
     * 3  和...                   (n-3)*常数时间+1（交换）
     * ...
     * T（N） = (n-1)*常数时间+1（交换） + (n-2)*常数时间+1（交换） + (n-3)*常数时间+1（交换）+ ... + (1)*常数时间+1（交换）
     * T(N) = (n-1+n-2+....+1)*常数时间 + N（交换）
     *
     * Sn=n*a1+n(n-1)d/2或Sn=n(a1+an)/2
     * Sn=an*n+bn
     */
    public static void main(String[] args) {
        for(int x = 0;x<10;x++) {
            int[] arr = SortUtil.randomArray(10, 100);
            SortUtil.printArray(arr);
            int[] newArr = Arrays.copyOf(arr, arr.length);
            selectSortV2(arr);
            SortUtil.logarithm(newArr, arr);
            SortUtil.printArray(arr);
        }
    }

    /**
     * 选择排序
     * @param arr
     */
    private static void selectSortV1(int[] arr){
        for(int x=0;x<arr.length;x++){
            for (int y = x+1; y < arr.length; y++) {
                if(arr[x]>arr[y]){
                    SortUtil.swap(arr, x, y);
                }
            }
        }
    }

    /**
     * 选择排序
     * 减少交换次数
     * @param arr
     */
    private static void selectSortV2(int[] arr){
        if (arr == null || arr.length < 2) {
            return;
        }
        for(int x=0;x<arr.length-1;x++){
            int minIndex = x;
            for (int y = x+1; y < arr.length; y++) {
                if(arr[minIndex]>arr[y]){
                    minIndex = y;
                }
            }
            if(minIndex != x) {
                SortUtil.swap(arr, x, minIndex);
            }
        }
    }

}
