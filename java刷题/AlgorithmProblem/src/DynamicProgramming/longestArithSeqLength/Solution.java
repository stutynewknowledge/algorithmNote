package DynamicProgramming.longestArithSeqLength;


import java.util.HashMap;
import java.util.Map;

/**
 * 1027. 最长等差数列
 * 给定一个整数数组 A，返回 A 中最长等差子序列的长度。
 *
 * 回想一下，A 的子序列是列表 A[i_1], A[i_2], ..., A[i_k]
 * 其中 0 <= i_1 < i_2 < ... < i_k <= A.length - 1。
 * 并且如果 B[i+1] - B[i]( 0 <= i < B.length - 1) 的值都相同，那么序列 B 是等差的。
 * 示例 1：
 *
 * 输入：[3,6,9,12]
 * 输出：4
 * 解释：
 * 整个数组是公差为 3 的等差数列。
 * 示例 2：
 *
 * 输入：[9,4,7,2,10]
 * 输出：3
 * 解释：
 * 最长的等差子序列是 [4,7,10]。
 * 示例 3：
 *
 * 输入：[20,1,15,3,10,5,8]
 * 输出：4
 * 解释：
 * 最长的等差子序列是 [20,15,10,5]。
 *
 *
 * 提示：
 *
 * 2 <= A.length <= 2000
 * 0 <= A[i] <= 10000
 */
public class Solution {
    public int longestArithSeqLength(int[] A) {
        int n = A.length;
        Map[] dp = new Map[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new HashMap<Integer, Integer>();
        }
        dp[0] = new HashMap<Integer, Integer>();
        int result = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // 公差
                int dis = A[i] - A[j];
                int length = (int)(dp[j].get(dis) == null ? 1 : dp[j].get(dis)) + 1;
                dp[i].put(dis, length);
                result = Math.max((int)dp[i].get(dis), result);
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(dp[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        new Solution().longestArithSeqLength(new int[]{3,6,9,12});
    }
}