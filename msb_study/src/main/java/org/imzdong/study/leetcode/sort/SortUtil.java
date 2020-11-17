package org.imzdong.study.leetcode.sort;

import java.util.Arrays;

/**
 * @author Winter
 * @since 2020年11月17日, 0017 15:59
 */
public class SortUtil {
    
    public static void swap(int[] arr, int x, int y){
        arr[x] = arr[x] ^ arr[y];
        arr[y] = arr[x] ^ arr[y];
        arr[x] = arr[x] ^ arr[y];
    }

    public static void printArray(int[] array){
        for(int arr:array){
            System.out.print(arr+",");
        }
        System.out.println();
        System.out.println("--------");
    }

    /**
     * 随机生成数组
     * @param maxSize 数组最大长度
     * @param maxValue 数组最大值
     * @return 数组
     */
    public static int[] randomArray(int maxSize, int maxValue){
        // Math.random()   [0,1)
        // Math.random() * N  [0,N)
        // (int)(Math.random() * N)  [0, N-1]
        int[] randomArray = new int[(int) ((maxSize + 1) * Math.random())];
        for(int x=0;x < randomArray.length;x++){
            randomArray[x] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return randomArray;
    }

    /**
     * 对数器
     * @param org
     * @param dest
     */
    public static void logarithm(int[] org,int[] dest){
        if(org == null || dest == null){
            System.out.println("Fuck!!!");
            return;
        }
        int orgLength = org.length;
        int destLength = dest.length;
        if(orgLength != destLength){
            System.out.println("Fuck!!!!");
            return;
        }
        Arrays.sort(org);
        for (int x=0;x<orgLength;x++){
            if(org[x] != dest[x]){
                System.out.println("Fuck--!!!!");
                return;
            }
        }
        System.out.println("Success!!!");
    }
}
