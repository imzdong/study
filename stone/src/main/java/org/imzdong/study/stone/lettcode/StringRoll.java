package org.imzdong.study.stone.lettcode;

/**
 * 翻转字符串“algorithm”
 * 字符串转换成数组，两个指针调换，时间复杂度：O(N)
 * @author zhoudong6
 * @since 2021年3月1日, 0001 10:08
 */
public class StringRoll {

    public static void main(String[] args) {
        String str = "algorithm";
        byte[] bytes = str.getBytes();
        int pre = 0;
        int finalP = bytes.length - 1;
        while (pre < finalP){
            byte temp = bytes[pre];
            bytes[pre] = bytes[finalP];
            bytes[finalP] = temp;
            pre++;
            finalP--;
        }
        String result = new String(bytes);
        System.out.println(result);
    }
}
