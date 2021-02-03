package slidingWindow.medianFinder;

import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 *
 * 例如，
 *
 * [2,3,4] 的中位数是 3
 *
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 *
 * 设计一个支持以下两种操作的数据结构：
 *
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 示例：
 *
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * 进阶:
 *
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 *
 * 题目源自：https://leetcode-cn.com/problems/find-median-from-data-stream/
 *
 * 思路：双优先队列(堆)
 */
public class MedianFinder {
    private PriorityQueue<Integer> small;
    private PriorityQueue<Integer> large;
    private int smallSize;
    private int largeSize;
    public MedianFinder() {
        this.small = new PriorityQueue<>((nums1, nums2)->nums2.compareTo(nums1));
        this.large = new PriorityQueue<>((nums1, nums2)->nums1.compareTo(nums2));
    }

    public void addNum(int num) {
        if (small.isEmpty() || num <= small.peek()){
            smallSize++;
            small.offer(num);
        }else{
            largeSize++;
            large.offer(num);
        }
        makeBalance();
    }

    public double findMedian() {
        return ((smallSize+largeSize) % 2) == 1 ? small.peek() : ((double)(small.peek()) + large.peek()) / 2;
    }

    // 维护small和large的数量
    private void makeBalance(){
        if(smallSize > largeSize+1){
            large.offer(small.poll());
            largeSize++;
            smallSize--;
        }else if (smallSize < largeSize){
            small.offer(large.poll());
            largeSize--;
            smallSize++;
        }
    }
}
