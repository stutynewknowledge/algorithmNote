package DynamicProgramming.maxSubarraySumCircular;

/**
 * 环形最大子数组和
 */
public class Solution {
    public int maxSubarraySumCircular(int[] A) {

        int n = A.length;
        if (n == 0){
            return 0;
        }
        int pre = A[0];
        int noCircularMaxNum = A[0];
        int preMin = A[0];
        int circularMinNum = A[0];
        int totalSum = A[0];
        for (int i = 1; i < n; i++) {
            // 第一种情况
            pre = Math.max(A[i], pre+A[i]);
            noCircularMaxNum = Math.max(noCircularMaxNum, pre);
            // 第二种情况
            preMin = Math.min(preMin+A[i], A[i]);
            totalSum += A[i];
            circularMinNum = Math.min(preMin, circularMinNum);
        }

        int circularMaxNum = totalSum - circularMinNum;

        return circularMinNum ==  totalSum ? noCircularMaxNum:Math.max(circularMaxNum, noCircularMaxNum);
    }
}
