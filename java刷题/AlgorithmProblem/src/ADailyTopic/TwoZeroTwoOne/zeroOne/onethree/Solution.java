package ADailyTopic.TwoZeroTwoOne.zeroOne.onethree;


/**
 * 冗余连接
 * 在本问题中, 树指的是一个连通且无环的无向图。
 * <p>
 * 输入一个图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
 * <p>
 * 结果图是一个以边组成的二维数组。每一个边的元素是一对[u, v] ，满足 u < v，表示连接顶点u 和v的无向图的边。
 * <p>
 * 返回一条可以删去的边，使得结果图是一个有着N个节点的树。如果有多个答案，则返回二维数组中最后出现的边。答案边 [u, v] 应满足相同的格式 u < v。
 * <p>
 * 示例 1：
 * <p>
 * 输入: [[1,2], [1,3], [2,3]]
 * 输出: [2,3]
 * 解释: 给定的无向图为:
 * 1
 * / \
 * 2 - 3
 * 示例 2：
 * <p>
 * 输入: [[1,2], [2,3], [3,4], [1,4], [1,5]]
 * 输出: [1,4]
 * 解释: 给定的无向图为:
 * 5 - 1 - 2
 * |   |
 * 4 - 3
 * 注意:
 * <p>
 * 输入的二维数组大小在 3 到 1000。
 * 二维数组中的整数在1到N之间，其中N是输入数组的大小。
 * <p>
 * 题目源自：https://leetcode-cn.com/problems/redundant-connection/
 *
 * 思路 并查集 如果是同一个祖先，则代表是多余的连接，直接返回，
 * 如果不是同一个祖先，则代表无法形成环，需要将其连接起来
 */
public class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < n; i++) {
            int firRoot = find(edges[i][0], parents);
            int secRoot = find(edges[i][1], parents);
            if (firRoot == secRoot) {
                return edges[i];
            } else {
                parents[firRoot] = secRoot;
            }
        }
        return new int[]{};
    }

    // 找祖先
    public int find(int i, int[] parents) {
        if (parents[i] != i) {
            parents[i] = find(parents[i], parents);
        }
        return parents[i];
    }
}
