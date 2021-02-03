package ADailyTopic.TwoZeroTwoOne.zeroTwo.zeroFour;

/**
 * 643. 子数组最大平均数 I
 * 给定 n 个整数，找出平均数最大且长度为 k 的连续子数组，并输出该最大平均数。
 *
 *
 *
 * 示例：
 *
 * 输入：[1,12,-5,-6,50,3], k = 4
 * 输出：12.75
 * 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
 *
 *
 * 提示：
 *
 * 1 <= k <= n <= 30,000。
 * 所给数据范围 [-10,000，10,000]。
 *
 * 题目源自：https://leetcode-cn.com/problems/maximum-average-subarray-i/
 */
public class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        if (n == 0){
            return 0d;
        }
        int sumK = 0;
        for (int i = 0; i < k; i++) {
            sumK += nums[i];
        }
        int curSumK = sumK;
        int left = 0;
        int right = k - 1;
        while (right + 1 < n){
            right++;
            curSumK +=(nums[right] - nums[left]);
            sumK = Math.max(curSumK, sumK);
            left++;

        }
        return (double)sumK / k;
    }
}
