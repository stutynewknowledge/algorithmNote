package maxEnvelopes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 俄罗斯套娃信封
 */
public class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;
        if(n == 0){
            return 0;
        }
        Arrays.sort(envelopes, (int[] a, int[]b) -> {
            if (a[0] != b[0]){
                return a[0] - b[0];
            }
            return b[1] - a[1];
        });
        for (int[] envelope : envelopes) {
            for (int i : envelope) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        int[] hArray = new int[n];
        for(int i = 0; i < n; i++){
            hArray[i] = envelopes[i][1];
        }
        return this.lengthOfLIS(hArray);
    }

    public int lengthOfLIS(int[] nums){
        int n = nums.length;
        if(n == 0){
            return n;
        }
        List<Integer> list = new ArrayList<Integer>();
        list.add(nums[0]);
        for (int i = 1; i < n; i++){
            int listLength = list.size();
            if(nums[i] > list.get(listLength - 1)){
                list.add(nums[i]);
                continue;
            }
            int left = 0;
            int right = listLength - 1;
            while(left < right){
                int mid = left + (right - left) / 2;
                if(nums[i] > list.get(mid)){
                    left = mid + 1;
                }else{
                    right = mid;
                }
            }
            list.set(left, nums[i]);
        }
        return list.size();
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        int[][] envelopes = {{1,3},{3,5},{6,7},{6,8},{8,4},{9,5}};
        System.out.println(s.maxEnvelopes(envelopes));
    }
}
