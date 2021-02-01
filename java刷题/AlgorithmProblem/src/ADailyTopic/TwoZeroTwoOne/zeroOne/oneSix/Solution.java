package ADailyTopic.TwoZeroTwoOne.zeroOne.oneSix;

/**
 * 803. 打砖块
 * 有一个 m x n 的二元网格，其中 1 表示砖块，0 表示空白。砖块 稳定（不会掉落）的前提是：
 *
 * 一块砖直接连接到网格的顶部，或者
 * 至少有一块相邻（4 个方向之一）砖块 稳定 不会掉落时
 * 给你一个数组 hits ，这是需要依次消除砖块的位置。每当消除 hits[i] = (rowi, coli) 位置上的砖块时，对应位置的砖块（若存在）会消失，然后其他的砖块可能因为这一消除操作而掉落。一旦砖块掉落，它会立即从网格中消失（即，它不会落在其他稳定的砖块上）。
 *
 * 返回一个数组 result ，其中 result[i] 表示第 i 次消除操作对应掉落的砖块数目。
 *
 * 注意，消除可能指向是没有砖块的空白位置，如果发生这种情况，则没有砖块掉落。
 *
 *
 *
 * 示例 1：
 *
 * 输入：grid = [[1,0,0,0],[1,1,1,0]], hits = [[1,0]]
 * 输出：[2]
 * 解释：
 * 网格开始为：
 * [[1,0,0,0]，
 *  [1,1,1,0]]
 * 消除 (1,0) 处加粗的砖块，得到网格：
 * [[1,0,0,0]
 *  [0,1,1,0]]
 * 两个加粗的砖不再稳定，因为它们不再与顶部相连，也不再与另一个稳定的砖相邻，因此它们将掉落。得到网格：
 * [[1,0,0,0],
 *  [0,0,0,0]]
 * 因此，结果为 [2] 。
 * 示例 2：
 *
 * 输入：grid = [[1,0,0,0],[1,1,0,0]], hits = [[1,1],[1,0]]
 * 输出：[0,0]
 * 解释：
 * 网格开始为：
 * [[1,0,0,0],
 *  [1,1,0,0]]
 * 消除 (1,1) 处加粗的砖块，得到网格：
 * [[1,0,0,0],
 *  [1,0,0,0]]
 * 剩下的砖都很稳定，所以不会掉落。网格保持不变：
 * [[1,0,0,0],
 *  [1,0,0,0]]
 * 接下来消除 (1,0) 处加粗的砖块，得到网格：
 * [[1,0,0,0],
 *  [0,0,0,0]]
 * 剩下的砖块仍然是稳定的，所以不会有砖块掉落。
 * 因此，结果为 [0,0] 。
 *
 *
 * 提示：
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * grid[i][j] 为 0 或 1
 * 1 <= hits.length <= 4 * 104
 * hits[i].length == 2
 * 0 <= xi <= m - 1
 * 0 <= yi <= n - 1
 * 所有 (xi, yi) 互不相同
 *
 */
public class Solution {
    public int[] hitBricks(int[][] grid, int[][] hits) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] gridCopy = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                gridCopy[i][j] = grid[i][j];
            }
        }

        // 1、先将要敲碎的砖块在copy数组中敲碎
        int len = hits.length;
        for (int i = 0; i < len; i++) {
            gridCopy[hits[i][0]][hits[i][1]] = 0;
        }

        // 2、通过并查集记录敲碎后的砖块与屋顶相连的个数
        int size = m * n;
        // 此处为何为size+1,因为需要创建一个虚拟的节点来表示屋顶
        UnionFind uf = new UnionFind(size + 1);
        // 将第一排与顶点相连接
        for (int j = 0; j < n; j++) {
            if(gridCopy[0][j] == 1){
                uf.union(j, size);
            }
        }
        // 如果当前砖块的左和上为砖块，则将其进行连接
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int index = i * n + j;
                if(gridCopy[i][j] == 1){
                    // 上
                    if(gridCopy[i - 1][j] == 1){
                        uf.union(index - n, index);
                    }
                    // 左 (细节：此处需要判断j是否是在第一列)
                    if(j > 0 && gridCopy[i][j-1] == 1){
                        uf.union(index - 1, index);
                    }

                }

            }
        }

        // 3 将copy中打碎的砖块进行倒着补齐，然后再将补齐后的砖块
        // 与上、下、左、右进行一一的连接，通过比较补齐前与补齐后的比较，
        // 得出敲碎该砖块后会掉落的砖块
        int[] result = new int[len];
        int[][] directions = new int[][]{
                {-1, 0}, // 上
                {1, 0}, // 下
                {0, -1}, // 左
                {0, 1} // 右
        };
        for (int i = len - 1; i >= 0; i--) {
            int x = hits[i][0];
            int y = hits[i][1];
            if(grid[x][y] == 0){
                continue;
            }
            // 获得未补齐前的大小
            int originSize = uf.getSize(size);
            // 如果补回的节点在第0行，将其与屋顶进行连接
            if(x == 0){
                uf.union(y, size);
            }
            for (int j = 0; j < 4; j++) {
                int newX = x + directions[j][0];
                int newY = y + directions[j][1];
                // 需要防止数组下标越界
                if(newX >= 0 && newY >= 0 && newX < m && newY < n && gridCopy[newX][newY] == 1){
                    uf.union(newX * n + newY, x * n + y);
                }
            }
            // 获得连接后的大小
            int cur = uf.getSize(size);
            result[i] = Math.max(0, cur - originSize - 1);
            // 真正的补齐
            gridCopy[x][y] = 1;
        }
        return result;
    }

    private class UnionFind{

        private int[] parents;

        // 记录当前节点的连通节点的数目
        private int[] size;

        UnionFind(int n){
            parents = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if (rootY == rootX){
                return ;
            }

            if(size[rootY] > size[rootX]){
                int temp;
                temp = rootX;
                rootX = rootY;
                rootY = temp;
            }
            parents[rootY] = rootX;
            size[rootX] += size[rootY];
        }

        public int find(int i){
            //  路径压缩
            while (i != parents[i]){
                parents[i] = parents[parents[i]];
                i = parents[i];
            }
            return i;
        }

        public int getSize(int i) {
            int rootI = find(i);
            return size[rootI];
        }
    }
}
