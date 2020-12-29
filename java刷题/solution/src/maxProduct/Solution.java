package maxProduct;

/**
 * 乘积最大子数组
 */
public class Solution {
    public int maxProduct(int[] nums){
        int n = nums.length;
        if (n == 0){
            return 0;
        }
        int[] maxP = new int[nums.length];
        int[] minP = new int[nums.length];
        int result = nums[0];
        maxP[0] = nums[0];
        minP[0] = nums[0];
        for (int i = 1; i < n; i++) {
            maxP[i] = Math.max(nums[i] * maxP[i - 1], Math.max(nums[i], nums[i] * minP[i-1]));
            minP[i] = Math.min(nums[i] * minP[i - 1], Math.min(nums[i], nums[i] * maxP[i-1]));
            result = Math.max(maxP[i],result);
        }

        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = {2,3,-2,4};
        System.out.println(s.maxProduct(nums));
    }
}
