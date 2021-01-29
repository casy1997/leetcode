package onetoten;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * 1.遍历 穷举
 * 2.hashtab 存找
 * */
public class TwoSum {
    /**
     * 1.遍历 穷举
     * */
    public int[] twoSum(int[] nums, int target) {
        int[] results = new int[]{0,1};
        if(nums.length == 2){
            return results;
        }
        for(int i = 0; i < nums.length; i++){
            for(int j = i+1; j <nums.length; j++ ){
                if(nums[i] + nums[j] == target){
                    results[0] = i;
                    results[1] = j;
                    break;
                }
            }
        }
        return results;
    }

    /**
     * 2.hashMap存找
     * */
    public int[] twoSum1(int[] nums, int target) {
        int[] results = new int[]{0,1};
        if ( nums.length == 2){
            results[0] = 0;
            results[1] = 1;
            return results;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            int aim = target - nums[i];
            if(map.containsKey(aim)){
                results[0] = i;
                results[1] = map.get(aim);
                return results;
            }
            map.put(nums[i],i);
        }
        return results;
    }

}
