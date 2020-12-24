package maxSubArray;

/**
 * 最长子序和
 */
public class Solution {
    public int maxSubArray(int[] nums){

        int n = nums.length;
        if (n == 0){
            return n;
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        // 考虑当数组中只有一个数的时候
        int result = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            result = Math.max(dp[i], result);
        }
        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        int[] nums1 = new int[]{1};
        System.out.println(s.maxSubArray(nums1));
    }
}
