package org.imzdong.study.lettcode;

/**
 * 33. 搜索旋转排序数组
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 *
 * 示例 1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 * 示例 3：
 * 输入：nums = [1], target = 0
 * 输出：-1
 *
 * 提示：
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums 中的每个值都 独一无二
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -10^4 <= target <= 10^4
 *
 * 进阶：你可以设计一个时间复杂度为 O(log n) 的解决方案吗？
 * @author admin
 * @date 2021/4/8
 */
public class SearchArray33 {

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 1, 2, 3};
        int search = search(nums, 3);
        System.out.println(search);
        System.out.println(searchJava(nums, 3));
    }

    private static int copy(int []nums, int target){
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if ((nums[0] > target) || (nums[0] > nums[mid]) || (target > nums[mid])) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo == hi && nums[lo] == target ? lo : -1;
    }

    private static int searchJava(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 二分查找(搜索旋转排序数组)
     * k旋转，0-k-1升序，k-length升序 k-1最大值
     * 如果target在0-k-1 或者k-1～length 二分查找
     * mid的数据可能处于不同位置，处理方法不同
     * target在0～k-1区间：直接二分查找
     * target在k～length区间：直接二分查找
     * mid的数和target比较
     * 1、nums[mid] < target
     *
     * 简要来说：
     *
     * nums[0] <= nums[mid]（0 - mid不包含旋转）且nums[0] <= target <= nums[mid] 时 high 向前规约；
     * nums[mid] < nums[0]（0 - mid包含旋转），target <= nums[mid] < nums[0] 时向前规约（target 在旋转位置到 mid 之间）
     * nums[mid] < nums[0]，nums[mid] < nums[0] <= target 时向前规约（target 在 0 到旋转位置之间）
     * 其他情况向后规约
     * 也就是说nums[mid] < nums[0]，nums[0] > target，target > nums[mid] 三项均为真或者只有一项为真时向后规约。
     *
     * @param nums
     * @param target
     * @return
     */
    private static int search(int [] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        int first = nums[left];
        /**
         * 1,2,3,4,4,5,6,7,8,9  ➡ 4,5,6,7,8,9,1,2,3,4
         * 10 正常走
         * 9  mid:8  target > mid left左移，
         *    mid:
         *    mid:8  target < mid
         * 6    mid > first :正常二分查找
         * 1    mid < first :  4,1,2,3,5
         *
         */
        while (left <= right){
            mid = (right + left)/2;
            int midV = nums[mid];
            if(midV == target){
                return mid;
            }
            if(first <= midV){
                if(target <= midV && target >first){
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }else {
                if(target > midV && target <= nums[nums.length-1]){
                    left = mid + 1;
                }else{
                    //两边都有可能
                    right = mid - 1;
                }
                // target > midV
            }
        }
        return -1;
    }

}
/**
 * 以二分搜索为基本思路
 *
 * 简要来说：
 *
 * nums[0] <= nums[mid]（0 - mid不包含旋转）且nums[0] <= target <= nums[mid] 时 high 向前规约；
 * nums[mid] < nums[0]（0 - mid包含旋转），target <= nums[mid] < nums[0] 时向前规约（target 在旋转位置到 mid 之间）
 * nums[mid] < nums[0]，nums[mid] < nums[0] <= target 时向前规约（target 在 0 到旋转位置之间）
 * 其他情况向后规约
 * 也就是说nums[mid] < nums[0]，nums[0] > target，target > nums[mid] 三项均为真或者只有一项为真时向后规约。
 *
 * 原文的分析是：
 *
 * 注意到原数组为有限制的有序数组（除了在某个点会突然下降外均为升序数组）
 *
 * if nums[0] <= nums[I] 那么 nums[0] 到 nums[i] 为有序数组,那么当 nums[0] <= target <= nums[i] 时我们应该在
 * 0−i
 * 0−i 范围内查找；
 * if nums[i] < nums[0] 那么在
 * 0
 * −
 * i
 * 0−i 区间的某个点处发生了下降（旋转），那么
 * I
 * +
 * 1
 * I+1 到最后一个数字的区间为有序数组，并且所有的数字都是小于 nums[0] 且大于 nums[i]，当target不属于 nums[0] 到 nums[i] 时（target <= nums[i] < nums[0] or nums[i] < nums[0] <= target），我们应该在
 * 0 − i
 * 0−i 区间内查找。
 * 上述三种情况可以总结如下：
 *
 *     nums[0] <= target <= nums[i]
 *                target <= nums[i] < nums[0]
 *                          nums[i] < nums[0] <= target
 * 所以我们进行三项判断：
 *
 * (nums[0] <= target)， (target <= nums[i]) ，(nums[i] < nums[0])，现在我们想知道这三项中有哪两项为真（明显这三项不可能均为真或均为假（因为这三项可能已经包含了所有情况））
 *
 * 所以我们现在只需要区别出这三项中有两项为真还是只有一项为真。
 *
 * 使用 “异或” 操作可以轻松的得到上述结果（两项为真时异或结果为假，一项为真时异或结果为真，可以画真值表进行验证）
 *
 * 之后我们通过二分查找不断做小 target 可能位于的区间直到 low==high，此时如果 nums[low]==target 则找到了，如果不等则说明该数组里没有此项。
 *
 * 作者：LukeLee
 * 链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array/solution/ji-jian-solution-by-lukelee/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
