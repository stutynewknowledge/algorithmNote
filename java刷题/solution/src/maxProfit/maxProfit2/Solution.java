package maxProfit.maxProfit2;

/**
 * 买卖股票最佳时机II
 */
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if(prices == null || n == 0){
            return 0;
        }
        /**
         int[][] dp = new int[n][2];
         dp[0][0] = 0;
         dp[0][1] = -prices[0];
         for(int i = 1; i < n; i++){
         dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
         dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
         }
         return dp[n-1][0];
         */
        // 优化：i至于i-1有关，空间可以优化到O(1)
        int profit0 = 0, profit1 = -prices[0];
        for(int i = 0; i < n; i++){
            int newProfit0 = Math.max(profit0, profit1+prices[i]);
            int newProfit1 = Math.max(profit1, profit0 - prices[i]);
            profit0 = newProfit0;
            profit1 = newProfit1;
        }
        return profit0;
    }
}
