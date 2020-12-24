package lengthOfLIS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最长上升子序列
 */
public class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0){
            return 0;
        }
        List<Integer> list = new ArrayList<Integer>();
        list.add(nums[0]);
        for(int i = 1; i < n; i++){
            int listLen = list.size();
            if(nums[i] > list.get(listLen - 1)){
                list.add(nums[i]);
                continue;
            }
            int left = 0;
            int right = listLen - 1;
            while(left < right){
                int mid = left + (right - left) / 2;
                if(list.get(mid) < nums[i]){
                    left = mid + 1;
                }else{
                    right = mid;
                }
            }
            list.set(left, nums[i]);
        }
        return list.size();
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        int[] envelopes = {3,5,8,7,4,5};
        System.out.println(s.lengthOfLIS(envelopes));
    }
}
