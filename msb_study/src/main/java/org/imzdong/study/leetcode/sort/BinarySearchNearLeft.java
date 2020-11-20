package org.imzdong.study.leetcode.sort;

/**
 * 一个有序数组中，找>=某个数最左侧的位置
 * @author dong
 * @since 2020/11/17 上午8:15
 */
public class BinarySearchNearLeft {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            nearLeft();
        }
    }

    private static void nearLeft() {
        int[] ints = SortUtil.randomSortArray(10, 50);
        SortUtil.printArray(ints);
        int index = (int) (Math.random() * ints.length);
        int indexValue = ints[index];
        System.out.println("indexValue:"+indexValue);
        //二分查找
        int start=0,end=ints.length-1,mid,left=0;
        while (start <= end){
            mid = start + ((end-start)>>1);
            if(ints[mid] >= indexValue){
                left = mid;
                end = mid -1;
            }else {
                start = mid + 1;
            }
        }
        System.out.println(ints[left]);
    }
}
