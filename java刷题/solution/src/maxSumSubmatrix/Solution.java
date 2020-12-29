package maxSumSubmatrix;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int result = Integer.MIN_VALUE;
        int n = matrix.length;
        if (n == 0){
            return 0;
        }
        int m = matrix[0].length;
        if(m == 0){
            return 0;
        }
        for (int i = 0; i < n; i++) {
            int[] curLine = new int[m];
            for (int j = i; j < n; j++) {
                for (int v = 0; v < m; v++) {
                    curLine[v] += matrix[j][v];
                }
                List<Integer> list = new ArrayList<>();
                list.add(0);
                int curSum = 0;
                for (int l = 0; l < m; l++) {
                    curSum += curLine[l];
                    int loc = this.bisect_left(list, curSum - k);
                    System.out.println(loc);
                    if (loc < list.size()){
                        result = Math.max(result, curSum - list.get(loc));
                    }
                    this.insort_right(list, curSum);
                }
                System.out.println(list);
            }
        }
        return result;
    }

    /**
     * 二分法，在数组中寻找到等于目标值的索引，若没有则返回数组长度。
     * @param nums：排序的数组 int[]
     * @param target：目标值 int
     * @return：int
     */
    public int bisect_left(List<Integer> nums, int target){
        int lo = 0;
        int hi = nums.size();
        while (lo < hi){
            int mid = (lo + hi) / 2;
            if(target > nums.get(mid)){
                lo = mid + 1;
            }else{
                hi = mid;
            }
        }
        return lo;
    }

    /**
     *
     * @param nums
     * @param target
     * @return
     */
    public static int bisect_right(List<Integer> nums, int target){
        int lo = 0;
        int hi = nums.size();
        while (lo < hi){
            int mid = (lo + hi) / 2;
            if (target < nums.get(mid)){
                hi = mid;
            }else{
                lo = mid + 1;
            }
        }
        return lo;
    }

    /**
     * 使用二分查找到数组中第一个比目标值大的数的索引，并且将目标值插入其中
     * @param nums:升序列表；
     * @param target：目标值
     * @return
     */
    public void insort_right(List<Integer> nums, Integer target){
        int lo = 0;
        int hi = nums.size();
        while (lo < hi){
            int mid = (hi + lo) / 2;
            if(target < nums.get(mid)){
                hi = mid;
            }else {
                lo = mid + 1;
            }
        }
        nums.add(lo,target);
    }



    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 10; i+=2) {
//            list.add(i);
//        }
//        System.out.println(list);
        Solution s = new Solution();
//        s.bisect_insert(list, 10);
//        System.out.println(list);

        int[][] nums = new int[][]{
                {1,0,1},
                {0,-2,3}
        };
        System.out.println(s.maxSumSubmatrix(nums, 2));
    }

}
