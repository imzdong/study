package org.imzdong.study.leetcode.day02;


/**
 * @description:
 * @author: Winter
 * @time: 2020/3/20
 */
public class GetKValue {

    public static void main(String[] args) {
        bubbleSort();
    }

    /**
     * 冒泡排序
     */
    private static void bubbleSort() {
        int[] arr={14,20,3,4};
        int k=2;
        int[] kArr=new int[k];
        for(int x=0;x<arr.length;x++){
            for(int y=x+1;y<arr.length;y++){
                if(arr[y]<arr[x]){
                    int temp = arr[x];
                    arr[x] = arr[y];
                    arr[y] = temp;
                }
            }
            if(x==k) {
                break;
            }
            kArr[x]=arr[x];
        }
        for(int x=0;x<kArr.length;x++) {
            System.out.println(arr[x]);
        }
    }

    private void quickSelect(int[] nums, int start, int end, int k) {
        if (start >= end) {
            return;
        }
        int left = start;
        int right = end;
        int pivot = nums[(left + right) / 2];
        while (left <= right) {
            while (left <= right && nums[left] < pivot) {
                left++;
            }
            while (left <= right && nums[right] > pivot) {
                right--;
            }
            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        if (right >= k) {
            quickSelect(nums, start, right, k);
        } else {
            quickSelect(nums, left, end, k);
        }

    }
}
