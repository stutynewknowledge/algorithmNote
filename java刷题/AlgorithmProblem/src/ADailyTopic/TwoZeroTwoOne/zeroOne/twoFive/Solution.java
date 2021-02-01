package ADailyTopic.TwoZeroTwoOne.zeroOne.twoFive;

/**
 * 959. 由斜杠划分区域
 * 在由 1 x 1 方格组成的 N x N 网格 grid 中，每个 1 x 1 方块由 /、\ 或空格构成。这些字符会将方块划分为一些共边的区域。
 *
 * （请注意，反斜杠字符是转义的，因此 \ 用 "\\" 表示。）。
 *
 * 返回区域的数目。
 *
 *
 *
 * 示例 1：
 *
 * 输入：
 * [
 *   " /",
 *   "/ "
 * ]
 * 输出：2
 * 解释：2x2 网格如下：
 *
 * 示例 2：
 *
 * 输入：
 * [
 *   " /",
 *   "  "
 * ]
 * 输出：1
 * 解释：2x2 网格如下：
 *
 * 示例 3：
 *
 * 输入：
 * [
 *   "\\/",
 *   "/\\"
 * ]
 * 输出：4
 * 解释：（回想一下，因为 \ 字符是转义的，所以 "\\/" 表示 \/，而 "/\\" 表示 /\。）
 * 2x2 网格如下：
 *
 * 示例 4：
 *
 * 输入：
 * [
 *   "/\\",
 *   "\\/"
 * ]
 * 输出：5
 * 解释：（回想一下，因为 \ 字符是转义的，所以 "/\\" 表示 /\，而 "\\/" 表示 \/。）
 * 2x2 网格如下：
 *
 * 示例 5：
 *
 * 输入：
 * [
 *   "//",
 *   "/ "
 * ]
 * 输出：3
 * 解释：2x2 网格如下：
 *
 *
 *
 * 提示：
 *
 * 1 <= grid.length == grid[0].length <= 30
 * grid[i][j] 是 '/'、'\'、或 ' '。
 *
 * 题目源自：https://leetcode-cn.com/problems/regions-cut-by-slashes/
 */
public class Solution {
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        if(n == 1){
            return 0;
        }
        int sectionSize = 4 * n * n;
        UnionFind unionFind = new UnionFind(sectionSize);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = 4 * (i * n + j);
                char curChar = grid[i].charAt(j);
                if(curChar == ' '){
                    // 是空格连接0、1、2、3
                    unionFind.union(index, index+1);
                    unionFind.union(index+1, index+2);
                    unionFind.union(index+2, index+3);
                }else if(curChar == '\\'){
                    // 是反斜杠连接0、1 和 2、3
                    unionFind.union(index, index+1);
                    unionFind.union(index+2, index+3);
                }else{
                    // 是斜杠连接0、3 和 1、2
                    unionFind.union(index, index+3);
                    unionFind.union(index+1, index+2);
                }
                // 相临两边合并
                if(j + 1 < n){
                    unionFind.union(index + 1, 4 * (i * n + j + 1) + 3);
                }
                if(i + 1 < n){
                    unionFind.union(index + 2, 4 * ((i + 1) * n + j) + 2);
                }
            }
        }
        return unionFind.getResult();
    }

    private class UnionFind{

        private int[] parents;

        private int result;

        UnionFind(int n){
            this.parents = new int[n];
            this.result = n;
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
            this.parents[rootX] = this.parents[rootY];
            result--;
        }

        public int getResult() {
            return result;
        }
    }
}
