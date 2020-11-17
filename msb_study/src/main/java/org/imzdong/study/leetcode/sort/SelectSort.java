package org.imzdong.study.leetcode.sort;

/**
 * 选择排序
 * @author dong
 * @since 上午8:09
 */
public class SelectSort {
    /**
     * 从左到右，依次选择最小的交换到左侧。
     * 0  和1～n的所有比较最小值交换   (n-1)*常数时间+1（交换）
     * 1  和2～n...                (n-2)*常数时间+1（交换）
     * 3  和...                   (n-3)*常数时间+1（交换）
     * ...
     * T（N） = (n-1)*常数时间+1（交换） + (n-2)*常数时间+1（交换） + (n-3)*常数时间+1（交换）+ ... + (1)*常数时间+1（交换）
     * T(N) = (n-1+n-2+....+1)*常数时间 + N（交换）
     *
     * Sn=n*a1+n(n-1)d/2或Sn=n(a1+an)/2
     * Sn=
     */
}
