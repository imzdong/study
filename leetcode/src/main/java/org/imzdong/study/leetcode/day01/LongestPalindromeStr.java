package org.imzdong.study.leetcode.day01;

import java.util.*;

/**
 * @description:
 * @author: Winter
 * @time: 2020/3/19
 */
public class LongestPalindromeStr {

    public static void main(String[] args) {
        String str = "jglknendplocymmvwtoxvebkekzfdhykknufqdkntnqvgfbahsljkobhbxkvyictzkqjqydczuxjkgecdyhixdttxfqmgksrkyvopwprsgoszftuhawflzjyuyrujrxluhzjvbflxgcovilthvuihzttzithnsqbdxtafxrfrblulsakrahulwthhbjcslceewxfxtavljpimaqqlcbrdgtgjryjytgxljxtravwdlnrrauxplempnbfeusgtqzjtzshwieutxdytlrrqvyemlyzolhbkzhyfyttevqnfvmpqjngcnazmaagwihxrhmcibyfkccyrqwnzlzqeuenhwlzhbxqxerfifzncimwqsfatudjihtumrtjtggzleovihifxufvwqeimbxvzlxwcsknksogsbwwdlwulnetdysvsfkonggeedtshxqkgbhoscjgpiel";

        byte[] bytes = str.getBytes();
        int[] strArray = new int[128];
        for(byte b:bytes){
            strArray[b]++;
        }
        boolean tFlag = false ;
        int realSum = 0;
        for(int i:strArray){
            if(i%2 == 0){
                realSum+=i;
            }else{
                realSum+=((i/2)*2);
                tFlag = true;
            }
        }
        int result = (realSum==bytes.length?(tFlag?realSum+1:realSum):realSum+1);
        System.out.println(result);
        //456+445=11
    }

    private static void orgMethod(String str){
        byte[] bytes = str.getBytes();
        Set<Byte> strSet = new HashSet<>();
        Map<Byte,Integer> sameMap = new HashMap<>();
        for(byte b:bytes){
            if(strSet.contains(b)) {
                Integer sI = sameMap.get(b);
                if(sI==null) {
                    sameMap.put(b,2);
                }else {
                    sameMap.put(b,sI+1);
                }
            }else {
                strSet.add(b);
            }
        }
        Collection<Integer> values = sameMap.values();
        boolean tFlag = false ;
        int realSum = 0;
        if(values.size()>0){
            for(Integer i:values){
                if(i%2 == 0){
                    realSum+=i;
                }else{
                    realSum+=((i/2)*2);
                    tFlag = true;
                }
            }
        }

        int result = (realSum==bytes.length?(tFlag?realSum+1:realSum):realSum+1);
        System.out.println(result);
    }
}
