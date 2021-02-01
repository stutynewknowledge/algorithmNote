package ADailyTopic.TwoZeroTwoOne.zeroOne.threeZero;

/**
 * 778. 水位上升的泳池中游泳
 * 在一个 N x N 的坐标方格 grid 中，每一个方格的值 grid[i][j] 表示在位置 (i,j) 的平台高度。
 *
 * 现在开始下雨了。当时间为 t 时，此时雨水导致水池中任意位置的水位为 t 。你可以从一个平台游向四周相邻的任意一个平台，但是前提是此时水位必须同时淹没这两个平台。假定你可以瞬间移动无限距离，也就是默认在方格内部游动是不耗时的。当然，在你游泳的时候你必须待在坐标方格里面。
 *
 * 你从坐标方格的左上平台 (0，0) 出发。最少耗时多久你才能到达坐标方格的右下平台 (N-1, N-1)？
 *
 *
 *
 * 示例 1:
 *
 * 输入: [[0,2],[1,3]]
 * 输出: 3
 * 解释:
 * 时间为0时，你位于坐标方格的位置为 (0, 0)。
 * 此时你不能游向任意方向，因为四个相邻方向平台的高度都大于当前时间为 0 时的水位。
 *
 * 等时间到达 3 时，你才可以游向平台 (1, 1). 因为此时的水位是 3，坐标方格中的平台没有比水位 3 更高的，所以你可以游向坐标方格中的任意位置
 * 示例2:
 *
 * 输入: [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
 * 输出: 16
 * 解释:
 *  0  1  2  3  4
 * 24 23 22 21  5
 * 12 13 14 15 16
 * 11 17 18 19 20
 * 10  9  8  7  6
 *
 * 最终的路线用加粗进行了标记。
 * 我们必须等到时间为 16，此时才能保证平台 (0, 0) 和 (4, 4) 是连通的
 *
 *
 * 提示:
 *
 * 2 <= N <= 50.
 * grid[i][j] 是 [0, ..., N*N - 1] 的排列。
 *
 * 题目源自：https://leetcode-cn.com/problems/swim-in-rising-water/
 */
public class Solution {
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int size = n * n;

        // indexes索引代表平台高度，值代表此平台高度的位置
        int[] indexes = new int[size];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                indexes[grid[i][j]] = i * n + j;
            }
        }

        // 使用并查集模拟下雨
        int[][] directions = new int[][]{
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };
        UnionFind uf = new UnionFind(size);
        for (int i = 0; i < size; i++) {
            int x = indexes[i] / n;
            int y = indexes[i] % n;
            for (int[] direction : directions) {
                int newX = direction[0] + x;
                int newY = direction[1] + y;
                if(newX >= 0 && newX < n && newY >= 0 && newY < n && grid[newX][newY] < i){
                    uf.union(indexes[i], newX * n + newY);
                }
                if(uf.isConnected(0, size-1)){
                    return i;
                }
            }
        }
        return 0;
    }

    private class UnionFind{

        private int[] parents;

        UnionFind(int n){
            this.parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
        }

        public int find(int i){
            if(i != parents[i]){
                this.parents[i] = find(this.parents[i]);
            }
            return this.parents[i];
        }

        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if(rootX == rootY){
                return;
            }
            this.parents[rootX] = rootY;
        }

        public boolean isConnected(int x, int y){
            return find(x) == find(y);
        }
    }
}
