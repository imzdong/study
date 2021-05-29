package org.imzdong.study.stone.lettcode.sort;

import java.util.Arrays;

/**
 * 快速排序：单边循环
 * 选择基准，mark和基准一直，向右移动，比较基准和右移动的大小。
 * 大于继续移动，小于和mark交换，mark后移动
 * @author zhoud
 * @since 2021/3/12 7:47
 */
public class QuickSingleSort {

    public static void main(String[] args) {
        int[] arr = {3,2,6,5,23,10,67};
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
     * 单边循环
     * 选择基准，mark和基准一致，
     * 向右移动，比较基准和右移位置的大小。
     * 大于继续移动，小于和mark交换，mark后移动
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] arr,int start, int end){
        int pivot = start;
        int mark = pivot;
        int pivotVal = arr[pivot];
        while (start < end){
            if(pivotVal > arr[start]){
                mark++;
                int temp = arr[mark];
                arr[mark] = arr[start];
                arr[start] = temp;
            }
            start++;
        }
        arr[pivot] = arr[mark];
        arr[mark] = pivotVal;
        return mark;
    }
}
