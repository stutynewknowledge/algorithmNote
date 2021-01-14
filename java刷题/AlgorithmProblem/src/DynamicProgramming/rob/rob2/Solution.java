package DynamicProgramming.rob.rob2;

import java.util.Arrays;

/**
 * 213. 打家劫舍 II
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，能够偷窃到的最高金额。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [2,3,2]
 * 输出：3
 * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
 * 示例 2：
 *
 * 输入：nums = [1,2,3,1]
 * 输出：4
 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * 示例 3：
 *
 * 输入：nums = [0]
 * 输出：0
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 * 题目源自：https://leetcode-cn.com/problems/house-robber-ii/
 */
public class Solution {
    public int noCircularMaxRob(int[] nums){
        int n = nums.length;
        if (n == 0){
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        for(int i = 1; i < n; i++){
            dp[i] = Math.max((i>1 ? dp[i - 2] : 0) + nums[i], dp[i-1]);
        }
        return dp[n-1];
    }

    public int rob(int[] nums) {
        /**
         分治
         1、偷第一家，不偷最后一家
         2、不偷第一家，偷最后一家
         */
        int n = nums.length;
        if(n == 0){
            return 0;
        }
        return Math.max(this.noCircularMaxRob(Arrays.copyOfRange(nums, 1,n)), this.noCircularMaxRob(Arrays.copyOfRange(nums, 0,n-1)));

    }
}
