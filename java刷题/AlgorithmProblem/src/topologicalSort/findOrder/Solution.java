package topologicalSort.findOrder;

import java.util.*;

public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
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
        List<Integer> result = new ArrayList<>();
        while(!queue.isEmpty()){
            int curCourse = queue.poll();
            result.add(curCourse);
            List<Integer> curBefore = edges.get(curCourse);
            int curbeforeSize = curBefore.size();
            for (int i = 0; i < curbeforeSize; i++) {
                inedges[curBefore.get(i)]--;
                if (inedges[curBefore.get(i)] == 0){
                    queue.offer(curBefore.get(i));
                }
            }
        }
        return result.size() == numCourses ? result.stream().mapToInt(Integer::valueOf).toArray() : new int[]{};
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findOrder(2, new int[][]{{0, 1}}));
    }
}
