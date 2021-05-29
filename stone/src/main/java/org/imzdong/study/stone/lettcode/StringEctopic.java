package org.imzdong.study.stone.lettcode;

/**
 * LeetCode 第 242 题：给定两个字符串 s 和 t，编写一个函数来判断 t 是否是 s 的字母异位词
 * 新建一个26字母的数组，遍历两个字符串，s包含+1，t包含减一。26字母的数组都为0：s和t是，否则不是
 * @author zhoudong6
 * @since 2021年3月1日, 0001 10:18
 */
public class StringEctopic {

    public static void main(String[] args) {
        String s = "anagram", t = "nagaram";
        if(s == null || t == null){
            return;
        }
        byte[] bytes = new byte[26];
        for(byte sb : s.getBytes()){
            bytes[sb%26]++;
        }
        for(byte tb : t.getBytes()){
            bytes[tb%26]--;
        }
        for (byte aByte : bytes) {
            if (aByte > 0) {
                System.out.println("s和t不是字母异位词");
                return;
            }
        }
        System.out.println("s和t是字母异位词");
    }
}
