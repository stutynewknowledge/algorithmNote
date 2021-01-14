package DynamicProgramming.getMaxMatrix;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 最大子矩阵
 */
public class Solution {
    public Integer[] getMaxMatrix(int[][] matrix) {
        int n = matrix.length;
        if (n == 0){
            return null;
        }
        int m = matrix[0].length;
        if(m == 0){
            return null;
        }
        int maxSum = Integer.MIN_VALUE;
        int beginN = 0,beginW = 0;
        Integer[] result = new Integer[4];
        for (int i = 0; i < n; i++) {
            int[] curLine = new int[m];
            for (int j = i; j < n; j++) {
                int curSum = 0;
                for (int k = 0; k < m; k++) {
                    curLine[k] += matrix[j][k];
                    if (curSum + curLine[k] > curLine[k]){
                        curSum += curLine[k];
                    }else{
                        curSum = curLine[k];
                        beginN = i;
                        beginW = k;
                    }
                    if(maxSum < curSum){
                        maxSum = curSum;
                        result[0] = beginN;
                        result[1] = beginW;
                        result[2] = j;
                        result[3] = k;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] matrix = {
                {9,-8,1,3,-2},
                {-3,7,6,-2,4},
                {6,-4,-4,8,-7}
        };
        Arrays.stream(s.getMaxMatrix(matrix)).forEach(System.out::println);
    }
}
