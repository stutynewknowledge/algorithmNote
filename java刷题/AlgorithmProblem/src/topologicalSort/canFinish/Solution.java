package topologicalSort.canFinish;

import java.util.*;

/**
 * 207. 课程表
 * 你这个学期必须选修 numCourse 门课程，记为 0 到 numCourse-1 。
 *
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们：[0,1]
 *
 * 给定课程总量以及它们的先决条件，请你判断是否可能完成所有课程的学习？
 *
 *
 *
 * 示例 1:
 *
 * 输入: 2, [[1,0]]
 * 输出: true
 * 解释: 总共有 2 门课程。学习课程 1 之前，你需要完成课程 0。所以这是可能的。
 * 示例 2:
 *
 * 输入: 2, [[1,0],[0,1]]
 * 输出: false
 * 解释: 总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。
 *
 *
 * 提示：
 *
 * 输入的先决条件是由 边缘列表 表示的图形，而不是 邻接矩阵 。详情请参见图的表示法。
 * 你可以假定输入的先决条件中没有重复的边。
 * 1 <= numCourses <= 10^5
 *
 * 题目源自：课程表
 *
 * 思路：拓扑排序
 */
public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // key为要学习的课程，value为要学习此课程的先觉条件
        Map<Integer, List<Integer>> edges = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            edges.put(i, new ArrayList<>());
        }
        int n = prerequisites.length;
        // 索引代表课程id
        int[] inedges = new int[numCourses];
        for (int i = 0; i < n; i++) {
            int [] prerequisity = prerequisites[i];
            edges.get(prerequisity[1]).add(prerequisity[0]);
            inedges[prerequisity[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if(inedges[i] == 0){
                queue.offer(i);
            }
        }
        // 学习的课程数量
        int result = 0;
        while(!queue.isEmpty()){
            int curCourse = queue.poll();
            result++;
            List<Integer> curBefore = edges.get(curCourse);
            int curbeforeSize = curBefore.size();
            for (int i = 0; i < curbeforeSize; i++) {
                inedges[curBefore.get(i)]--;
                if (inedges[curBefore.get(i)] == 0){
                    queue.offer(curBefore.get(i));
                }
            }
        }
        return result == numCourses;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().canFinish(2, new int[][]{{0, 1}}));
    }

}
