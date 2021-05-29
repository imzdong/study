package org.imzdong.study.stone.lettcode.sort;

/**
 * 选择排序
 * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。 *
 * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。 *
 * 重复第二步，直到所有元素均排序完毕。
 * @author zhoud
 * @since 2021/3/11 16:38
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] array = {12,3,5,17,9,0};
        selectionSort(array);
        for (int i:array){
            System.out.println(i);
        }
    }

    private static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i+1; j < array.length; j++) {
                if(array[min] > array[j]){
                    min = j;
                }
            }
            if(min != i){
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }
}
