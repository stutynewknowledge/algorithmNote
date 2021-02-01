package ADailyTopic.TwoZeroTwoOne.zeroOne.oneFive;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 移除最多的同行或同列石头
 * n 块石头放置在二维平面中的一些整数坐标点上。每个坐标点上最多只能有一块石头。
 * <p>
 * 如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头。
 * <p>
 * 给你一个长度为 n 的数组 stones ，其中 stones[i] = [xi, yi] 表示第 i 块石头的位置，返回 可以移除的石子 的最大数量。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
 * 输出：5
 * 解释：一种移除 5 块石头的方法如下所示：
 * 1. 移除石头 [2,2] ，因为它和 [2,1] 同行。
 * 2. 移除石头 [2,1] ，因为它和 [0,1] 同列。
 * 3. 移除石头 [1,2] ，因为它和 [1,0] 同行。
 * 4. 移除石头 [1,0] ，因为它和 [0,0] 同列。
 * 5. 移除石头 [0,1] ，因为它和 [0,0] 同行。
 * 石头 [0,0] 不能移除，因为它没有与另一块石头同行/列。
 * 示例 2：
 * <p>
 * 输入：stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
 * 输出：3
 * 解释：一种移除 3 块石头的方法如下所示：
 * 1. 移除石头 [2,2] ，因为它和 [2,0] 同行。
 * 2. 移除石头 [2,0] ，因为它和 [0,0] 同列。
 * 3. 移除石头 [0,2] ，因为它和 [0,0] 同行。
 * 石头 [0,0] 和 [1,1] 不能移除，因为它们没有与另一块石头同行/列。
 * 示例 3：
 * <p>
 * 输入：stones = [[0,0]]
 * 输出：0
 * 解释：[0,0] 是平面上唯一一块石头，所以不可以移除它。
 * <p>
 * 提示：
 * <p>
 * 1 <= stones.length <= 1000
 * 0 <= xi, yi <= 104
 * 不会有两块石头放在同一个坐标点上
 * <p>
 * 题目源自：https://leetcode-cn.com/problems/most-stones-removed-with-same-row-or-column/
 */
public class Solution {
    public int removeStones(int[][] stones) {
        UnionFind unionFind = new UnionFind();
        int n = stones.length;
        for (int i = 0; i < n; i++) {
            unionFind.union(stones[i][0], stones[i][1] + 10001);
        }
        return n - unionFind.getCount();
    }

    /**
     * 并查集
     */
    private class UnionFind {
        /*
        连接区域的个数
         */
        private int count;

        /*
        连接图 key为父节点，value为儿子节点
         */
        private Map<Integer, Integer> parents;

        UnionFind() {
            this.parents = new HashMap<>();
            this.count = 0;
        }

        public int find(int i) {
            // 如果在图中没有找到节点，那么就新加入一个节点，并且连接区域数量+1；
            if (!parents.containsKey(i)) {
                this.parents.put(i, i);
                this.count++;
            }
            if (i != parents.get(i)) {
                parents.put(i, find(parents.get(i)));
            }
            return parents.get(i);
        }

        public void union(int x, int y) {
            // 行的祖父
            int rootX = find(x);
            // 列的祖父
            int rootY = find(y);
            if (rootX == rootY) {
                return;
            }
            // 将行的根节点与列的根节点进行合并
            this.parents.put(rootX, rootY);
            this.count--;
        }

        public int getCount() {
            return count;
        }
    }
}
