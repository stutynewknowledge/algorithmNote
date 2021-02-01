package ADailyTopic.TwoZeroTwoOne.zeroOne.zeroSeven;

/**
 * 547. 省份数量
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 *
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 *
 * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
 *
 * 返回矩阵中 省份 的数量。
 *
 *
 *
 * 示例 1：
 *
 *
 * 输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
 * 输出：2
 * 示例 2：
 *
 *
 * 输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
 * 输出：3
 *
 *
 * 提示：
 *
 * 1 <= n <= 200
 * n == isConnected.length
 * n == isConnected[i].length
 * isConnected[i][j] 为 1 或 0
 * isConnected[i][i] == 1
 * isConnected[i][j] == isConnected[j][i]
 */
public class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind unionFind = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                unionFind.union(i, j, isConnected[i][j]);
            }
        }

        return unionFind.getProvinceNum();
    }

    private class UnionFind{
        private int[] parents;
        private int provinceNum;

        UnionFind(int n){
            parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
            provinceNum = n;
        }

        public int find(int i){
            if (i != parents[i]){
                parents[i] = find(parents[i]);
            }
            return parents[i];
        }
        
        public void union(int x, int y, int isConnectedValue){
            int rootX = find(x);
            int rootY = find(y);
            if(rootX != rootY){
                if(isConnectedValue == 1){
                    parents[rootX] = parents[rootY];
                    provinceNum--;
                }
            }
        }

        public int getProvinceNum() {
            return provinceNum;
        }
    }

    public static void main(String[] args) {

        int[][] isConnected = new int[][]{
                {1,1,0},
                {1,1,0},
                {0,0,1}
        };
        System.out.println(new Solution().findCircleNum(isConnected));
    }
}
