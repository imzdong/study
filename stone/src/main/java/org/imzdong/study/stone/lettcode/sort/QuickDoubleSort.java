package org.imzdong.study.stone.lettcode.sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 快速排序非递归实现
 * @author zhoud
 * @since 2021/3/12 18:45
 */
public class QuickDoubleSort {

    public static void main(String[] args) {
        int[] arr = {3, 7,6,2,18,1,9,0};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 快速排序
     * 基准是首位，
     * left从左到右移动，值和基准比较。arr[left]< 基准 继续移动，大于基准，停止
     * right从右到左移动，值和基准比较。arr[right] > 基准 继续移动，大于基准，停止
     * 停止的两数交换位置。
     * 最后替换基准和两个指针的位置
     * @param arr
     */
    private static void quickSort(int[] arr) {
        if(arr==null || arr.length==0){
            return;
        }
        Stack<Map<String, Integer>> maps = new Stack<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("start", 0);
        map.put("end", arr.length-1);
        maps.add(map);
        while (!maps.isEmpty()){
            Map<String, Integer> pop = maps.pop();
            int start = pop.get("start");
            int end = pop.get("end");
            int prviot = partion(arr, start, end);
            if(start < prviot-1) {
                HashMap<String, Integer> left = new HashMap<>();
                left.put("start", start);
                left.put("end", prviot - 1);
                maps.add(left);
            }
            if(prviot+1 < end){
                HashMap<String, Integer> right = new HashMap<>();
                right.put("start", prviot + 1);
                right.put("end", end);
                maps.add(right);
            }
        }
    }

    /**
     * 快速排序
     * 基准是首位，
     * left从左到右移动，值和基准比较。arr[left]< 基准 继续移动，大于基准，停止
     * right从右到左移动，值和基准比较。arr[right] > 基准 继续移动，大于基准，停止
     * 停止的两数交换位置。
     * 最后替换基准和两个指针的位置
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private static int partion(int[] arr, int start, int end) {
        int pivot = start;
        int pivotVal = arr[start];
        while (start < end){
            //right从右到左移动，值和基准比较。arr[right] > 基准 继续移动，大于基准，停止
            while (start < end && arr[end] > pivotVal){
                end--;
            }
            //left从左到右移动，值和基准比较。arr[left]< 基准 继续移动，大于基准，停止
            while (start < end && arr[start] <= pivotVal){
                start++;
            }
            //停止的两数交换位置。
            if(start < end) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        }
        //最后替换基准和两个指针的位置
        arr[pivot] = arr[start];
        arr[start] = pivotVal;
        return start;
    }


}
