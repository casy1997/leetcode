package onetoten;

import org.junit.Test;

import java.util.HashMap;

/**
 * 字符串中的最长重复子串
 *
 * 1.滑动窗口
 * 2.哈希表
 *
 * */
public class LongestStringWRC {

    /**
     * 滑动窗口
     * */
    public int lengthOfLongestSubstring(String s) {
        if("".equals(s)){
            return 0;
        }
        if(s.length() < 2){
            return 1;
        }
        int tail = 0;
        int head = 0;
        int w = 1;
        int maxSubstring = 0;
        int next = 0;
        next = tail + 1;
        boolean flag = false;
        while(next < s.length()){
            flag = false;
            for(int i = head; i <=tail; i++){
                if(s.charAt(next) == s.charAt(i)){
                    head = i + 1;
                    tail++;
                    w = tail - head + 1;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                tail++;
                w++;
            }
            maxSubstring = maxSubstring < w ? w:maxSubstring;
            next = tail + 1;
        }
        return maxSubstring;
    }
    /**
     * 哈希表
     * */
    public int lengthOfLongestSubstring1(String s) {
        if("".equals(s)){
            return 0;
        }
        if(s.length() < 2){
            return 1;
        }
        HashMap<Character,Integer> map = new HashMap<>();
        map.put(s.charAt(0),0);
        int next = 1;
        int w = 1;
        int head = 0;
        int tail = 0;
        int maxSubstring = 1;
        boolean flag = false;
        while(next < s.length()){
            flag = false;
            if( map.containsKey(s.charAt(next))){
                head = map.get(s.charAt(next)) >= head ? map.get(s.charAt(next))+1: head;
                map.remove(s.charAt(next));
                map.put(s.charAt(next),next);
                tail++;
                w = tail - head + 1;
                flag = true;
            }
            if(!flag){
                map.put(s.charAt(next),next);
                tail++;
                w++;
            }
            next = tail + 1;
            maxSubstring = maxSubstring < w? w : maxSubstring;
        }

        return maxSubstring;
    }
     public int lengthOfLongestSubstring2(String s) {
        if("".equals(s)){
            return 0;
        }
        if(s.length() < 2){
            return 1;
        }
        HashMap<Character,Integer> map = new HashMap<>();
        map.put(s.charAt(0),0);
        int max = 0;
        int left = 0;
         for (int i = 1; i < s.length(); i++) {
             if(map.containsKey(s.charAt(i))){
                 left = map.get(s.charAt(i)) >= left ? map.get(s.charAt(i)) + 1 : left;
             }
             map.put(s.charAt(i),i);
             max = max < i - left + 1 ? i - left + 1: max;
         }
        return max;
    }


    @Test
    public void test1(){
        System.out.println( lengthOfLongestSubstring1("bbba"));
        System.out.println( lengthOfLongestSubstring("bbba"));
        System.out.println( lengthOfLongestSubstring2("bbba"));

    }
}
