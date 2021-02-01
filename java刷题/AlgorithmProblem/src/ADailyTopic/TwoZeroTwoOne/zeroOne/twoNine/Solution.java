package ADailyTopic.TwoZeroTwoOne.zeroOne.twoNine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 1631. 最小体力消耗路径
 * 你准备参加一场远足活动。给你一个二维 rows x columns 的地图 heights ，其中 heights[row][col] 表示格子 (row, col) 的高度。一开始你在最左上角的格子 (0, 0) ，且你希望去最右下角的格子 (rows-1, columns-1) （注意下标从 0 开始编号）。你每次可以往 上，下，左，右 四个方向之一移动，你想要找到耗费 体力 最小的一条路径。
 *
 * 一条路径耗费的 体力值 是路径上相邻格子之间 高度差绝对值 的 最大值 决定的。
 *
 * 请你返回从左上角走到右下角的最小 体力消耗值 。
 *
 *
 *
 * 示例 1：
 *
 *
 *
 * 输入：heights = [[1,2,2],[3,8,2],[5,3,5]]
 * 输出：2
 * 解释：路径 [1,3,5,3,5] 连续格子的差值绝对值最大为 2 。
 * 这条路径比路径 [1,2,2,2,5] 更优，因为另一条路径差值最大值为 3 。
 * 示例 2：
 *
 *
 *
 * 输入：heights = [[1,2,3],[3,8,4],[5,3,5]]
 * 输出：1
 * 解释：路径 [1,2,3,4,5] 的相邻格子差值绝对值最大为 1 ，比路径 [1,3,5,3,5] 更优。
 * 示例 3：
 *
 *
 * 输入：heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
 * 输出：0
 * 解释：上图所示路径不需要消耗任何体力。
 *
 *
 * 提示：
 *
 * rows == heights.length
 * columns == heights[i].length
 * 1 <= rows, columns <= 100
 * 1 <= heights[i][j] <= 106
 *
 * 题目源自：https://leetcode-cn.com/problems/path-with-minimum-effort/
 */
public class Solution {
    public int minimumEffortPath(int[][] heights) {
        // 1、计算所有变得权重
        int n = heights.length;
        int m = heights[0].length;
        int size = m * n;
        UnionFind uf = new UnionFind(size);
        List<int[]> edges2weight = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int index = i * m + j;
                if(i > 0){
                    edges2weight.add(new int[]{index - m, index, Math.abs(heights[i][j] - heights[i-1][j])});
                }
                if(j > 0){
                    edges2weight.add(new int[]{index - 1, index, Math.abs(heights[i][j] - heights[i][j-1])});
                }
            }
        }
        // 2、对edge2weight以权重从大到小排序
        Collections.sort(edges2weight, (int[] edge1, int[] edge2) -> edge1[2]-edge2[2]);
        // 3、通过并查集维护边的连通性，若0，size-1能够连通，则就代表此权重为最小体力消耗值
        int edgeSize = edges2weight.size();
        for (int i = 0; i < edgeSize; i++) {
            int[] curEdge = edges2weight.get(i);
            uf.union(curEdge[0], curEdge[1]);
            if(uf.isConnected(0, size - 1)){
                return curEdge[2];
            }
        }
        return 0;
    }

    private class UnionFind{
        private int[] parents;

        UnionFind(int n){
            parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
        }

        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if(rootX == rootY){
                return ;
            }
            parents[rootX] = rootY;
        }

        public int find(int i){
            while (i != parents[i]){
                parents[i] = parents[parents[i]];
                i = parents[i];
            }
            return i;
        }

        public boolean isConnected(int x, int y){
            return find(x) == find(y);
        }
    }
}
