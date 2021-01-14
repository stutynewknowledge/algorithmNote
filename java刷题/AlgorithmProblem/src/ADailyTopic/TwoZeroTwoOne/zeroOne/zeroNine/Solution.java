package ADailyTopic.TwoZeroTwoOne.zeroOne.zeroNine;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 1202. 交换字符串中的元素
 * 给你一个字符串 s，以及该字符串中的一些「索引对」数组 pairs，其中 pairs[i] = [a, b] 表示字符串中的两个索引（编号从 0 开始）。
 *
 * 你可以 任意多次交换 在 pairs 中任意一对索引处的字符。
 *
 * 返回在经过若干次交换后，s 可以变成的按字典序最小的字符串。
 *
 *
 *
 * 示例 1:
 *
 * 输入：s = "dcab", pairs = [[0,3],[1,2]]
 * 输出："bacd"
 * 解释：
 * 交换 s[0] 和 s[3], s = "bcad"
 * 交换 s[1] 和 s[2], s = "bacd"
 * 示例 2：
 *
 * 输入：s = "dcab", pairs = [[0,3],[1,2],[0,2]]
 * 输出："abcd"
 * 解释：
 * 交换 s[0] 和 s[3], s = "bcad"
 * 交换 s[0] 和 s[2], s = "acbd"
 * 交换 s[1] 和 s[2], s = "abcd"
 * 示例 3：
 *
 * 输入：s = "cba", pairs = [[0,1],[1,2]]
 * 输出："abc"
 * 解释：
 * 交换 s[0] 和 s[1], s = "bca"
 * 交换 s[1] 和 s[2], s = "bac"
 * 交换 s[0] 和 s[1], s = "abc"
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 10^5
 * 0 <= pairs.length <= 10^5
 * 0 <= pairs[i][0], pairs[i][1] < s.length
 * s 中只含有小写英文字母
 *
 * 题目源自：https://leetcode-cn.com/problems/smallest-string-with-swaps/
 */

public class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int pairsLen = pairs.size();
        if (pairsLen == 0){
            return s;
        }
        int n = s.length();
        int[] parents = new int[n];
        // 初始化根节点
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        
        // 找同类
        for (int i = 0; i < pairsLen; i++) {
            int pre = find(pairs.get(i).get(0), parents);
            int aft = find(pairs.get(i).get(1), parents);
            parents[pre] = aft;
        }

//         根据根节点，建立小根堆
        Map<Integer, PriorityQueue<Character>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(i, parents);
            if(map.containsKey(root)){
                map.get(root).offer(s.charAt(i));
            }else{
                PriorityQueue<Character> minHeap = new PriorityQueue<>();
                minHeap.offer(s.charAt(i));
                map.put(root, minHeap);
            }
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < n; i++) {
            int root = find(i, parents);
            result.append(map.get(root).poll());
        }
        return result.toString();
    }

    public int find(int i, int[] parents){
        if(parents[i] != i){
            parents[i] = find(parents[i], parents);
        }
        return parents[i];
    }

    public static void main(String[] args) {
        List<List<Integer>> pairs = new ArrayList<>();
        List<Integer> pair = new ArrayList<>();
        List<Integer> pair1 = new ArrayList<>();
        pair.add(1);
        pair.add(2);
        pairs.add(pair);
        pair1.add(0);
        pair1.add(1);
        pairs.add(pair1);
        System.out.println(new Solution().smallestStringWithSwaps("dcab", pairs));
    }
}
