package ADailyTopic.TwoZeroTwoOne.zeroOne.oneTwo;

import java.util.*;

/**
 * 1203. 项目管理
 * 有 n 个项目，每个项目或者不属于任何小组，或者属于 m 个小组之一。group[i] 表示第 i 个项目所属的小组，如果第 i 个项目不属于任何小组，则 group[i] 等于 -1。项目和小组都是从零开始编号的。可能存在小组不负责任何项目，即没有任何项目属于这个小组。
 *
 * 请你帮忙按要求安排这些项目的进度，并返回排序后的项目列表：
 *
 * 同一小组的项目，排序后在列表中彼此相邻。
 * 项目之间存在一定的依赖关系，我们用一个列表 beforeItems 来表示，其中 beforeItems[i] 表示在进行第 i 个项目前（位于第 i 个项目左侧）应该完成的所有项目。
 * 如果存在多个解决方案，只需要返回其中任意一个即可。如果没有合适的解决方案，就请返回一个 空列表 。
 *
 *
 *
 * 示例 1：
 *
 *
 *
 * 输入：n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
 * 输出：[6,3,4,1,5,2,0,7]
 * 示例 2：
 *
 * 输入：n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3],[],[4],[]]
 * 输出：[]
 * 解释：与示例 1 大致相同，但是在排序后的列表中，4 必须放在 6 的前面。
 *
 *
 * 提示：
 *
 * 1 <= m <= n <= 3 * 104
 * group.length == beforeItems.length == n
 * -1 <= group[i] <= m - 1
 * 0 <= beforeItems[i].length <= n - 1
 * 0 <= beforeItems[i][j] <= n - 1
 * i != beforeItems[i][j]
 * beforeItems[i] 不含重复元素
 *
 * 题目源自：https://leetcode-cn.com/problems/sort-items-by-groups-respecting-dependencies/
 */
public class Solution {
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        // 1、为没有组的项目进行分组，因为组为m个，可以将没有组的项目分给m++
        for (int i = 0; i < n; i++) {
            if(group[i] == -1){
                group[i] = m;
                m++;
            }
        }

        // 2、实例化组和项目的邻接表
        List<Integer>[] groupAdj = new ArrayList[m];
        List<Integer>[] itemAdj = new ArrayList[n];
        for (int i = 0; i < m; i++) {
            groupAdj[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            itemAdj[i] = new ArrayList<>();
        }

        // 3、统计入度数组且建立有向图
        int[] groupInDegree = new int[m];
        int[] itemInDegree = new int[n];
        for (int i = 0; i < n; i++) {
            int curGroup = group[i];
            int beforeLen = beforeItems.get(i).size();
            for (int j = 0; j < beforeLen; j++) {
                int beforeItem = beforeItems.get(i).get(j);
                int beforeGroup = group[beforeItem];
                if(curGroup != beforeGroup){
                    groupAdj[beforeGroup].add(curGroup);
                    groupInDegree[curGroup]++;
                }
                itemAdj[beforeItem].add(i);
                itemInDegree[i]++;
            }
        }

        // 4、获得item与group的拓扑排序后的结果
        List<Integer> groupRes = topologySort(groupAdj, groupInDegree, m);
        List<Integer> itemRes = topologySort(itemAdj, itemInDegree, n);
        if(groupRes.size() == 0){
            return new int[]{};
        }
        if(itemRes.size() == 0){
            return new int[]{};
        }

        // 5 根据项目拓扑排序结果(项目与组的多对一关系)，建立组与项目之间的一对多关系
        // 通过map来存储 key为组，value为项目
        Map<Integer, List<Integer>> groups2Items = new HashMap<>();
        for (int i = 0; i < n; i++) {
            groups2Items.computeIfAbsent(group[itemRes.get(i)], key->new ArrayList<>()).add(itemRes.get(i));
        }

        // 6 根据组的拓扑排序结果以及组与项目之间的一对多关系来获得结果
        List<Integer> res = new ArrayList<>(n);
        for (int i = 0; i < m; i++) {
            int curGroup = groupRes.get(i);
            List<Integer> curGropItems = groups2Items.get(curGroup);
            if(curGropItems != null){
                res.addAll(curGropItems);
            }
        }

        return res.stream().mapToInt(Integer::valueOf).toArray();
    }


    /**
     * 拓扑排序
     */
    private List<Integer> topologySort(List<Integer>[] itemsAdj, int[] inDegree, int n){
        List<Integer> res = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if(inDegree[i] == 0){
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()){
            Integer cur = queue.poll();
            res.add(cur);
            List<Integer> afterList = itemsAdj[cur];
            for (int i = 0; i < afterList.size(); i++) {
                inDegree[afterList.get(i)] = inDegree[afterList.get(i)]-1;
                if(inDegree[afterList.get(i)] == 0){
                    queue.offer(afterList.get(i));
                }
            }
        }

        if(res.size() == n){
            return res;
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {

    }
}
