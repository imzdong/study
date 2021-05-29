package org.imzdong.study.stone.lettcode.sort;

import java.util.Arrays;

/**
 * 快速排序：分而治之
 * @author zhoud
 * @since 2021/3/12 7:47
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {3,2,6,10,23,67,5};
        quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int start, int end){
        if(end <= start){
            return;
        }
        int pivot = partition(arr, start, end);
        quickSort(arr, start, pivot-1);
        quickSort(arr, pivot+1, end);
    }

    /**
     * left指针，和基准比较，小于等于，右移。大于停止；
     * 移动right指针，和基准比较，大于等于，左移。小于停止。
     * 停止后左右值交换
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] arr,int start, int end){
        int pivot = start;
        int pivotVal = arr[start];
        while (start != end){
            while (end > start && arr[end] > pivotVal){
                end--;
            }
            while ( end > start&&arr[start] <= pivotVal){
                start++;
            }
            if(start < end){
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        }
        arr[pivot] = arr[start];
        arr[start] = pivotVal;
        return start;
    }
}
