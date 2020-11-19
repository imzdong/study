package org.imzdong.study.leetcode.sort;

/**
 * 二分查找是否存在
 * @author dong
 * @since 2020/11/17 上午8:13
 */
public class BinarySearchExist {

    /**
     * 70
     * int[] ints = {1,3,5,6,8,46,70,77,324,567,789};
     * start=0;end=11,i=5;int[i]=46;46<70
     * start=5;end=11;i=5+(11-5)/2=8;int[i]=324;324>70
     * start=5;end=8;i=5+(8-5)/2=6;int[i]=70;70=70;return
     */
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int[] ints = SortUtil.randomSortArray(10, 50);
            int index = (int) (Math.random() * ints.length);
            int indexValue = ints[index];
            boolean existV2 = bsExistV2(ints, indexValue);
            if(!existV2){
                System.out.println("Fuck!!!!,i:"+i);
                System.out.println(indexValue);
                SortUtil.printArray(ints);
                break;
            }
        }

    }

    private static boolean bsExistV1(int[] ints, int exist) {
        int start = 0, end = ints.length,i = (end-start)>>1;
        int num = 0;
        for (;;i = start+((end-start)>>1)) {
            num++;
            if(ints[i] == exist){
                return true;
            }
            if(i==start||i==end){
                return false;
            }
            if(ints[i] > exist){
                end = i;
            }else {
                start = i;
            }
        }
    }

    private static boolean bsExistV2(int[] ints, int exist) {
        int start = 0, end = ints.length,mid;
        int num = 0;
        /**
         * int[] ints = {1,3,5,6,8,46,70,77,324,567,789};
         *         int exist = 30; mid=5 start + ((end-start)>>1)
         *         46>30 start=0,end=mid=5,n_mid=2
         *         5<30  start=n_mid=2,end=5,n_mid=3
         *         6<30  start=3,end=5,n_mid=4
         *         8<30  start=4,end=5,n_mid=4
         *         ...
         *         */
        while (start <= end) {
            num++;
            mid = start + ((end-start)>>1);
            if(ints[mid] == exist){
                System.out.println(String.format("size:%s,num:%s,exist:%s,index of ints:%s",ints.length,num,exist,mid));
                return true;
            }else if(ints[mid] > exist){
                end = mid - 1;
            }else {
                start = mid + 1;
            }
        }
        return false;
    }


}
