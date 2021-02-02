package onetoten;

import org.junit.Test;

/**
 * 最长回文子串
 * */
public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        if(s == null ){
            return null;
        }
        //统一奇偶
        String ss;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append('#');
            sb.append(s.charAt(i));
        }
        sb.append('#');
        ss = sb.toString();
        int[] center = new int[ss.length()];
        center[0] = 0; //每个位置的回文半径
        int right = -1; //右边界
        int c = -1; //离右边界最近的回文中心
        int max = 0; //最大回文长度
        int maxc = 0;//回文中心
        for (int i = 0; i < ss.length(); i++) {
            /**
             * 1. i 在右边界外   暴力扩即可
             * 2. i 在右边界内
             * 2.1 i' 回文区域在 边界内
             * 2.2 i' 回文区域在 边界外
             * 2.3 i' 回文区域在 边界上
             * */
            //判断 i 在右边界内吗
            //在外边
            if(i >= right){
                for (int j = i ;j < ss.length(); j++) {
                    if(2*i - j < 0){
                        break;
                    }
                    if(ss.charAt(2*i - j) == ss.charAt(j)){
                        center[i] = (j - i)*2+1;
                        right = j;
                    }else{
                        break;
                    }
                }
                c = i;
            }else{
                //查找i' 的回文区域
                int li = 2*c - i;
                int left = 2*c - right;
                //在边界外
                if(li - center[li]/2 < left){
                    center[i] = 2*(right - i)+1;
                    c = i;
                }else if(li - center[li]/2 > left){//在边界内
                    center[i] = center[li];
                }else{//在边界上
                    for (int l = i + center[li]/2; l < ss.length(); l++) {
                        if(2*i - l < 0){
                            break;
                        }
                        if(ss.charAt(l) == ss.charAt(2*i - l)){
                            center[i] = 2*(l - i)+1;
                            right = l;
                        }else{
                            break;
                        }
                    }
                    c = i;
                }
            }
            if(center[i]/2 > max){
                max = center[i]/2;
                maxc = c/2;
            }
        }
        char[] result = new char[max];
        if(max % 2 == 0){
            s.getChars(maxc -max/2,maxc + max/2 ,result,0);
        }else{
            s.getChars(maxc - max/2 ,maxc + max/2+1,result,0);
        }
        String results = new String(result);

        return results;
    }

    public String longestPalindrome1(String s) {
        if(s.length() < 2){
            return s;
        }
        int max =  0;
        int maxc = -1;
        int R = 1;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if( 2*i - j < 0){
                    break;
                }
                if(s.charAt(j) != s.charAt(2*i - j)){
                    break;
                }
                R = j - i + 1;
            }
            if(max < 2*R - 1){
                maxc = i;
                max = 2*R - 1;
            }
        }
        R = 1;
        for (int i = 0; i < s.length() - 1; i++) {
            cur = i;
            next = i + 1;
            while(s.charAt(cur) == s.charAt(next)){
                R = next - cur + 1;
                cur--;
                next++;
                if(cur < 0 || next >= s.length()){
                    break;
                }
            }
            if(R > max){
                maxc = i;
                max= R;
            }

        }
        /**
         * The first character to be copied is at index srcBegin;
         * the last character to be copied is at index srcEnd-1.
         * */
        char[] result = new char[max];
        if(max % 2 == 0){
            s.getChars(maxc+1 -max/2,maxc + max/2 +1,result,0);
        }else{
            s.getChars(maxc - max/2 ,maxc + max/2+1,result,0);
        }
        String results = new String(result);

        return results;
    }
/**
 *动态规划
 */
    public String longestPalindrome2(String s){
        if (s.length() <2) {
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];// 默认赋值为false
        int start = 0;
        int max = 1;
        //初始化对角线上的元素为true
        for(int i = 0; i < s.length(); i++ ){
            dp[i][i] = true;
        }
        for (int j = 1; j < s.length(); j++) {
            for (int i = j - 1; i >= 0 ; i--) {
                if(i + 1 <= j - 1) {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1];
                }else{
                    dp[i][j] = s.charAt(i) ==s.charAt(j);
                }
                if(dp[i][j] && j - i + 1 > max) {
                        max = j - i + 1;
                        start = i;
                    }
            }
        }
        return s.substring(start,start+max);

    }

    @Test
    public void test1(){
        String[] s = new String[]{"aabaa","aabbaa","abbabba","aba","abc","aa","ab","aabc"};
        for (String s1 : s) {
            System.out.println(longestPalindrome2(s1));
        }
    }
    @Test
    public void test2(){
        String s = "abbabba";
        System.out.println(longestPalindrome(s));
    }
}
