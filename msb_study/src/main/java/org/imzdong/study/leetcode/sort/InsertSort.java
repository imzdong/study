package org.imzdong.study.leetcode.sort;

import java.util.Arrays;

/**
 * 插入排序
 * @author dong
 * @since 2020/11/17
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = SortUtil.randomArray(10, 100);
        SortUtil.printArray(arr);
        int[] copy = Arrays.copyOf(arr, arr.length);
        for(int x=1 ; x < arr.length ; x++){
            for(int y=x-1; y >= 0;y-- ) {
                if(arr[y+1]<arr[y]){
                    SortUtil.swap(arr,y,y+1);
                }
            }
        }
        /**
         * for (int i = 1; i < arr.length; i++) { // 0 ~ i 做到有序
         * 			for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
         * 				swap(arr, j, j + 1);
         *                        }* 		}
         */
        SortUtil.logarithm(copy, arr);
        SortUtil.printArray(arr);
    }
}
