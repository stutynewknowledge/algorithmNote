package ADailyTopic.TwoZeroTwoOne.zeroTwo.zeroThree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 480. 滑动窗口中位数
 * 中位数是有序序列最中间的那个数。如果序列的大小是偶数，则没有最中间的数；此时中位数是最中间的两个数的平均数。
 *
 * 例如：
 *
 * [2,3,4]，中位数是 3
 * [2,3]，中位数是 (2 + 3) / 2 = 2.5
 * 给你一个数组 nums，有一个大小为 k 的窗口从最左端滑动到最右端。窗口中有 k 个数，每次窗口向右移动 1 位。你的任务是找出每次窗口移动后得到的新窗口中元素的中位数，并输出由它们组成的数组。
 *
 *
 *
 * 示例：
 *
 * 给出 nums = [1,3,-1,-3,5,3,6,7]，以及 k = 3。
 *
 * 窗口位置                      中位数
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       1
 *  1 [3  -1  -3] 5  3  6  7      -1
 *  1  3 [-1  -3  5] 3  6  7      -1
 *  1  3  -1 [-3  5  3] 6  7       3
 *  1  3  -1  -3 [5  3  6] 7       5
 *  1  3  -1  -3  5 [3  6  7]      6
 *  因此，返回该滑动窗口的中位数数组 [1,-1,-1,3,5,6]。
 *
 *
 *
 * 提示：
 *
 * 你可以假设 k 始终有效，即：k 始终小于输入的非空数组的元素个数。
 * 与真实值误差在 10 ^ -5 以内的答案将被视作正确答案。
 *
 * 题目源自:https://leetcode-cn.com/problems/sliding-window-median/
 */
public class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] result = new double[n - k + 1];
        if (n == 0){
            return result;
        }
        DralHeap dh = new DralHeap(k);
        for (int i = 0; i < k; i++) {
            dh.insert(nums[i]);
        }
        result[0] = dh.getMedian();
        for (int i = k; i < n; i++) {
            dh.insert(nums[i]);
            dh.earse(nums[i - k]);
            result[i - k + 1] = dh.getMedian();
        }
        return result;
    }

    private class DralHeap{
        // 存储较小的 (大根堆)
        private PriorityQueue<Integer> small;
        // 存储较大的 (小根堆)
        private PriorityQueue<Integer> large;
        // 哈希表 (延迟删除) key为要被删除的数，value为被删除的数的个数
        private Map<Integer, Integer> delayed;

        private int k;

        // small 和 large 当前包含的元素个数，需要扣除被「延迟删除」的元素
        private int smallSize, largeSize;

        DralHeap(int k){
            this.k = k;
            // 建立大根堆
            this.small = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });
            // 建立小根堆
            this.large = new PriorityQueue<Integer>((nums1, nums2)-> nums1.compareTo(nums2));
            this.delayed = new HashMap<>();
        }

        // 获得中位数
        public double getMedian(){
            return (k % 2) == 1 ? small.peek() : ((double)this.small.peek() + this.large.peek()) / 2;
        }

        // 插入一个数
        public void insert(int num){
            if (small.isEmpty() || small.peek() >= num){
                small.offer(num);
                smallSize++;
            }else{
                large.offer(num);
                largeSize++;
            }
            makeBalance();
        }

        /**
         * 情况1： 如果num与small和large的堆顶元素不相同，则num是需要被延迟删除的，此时delayed中的num+1
         * 情况2：如果num与small堆顶元素相同，那么该元素需要被立即删除(将num在delayed中的value+1，然后在立即调用prune(num)即可做到立即删除)
         * @param num
         */
        public void earse(int num){

            delayed.put(num, delayed.getOrDefault(num, 0) + 1);

            if(num <= small.peek()){
                // 虽然是延迟删除，但是需要将此时的small的大小进行-1
                smallSize--;
                if (num == small.peek()){
                    prune(small);
                }
            }else{
                largeSize--;
                if (num == large.peek()){
                    prune(large);
                }
            }
            makeBalance();
        }

        // 维持large与small的个数保持一致或者small比large多一个
        public void makeBalance(){
            if(smallSize > largeSize + 1){
                // 情况1： 此时 small比large中的元素多两个
                large.offer(small.poll());
                largeSize++;
                smallSize--;
                // small的堆顶元素被移除，则需要对small的堆顶元素惊醒prune，即看其是否是需要被删除的
                prune(small);
            }else if (smallSize < largeSize){
                // 情况2：此时的large中的元素比small中的元素多1个
                small.offer(large.poll());
                smallSize++;
                largeSize--;
                prune(large);
            }
        }

        // 保持heap(small or large)中的堆顶元素始终是不需要被删除的，并且减少delayed中对应元素的值
        public void prune(PriorityQueue<Integer> heap){
            while(!heap.isEmpty()){
                int num = heap.peek();
                if (delayed.containsKey(num)){
                    delayed.put(num, delayed.get(num) - 1);
                    if (delayed.get(num) == 0){
                        delayed.remove(num);
                    }
                    heap.poll();
                }else{
                    break;
                }
            }
        }
    }
}
