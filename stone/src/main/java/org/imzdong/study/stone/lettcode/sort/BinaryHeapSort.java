package org.imzdong.study.stone.lettcode.sort;

import java.util.Arrays;

/**
 * 二叉堆：特殊的完全二叉树
 * 最大堆：所有根节点都大于或者等于它的左右孩子
 * 最小堆：所有根节点都小于或者等于它的左右孩子
 * @author zhoud
 * @since 2021/3/14 10:36
 */
public class BinaryHeapSort {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,6,5,8};
        upAdjust(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 最大堆的实现
     * 上浮：最后一个叶子节点向上比较，大于就交换，并停止。
     * @param arr
     */
    private static void upAdjust(int[] arr){
        if(arr == null || arr.length <= 1){
            return;
        }
        if(arr.length == 2){
            if(arr[1] > arr[0]){
                int temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            return;
        }
        int lastIndex = arr.length - 1 ;
        while (lastIndex > 0){
            int tempIndex = lastIndex;
                while (tempIndex >= 2 ) {
                    int parentIndex= (tempIndex - 1) / 2;
                    if(tempIndex%2 == 0){
                        parentIndex = (tempIndex - 2) / 2;
                    }
                    if(arr[tempIndex] > arr[parentIndex]) {
                        int temp = arr[tempIndex];
                        arr[tempIndex] = arr[parentIndex];
                        arr[parentIndex] = temp;
                    }
                    tempIndex = parentIndex;
                }
            lastIndex--;
        }
    }


    /**
     * 最大堆的实现
     * 上浮：最后一个叶子节点向上比较，大于就交换，并停止。
     * @param arr
     */
    private static void upAdjustV1(int[] arr){
        if(arr == null || arr.length <= 1){
            return;
        }
        int childIndex = arr.length - 1;
        int parentIndex = (childIndex - 1)/2;
        while (childIndex > 0 && arr[childIndex] > arr[parentIndex]){

        }
    }
}
