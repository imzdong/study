package org.imzdong.study.stone.lettcode.tree;

/**
 * 冒泡排序及升级版本
 * @author zhoud
 * @since 2021/3/11 12:35
 */
public class BubbleSort {

    public static void main(String[] args) {
        int [] arrays = {3,7,2,8,4,0};
        //int [] arrays = {1,0,3,5,7};
        bubbleSortV3(arrays);
        //bubbleSortV1(arrays);
        for (int array : arrays) {
            System.out.println(array);
        }
    }

    private static void bubbleSortV3(int[] arrays) {
        int sortIndex = arrays.length - 1;
        int lastExchage = 0;
        for (int i = 0; i < arrays.length-1; i++) {
            boolean isSort = true;
            for (int j = 0; j < sortIndex; j++) {
                if(arrays[j] > arrays[j+1]){
                    int temp = arrays[j];
                    arrays[j] = arrays[j+1];
                    arrays[j+1] = temp;
                    isSort = false;
                    lastExchage = j;
                }
            }
            sortIndex = lastExchage;
            if(isSort){
                break;
            }
        }
    }

    private static void bubbleSortV1(int[] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
            boolean isSort = true;
            for (int j = 0; j < arrays.length - i -1; j++) {
                if(arrays[j] > arrays[j+1]){
                    int temp = arrays[j];
                    arrays[j] = arrays[j+1];
                    arrays[j+1] = temp;
                    isSort = false;
                }
            }
            if(isSort){
                System.out.println("hahah");
                break;
            }
        }
    }

    private static void bubbleSortV2(int[] arrays) {
        for (int i = 0; i < arrays.length-1; i++) {
            for (int j = 0; j < arrays.length - 1- i; j++) {
                if(arrays[j] > arrays[j+1]){
                    int temp = arrays[j];
                    arrays[j] = arrays[j+1];
                    arrays[j+1] = temp;
                }
            }
        }
    }
}
