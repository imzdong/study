package org.imzdong.study.leetcode.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * @author dong
 * @since 2020/11/17
 */
public class BubbleSort{

    /**
     * 从左到右依次，比较，谁大置换到右
     * O(N^2)
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int[] arr = SortUtil.randomArray(10, 100);
            int[] newArr = Arrays.copyOf(arr, arr.length);
            bubbleSort(arr);
            SortUtil.logarithm(newArr,arr);
            SortUtil.printArray(arr);
        }
    }

    /**
     * 0~n
     * 0~n-1
     * ...
     * 冒泡排序
     * @param arr
     */
    private static void bubbleSort(int[] arr){
        if (arr == null || arr.length < 2) {
            return;
        }
        for(int x=arr.length-1; x > 0;x--){
            for (int y = 0; y < x; y++) {
                if(arr[y] > arr[y+1]){
                    SortUtil.swap(arr, y, y+1);
                }
            }
        }
    }
}
