package org.imzdong.study.leetcode.sort;

/**
 * 一个有序数组中，找<=某个数最右侧的位置
 * @author dong
 * @since 2020/11/19 上午7:43
 */
public class BinarySearchNearRight {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            nearRight();
        }
    }

    private static void nearRight() {
        int[] ints = SortUtil.randomSortArray(12, 50);
        int index = (int) (Math.random() * ints.length);
        int indexValue = ints[index];
        SortUtil.printArray(ints);
        System.out.println("indexValue:"+indexValue);
        //二分查找
        int start=0,end=ints.length-1,mid,right=0;
        while (start <= end){
            mid = start + ((end-start)>>1);
            if(ints[mid] <= indexValue){
                right = mid;
                start = mid + 1;
            }else {
                end = mid -1;
            }
        }
        System.out.println(ints[right]);
    }
}
