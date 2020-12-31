package maxSizeSlices;

import java.util.Arrays;

/**
 * 给你一个披萨，它由 3n 块不同大小的部分组成，现在你和你的朋友们需要按照如下规则来分披萨：
 *
 * 你挑选 任意 一块披萨。
 * Alice 将会挑选你所选择的披萨逆时针方向的下一块披萨。
 * Bob 将会挑选你所选择的披萨顺时针方向的下一块披萨。
 * 重复上述过程直到没有披萨剩下。
 * 每一块披萨的大小按顺时针方向由循环数组 slices 表示。
 *
 * 请你返回你可以获得的披萨大小总和的最大值。
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/pizza-with-3n-slices
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int maxSizeSlices(int[] slices) {
        int n = slices.length;
        return Math.max(
                    maxSizeSlicesHelper(Arrays.copyOfRange(slices, 1, slices.length), n / 3),
                    maxSizeSlicesHelper(Arrays.copyOfRange(slices, 0, slices.length-1), n / 3)
                );
    }

    public int maxSizeSlicesHelper(int[] newSlices, int choices){
        int n = newSlices.length;
        int[][] dp = new int[n+1][choices+1];
        for (int i = 1; i < n+1; i++) {
            for(int j = 1; j < choices + 1; j++){
                dp[i][j] = Math.max((i > 1?dp[i-2][j-1]:0) + newSlices[i-1], dp[i-1][j]);
            }
        }
        return dp[n][choices];
    }
}
