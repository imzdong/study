package org.imzdong.study.leetcode.sort;

/**
 * 局部最小值
 * @author dong
 * @since 2020/11/24 下午10:35
 */
public class BsAwesome {

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            int[] ints = SortUtil.randomArray(10, 50);
            int lessIndex = getLessIndex(ints);
            int lessIndexV1;
            try {
                lessIndexV1 = getLessIndexV1(ints);
            }catch (Exception e){
                SortUtil.printArray(ints);
                System.out.println(lessIndex);
                e.printStackTrace();
                break;
            }
            if(lessIndex != lessIndexV1) {
                System.out.println(String.format("lessIndex:%s,lessIndexV1:%s",lessIndex,lessIndexV1));
                System.out.println("Fuck!!!");
                SortUtil.printArray(ints);
            }
        }

    }

    public static int getLessIndexV1(int[] ints){
        if(ints.length < 1 ){
            return -1;
        }
        if(ints.length == 1 || ints[0] < ints[1]){
            return 0;
        }
        if(ints[ints.length -1]<ints[ints.length -2]){
            return ints.length -1;
        }
        int start=1,end=ints.length-2,mid;
        while (start < end){
            mid = ((end + start)/2);
            if(ints[mid-1] < ints[mid]){
                end = mid -1;
            }else if(ints[mid] > ints[mid+1]){
                start = mid + 1;
            }else {
                return mid;
            }
        }
        return start;
    }

    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1; // no exist
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        int left = 1;
        int right = arr.length - 2;
        int mid = 0;
        while (left < right) {
            mid = (left + right) / 2;
            if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

}
