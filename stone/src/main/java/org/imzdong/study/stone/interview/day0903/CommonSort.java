package org.imzdong.study.stone.interview.day0903;

/**
 * @author admin
 * @since 2021/9/3 上午10:28
 */
public class CommonSort {

    public static void main(String[] args) {
        int arrays[] = {3, 2, 5, 8, 10, 2, 13, 15,18};
        //bubbleSort(arrays);
        //insertSort(arrays);
        /**
         * 选择排序算法的实现思路有点类似插入排序，
         * 也分已排序区间和未排序区间。
         * 但是选择排序每次会从未排序区间中找到最小的元素，
         * 将其放到已排序区间的末尾。
         */
        for (int i = 0; i < arrays.length; i++) {
            int minIndex = i;
            for (int j = i+1; j < arrays.length; j++) {
                if(arrays[j] < arrays[minIndex]){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                int tmp = arrays[i];
                arrays[i] = arrays[minIndex];
                arrays[minIndex] = tmp;
            }
        }
        for (int arr : arrays) {
            System.out.println(arr);
        }
    }

    private static void insertSort(int[] arrays) {
        /**
         * 插入算法的核心思想是取未排序区间中的元素，
         * 在已排序区间中找到合适的插入位置将其插入，
         * 并保证已排序区间数据一直有序。重复这个过程，
         * 直到未排序区间中元素为空，算法结束。
         */
        for (int i = 1; i < arrays.length; i++) {
            int value = arrays[i];
            int j = i-1;
            for(;j>=0;j--){
                if(arrays[j] > value){
                    arrays[j+1] = arrays[j];
                }else {
                    break;
                }
            }
            arrays[j+1] = value;
        }
    }

    private static void bubbleSort(int[] arrays) {
        /**
         * 冒泡排序只会操作相邻的两个数据。每次冒泡操作都会对相邻的两个元素进行比较，看是否满足大小关系要求。
         * 如果不满足就让它俩互换。一次冒泡会让至少一个元素移动到它应该在的位置，重复 n 次，
         * 就完成了 n 个数据的排序工作。
         */
        for (int i = 0; i < arrays.length; i++) {
            boolean isSort = true;
            for (int j = 0; j < arrays.length - i - 1; j++) {
                if(arrays[j] > arrays[j+1]){
                    isSort = false;
                    int temp = arrays[j];
                    arrays[j] = arrays[j+1];
                    arrays[j+1] = temp;
                }
            }
            if (isSort){
                System.out.println(i);
                System.out.println("-------------");
                break;
            }
        }
    }
}
