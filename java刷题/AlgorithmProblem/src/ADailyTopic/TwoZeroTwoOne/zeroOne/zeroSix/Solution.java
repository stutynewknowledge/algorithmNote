package ADailyTopic.TwoZeroTwoOne.zeroOne.zeroSix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 399. 除法求值
 * 给你一个变量对数组 equations 和一个实数值数组 values 作为已知条件，其中 equations[i] = [Ai, Bi] 和 values[i] 共同表示等式 Ai / Bi = values[i] 。每个 Ai 或 Bi 是一个表示单个变量的字符串。
 *
 * 另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，请你根据已知条件找出 Cj / Dj = ? 的结果作为答案。
 *
 * 返回 所有问题的答案 。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。如果问题中出现了给定的已知条件中没有出现的字符串，也需要用 -1.0 替代这个答案。
 *
 * 注意：输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。
 *
 *
 *
 * 示例 1：
 *
 * 输入：equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
 * 输出：[6.00000,0.50000,-1.00000,1.00000,-1.00000]
 * 解释：
 * 条件：a / b = 2.0, b / c = 3.0
 * 问题：a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
 * 结果：[6.0, 0.5, -1.0, 1.0, -1.0 ]
 * 示例 2：
 *
 * 输入：equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
 * 输出：[3.75000,0.40000,5.00000,0.20000]
 * 示例 3：
 *
 * 输入：equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
 * 输出：[0.50000,2.00000,-1.00000,-1.00000]
 *
 * 题目源自：https://leetcode-cn.com/problems/evaluate-division/
 */
public class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int n = equations.size();
        Map<String, Integer> hashMap = new HashMap<>(2 * n);
        UnionFind unionFind = new UnionFind(2 * n);
        int id = 0;
        for (int i = 0; i < n; i++) {
            List<String> equation = equations.get(i);
            String first = equation.get(0);
            String second = equation.get(1);

            if(!hashMap.containsKey(first)){
                hashMap.put(first, id);
                id++;
            }
            if(!hashMap.containsKey(second)){
                hashMap.put(second, id);
                id++;
            }

            // 建图
            unionFind.union(hashMap.get(first), hashMap.get(second), values[i]);
        }

        // 求值
        int queriesLen = queries.size();
        double[] result = new double[n];
        for (int i = 0; i < queriesLen; i++) {
            List<String> query = queries.get(i);
            String firstVariable = query.get(0);
            String secondVariable = query.get(1);
            Integer firstId = hashMap.get(firstVariable);
            Integer secondId = hashMap.get(secondVariable);
            if(firstId == null || secondId == null){
                result[i] = -1.d;
            }else{
                result[i] = unionFind.getWeight(firstId, secondId);
            }

        }
        return result;

    }

    private class UnionFind{

        private int[] parents;

        private double[] weight;

        UnionFind(int n){
            parents = new int[n];
            weight = new double[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
                weight[i] = 1d;
            }
        }

        public int find(int i){
            if(parents[i] != i){
                int pre = parents[i];
                parents[i] = find(parents[i]);
                weight[i] *= weight[pre];
            }
            return parents[i];
        }

        public void union(int x, int y, double value){
            int rootx = find(x);
            int rooty = find(y);
            if(rootx != rooty){
                parents[rootx] = parents[rooty];
                weight[rootx] = weight[y] * value / weight[x];
            }
        }

        public double getWeight(int x, int y){
            int rootx = find(x);
            int rooty = find(y);
            if(rootx == rooty){
                return weight[x] / weight[y];
            }else{
                return -1d;
            }
        }
    }

    public static void main(String[] args) {

    }
}
