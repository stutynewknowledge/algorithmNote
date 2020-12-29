package maxProfit.maxProfit4;

/**
 * 买卖股票最佳时机IV
 */
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n == 0 || prices == null){
            return 0;
        }
        // 情况1: 当k达到临界值
        int kCritical = n / 2;
        if (k >= kCritical){
            return kInfiniteMaxProfit(prices);
        }
        // 情况2: k小于临界值
        int[][][] dp = new int[n][k+1][2];
        // 初始值
        for(int i = 0; i <= k; i++){
            dp[0][i][0] = 0;
            dp[0][i][1] = -prices[0];
        }
        for (int i = 1; i < n; i++){
            for(int j = k; j > 0; j--){
                dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0] - prices[i]);
            }
        }
        return dp[n-1][k][0];
    }

    // k达到临界值，可以将k看作为无穷大
    public int kInfiniteMaxProfit(int[] prices){
        int n = prices.length;
        int profit0 = 0, profit1 = -prices[0];
        for(int i = 0; i < n; i++){
            profit0 = Math.max(profit0, profit1+prices[i]);
            profit1 = Math.max(profit1, profit0 - prices[i]);
        }
        return profit0;
    }
}
