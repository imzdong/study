package org.imzdong.study.leetcode.sort;

/**
 * 一个有序数组中，找>=某个数最左侧的位置
 * @author dong
 * @since 2020/11/17 上午8:15
 */
public class BinarySearchNearLeft {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int index = (int) (Math.random() * 10);
            System.out.println(index);
        }
        //nearLeft();
    }

    private static void nearLeft() {
        int[] ints = SortUtil.randomSortArray(10, 50);
        SortUtil.printArray(ints);
        int index = (int) Math.random() * ints.length;
        int indexValue = ints[index];
        System.out.println(String.format("indexValue:%s",indexValue));
        //二分查找
        int start=0,end=ints.length-1,mid;
        while (start < end){
            mid = start + ((end-start)>>1);
            if(ints[mid] == indexValue){
                System.out.println(String.format("index:%s,findIndex:%s",index,mid));
                break;
            }else if(ints[mid] > indexValue){
                end = mid -1;
            }else {
                start = mid + 1;
            }
            System.out.println(String.format("start:%s,end:%s,mid:%s",start,end,mid));
        }
    }
}
