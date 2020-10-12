# 动态规划

## 一 动态规划简介

> 动态规划不是某一种具体的算法，而是一种思想

动态规划解决的都是一些问题的最优解，即是从某个问题的很多种解决办法中找到最优的一个。

### 1 动态规划相关背景

####  最优子结构

> 最优子结构规定的是子问题于原问题之间的关系

一个问题的最优解是由它的各个子问题的最优解决定的

找到最优子结构，也就能推导出一个状态转移方程*f*(*n*)，通过这个状态转移方程，我们能很快写出问题的的鬼实现方法。



![](%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92I.assets/1-1.png)

####  重复子问题

> 重复子问题是子问题与子问题之间的关系

当递归寻找每个字问题的最优解的时候，会重复的遇到一些更小的的子问题，而这些子问题会重叠的出现在子问题里，出现这些问题的时候，会有很多重复的计算，动态规划可以保证，每个重复的子问题只会被求解一次。动态规划可以减少很多重复的计算。

> 重复子问题不是保证解的正确性必须的，但是如果递归求解子问题时，没有出现重复子问题，则没有必要用动态规划，直接普通的递归就可以了

####  动态规划核心

> 解决动态规划问题的核心：找到子问题以及子问题与原问题之间的关系

### 2 解决动态规划问题的思考过程

以例题为例

```text
题目：300.最长上升子序列
描述：
给定一个无序的整数数组，找到其中最长上升子序列的长度。
示例：
输入: [10,9,2,5,3,7,101,18]
输出: 4
解释: 最长的上升子序列是 [2,3,7,101]，它的长度是4。
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/xcos8s/
```

```markdown
思路：
1、定义状态:由于一个子序列一定会以一个数结尾，于是可以将dp[i]定义成以nums[i]结尾的最长子序列的长度，注意num[i]必须被选取，且必须是这个子序列的最后一个元素
2、确定状态转移方程：
    (1) 遍历到nums[i]时，需要把下标i之前的所有的数都看一遍；
    (2) 只要num[i]大于任意位置的数,那么nums[i]就可以直接在这个数后面形成一个更长的上升子序列；
    (3) 因此dp[i]就等于下标i之前严格小于nums[i]的状态值的最大值+1
    综上可以写出状态转移方程: dp[i] = max{1+dp[j] for j < i if nums[i] > nums[j]}
3、考虑初始化的值：dp[i] = 1，1个字符显然是长度为1的上升子序列
4、考虑输出：不能返回最后一个值，最后一个值的定义只是nums[n-1](n为数组的长度)为结尾的[上升子序列的值],并不是该数组的最长上升子序列，此时的最长上升子序列是以nums[i] (此时i<n)为结尾的最大长度。
```

![3-1](%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92I.assets/3-1.png)

```python
class Solution:
    def lengthOfLIS(self, nums: List[int]) -> int:
        '''
        动态规划
        '''
        n = len(nums)
        if n == 0:
            return 0
        dp = [1 for _ in range(n)]
        result = 0
        for i in range(n):
            for j in range(i):
                if nums[i] > nums[j]:
                    dp[i] = max(dp[i], dp[j] + 1)
            result = max(dp[i], result)
        return result
```

### 3 动态规划与其他算法的关系

#### 分治

> 解决分治的思路：想办法把问题的规模缩小，有时候减少一个，有时候减少一半，然后将每个小问题的解以及当前情况组合起来得到结果

其与动态规划的区别是：分治不存在重复的子问题。

#### 贪心

1. 关于最优子结构
   - 贪心： 每一步的最优解一定包含上一步的最优解，上一步的最优解不需要进行记录
   - 动态规划：全局的最优解中一定包含某个局部的最优解，但不一定包含上一步的局部最优解，因此需要记录所有的局部最优解
2. 关于子问题的最优解组合成原问题最优解的组合方式
   - 贪心：如果把最优解的问题看成一棵树的话，贪心从根出发，每次向下遍历最优子树即可，这里的最优子树是贪心意义上的最优，此时不需要知道一个节点的所有子树的情况，于是构不成一棵完整的树
   - 动态规划：动态规划是需要对每一个子树进行最优解，直至下面的每一个叶子的值，最后得到一棵完整的树，在所有子树都得到最优解后，将它们组合成答案
3. 结果正确性
   - 贪心：贪心不能保证最后的结果一定是最优，但复杂度最低。
   - 动态规划：保证最后的结果一定是最优的，但复杂度较高。

|            | 分治       | 动态规划   | 贪心     |
| ---------- | ---------- | ---------- | -------- |
| 适用类型   | 通用       | 优化       | 优化     |
| 子问题     | 每个都不同 | 有很多重复 | 只有一个 |
| 最优子结构 | 没有要求   | 必须满足   | 必须满足 |
| 子问题数   | 全部都要解 | 全部都要解 | 只解一个 |

## 二 线性动态规划

### 1 线性动态规划简介

> 主要特点：按照规模 i 从小到达一次推过去的，较大规模的解依赖较小规模的问题的解。

状态定义：

```markdown
dp[n] := dp[0...n]
```

状态转移：

```markdown
dp[n] = f(dp[n-1]...dp[0])
```

> 过程：大规模的状态只与较小规模的问题有关，问题的规模可以用 i 来表示，因此从小i到大推i直至推到n，就得到了大规模问题n的解。

按照问题的输入格式，线性动态规划解决的问题主要是单串、双串、矩阵上的问题，因为在单串，双串、矩阵上问题规模完全用位置决定问题规模的大小。

### 2 单串

单串 dp[i] 线性动态规划中最简单的一类问题，输入是一个串，状态一般定义为  `dp[i] := 考虑[0..i]上，原问题的解`

其中 i 位置的处理，根据不同的问题，主要是两种形式：

1. 第一种是i必须取，此时状态可以进一步描述为 `dp[i] := 考虑[0..i]上，且取 i，原问题的解`
2. 第二种是`i`可以取可以不取

线性动态规划中单串`dp[i]`的问题，状态的推导方向以及推到公式如下

![2-2-1](%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92I.assets/2-2-1.png)

#### **<font face="微软雅黑" size=4>依赖比`i`小的O(1)个子问题</font>**

`dp[n]`只与常数个小规模子问题有关，状态的推导过程`dp[i]=f(dp[i-1], dp[i-2], ...)`。其时间复杂度为O(n)，空间复杂度O(n)可以优化为O(1)。

如上图所示，虽然绿色部分的`dp[i-1],dp[i-2], ..., dp[0]`均以求过，但计算橙色的当前状态时，其仅与`dp[i-1]`有关，这属于比`i`小的O(1)个子问题

例子：

```markdown
给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
示例:
输入: [-2,1,-3,4,-1,2,1,-5,4]
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/maximum-subarray

思路：
1、定义状态：假设nums的长度为n，要想找到第n个数结尾的连续子数组的最大和，我们只需要找到第n-1个数结尾的连续子数组的最大和，在利用此和去加上`nums[n]`与`nums[n]`进行比较，因此我们可以将dp[i]设定为第i个数结尾的连续子数组的最大和。
2、由状态可以得到状态转移方程为:`dp[n] = max(nums[n], dp[n-1] + nums[n])`
3、考虑初始的值：当有数的时候，dp[n] := nums[0], 其nums长度为1时，其最大连续子序列为nums[0]
4、考虑边界： 当没有nums为空的时候，其返回None,
5、考虑输出，dp[n]表示的是以第n个数结尾的连续子数组的和，并不代表此数组的最大和，此数组的连续子数组的最大和为`max(dp)`
```

```python
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return None
        dp = [nums[0] for _ in range(n)]
        for i in range(1, n):
            dp[i] = max(dp[i-1] + nums[i], nums[i])
        return max(dp)

# 由分析可以得出dp[i]只与dp[i-1]和nums[i]有关，符合依赖比`i`小的O(1)个子问题
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return None
        pre = nums[0]	# 代表以第i个数结尾的连续子数组的最大和
        result = nums[0]	# 代表数组nums的连续子数组的最大和
        for i in range(1, n):
            pre = max(pre + nums[i], nums[i])
            result = max(result, pre)
        return result
```

#### **<font face="微软雅黑" size=4>依赖比`i`小的O(n)个子问题</font>**

`dp[n]`与之前的更小规模的所有子问题`dp[n - 1], dp[n - 2], ..., dp[o]`都<font color=#FF000>可能</font>有关系

状态推倒过程

```code
dp[i] = f(dp[i-1], dp[i-2], ..., dp[0])
```

其中f常见的有max/min函数，可能还会对`i-1, i-2, ..., 0`有一些筛选条件，但推倒`dp[n]`时依然是O(n)级别的子问题数量。

以最长上升子序列为例

```code
for i = 1, ..., n
    for j = 1, ..., i-1
        dp[i] = min(dp[i], f(dp[j])
```

此时时间复杂度为*O*(n**2)，空间复杂度O(n)

例子 ：

```markdown
见(一)中题： 最长上升子序列
```

#### **单串练习**

##### 单串：最长上升子序列系列

**<font size=4>1. 最长上升子序列的长度</font>**

```markdown
给定一个无序的整数数组，找到其中最长上升子序列的长度。

示例:

输入: [10,9,2,5,3,7,101,18]
输出: 4 
解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
说明:
可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
你算法的时间复杂度应该为 O(n2) 。
进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5oqnpg/

**思路1**
1、定义状态:由于一个子序列一定会以一个数结尾，于是可以将dp[i]定义成以nums[i]结尾的最长子序列的长度，注意num[i]必须被选取，且必须是这个子序列的最后一个元素
2、确定状态转移方程：
    (1) 遍历到nums[i]时，需要把下标i之前的所有的数都看一遍；
    (2) 只要num[i]大于任意位置的数,那么nums[i]就可以直接在这个数后面形成一个更长的上升子序列；
    (3) 因此dp[i]就等于下标i之前严格小于nums[i]的状态值的最大值+1
    综上可以写出状态转移方程: dp[i] = max{1+dp[j] for j < i if nums[i] > nums[j]}
3、考虑初始化的值：dp[i] = 1，1个字符显然是长度为1的上升子序列
4、考虑输出：不能返回最后一个值，最后一个值的定义只是nums[n-1](n为数组的长度)为结尾的[上升子序列的值],并不是该数组的最长上升子序列，此时的最长上升子序列是以nums[i] (此时i<n)为结尾的最大长度。

**思路2(优化方法)**
	(1)依然将目光放在最后一个数;
	(2)如果已经得到的上升子序列的结尾的数越小，遍历的时候后面接上一个数，就会有更大的可能性获得一个更高的上升子序列。
	(3)既然结尾的值越小越好，我们可以记录在长度固定的情况下，结尾最小的那个元素的。
1、定义状态:
 	将tail[i]定义成长度为`i+1`的所有上升子序列中的结尾的最小值。
 	注意：
 		(1)`tail[0]`表示长度为1的所有上升子序列中结尾的最小值，
 		(2)下标和长度相差1
2、确定状态转移方程:
	`tail`数组是一个上升数组。
	证明:对于任意的下标0 <= i < j < len，都有tail[i] < tail[j]
	 反证法：假设对于任意的 i < j，存在某个a[i] >= a[j]
	 对于tail[i]来说，对应一个上升序列[a[0],a[1],a[2], ..., a[i]]，依照定义tail[i] = a[i];
	 对于tail[j]来说，对应一个上升序列[b[0],b[1],b[2], ..., b[i], ..., b[j]]，依照定义tail[j] = b[j];
	 由于tail[i] >= tail[j], 等价于a[i] >= b[j]。而在上升子序列[b[0],b[1],b[2], ..., b[i], ..., b[j]]，b[i]严格小于b[j]，故又a[i] > b[j] > b[i];则上升子序列 [b_0, b_1, ..., b_i] 是一个长度也为i+1但是结尾更小的数组，但是与a[i]的最小性矛盾。
	 因此原命题成立。
	 我们只需要维护数组tail的定义，它的长度就是最长上升子序列的长度。
	在此时我们就只需要求tail数组即可。
	(1)设置一个数组 tail，初始时为空；
	(2)在遍历数组nums过程中。每来一个新数`num`，如果这个数**大于**有序数组tail的最后一个数，就把num放在有序数组的后面，否则进入第3步
	(3)在有序数组中查找第1个大于或者等于num的数，试图将它变小，此时分两种情况
		1) 当tail中存在等于num的数，什么都不需要做
		2) 当tail中存在大于num的数，那么就将第1个大于num的数将其变小，即将num替换它。
	由于tail数组是一个有序数组，便可以用二分查找的方法。
3、思考初始化
	dp[0] = num[0],在只有1个元素的情况下，它就是长度为1并且结尾最小的元素。
4、考虑输出
	数组 tail 的长度，上文其实也已经说了，还是依据定义，tail[i] 表示长度固定为 i + 1 的所有「上升子序列」的结尾元素中最小的那个，长度最多就是数组 tail 的长度。
思路来源于力扣中的作者：liweiwei1419
链接：https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/dong-tai-gui-hua-er-fen-cha-zhao-tan-xin-suan-fa-p/
```

```python
class Solution:
    def lengthOfLIS(self, nums: List[int]) -> int:
        '''
        动态规划+贪心+二分
        '''
        n = len(nums)
        if n <= 1:
            return n
        tail = [nums[0], ]
        for i in range(1, n):
            if nums[i] > tail[-1]:
                tail.append(nums[i])
                continue
            left = 0
            right = len(tail) - 1
            while left < right:
                mid = (left + right) >> 1   # 此时使用左中位数
                if nums[i] > tail[mid]:
                    left = mid + 1
                else:
                    right = mid
            tail[left] = nums[i]
        return len(tail)
```



**<font size=4>2. 最长递增子序列的个数</font>**

```markdown
给定一个未排序的整数数组，找到最长递增子序列的个数。

示例 1:
输入: [1,3,5,4,7]
输出: 2
解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。

示例 2:
输入: [2,2,2,2,2]
输出: 5

解释: 最长递增子序列的长度是1，并且存在5个子序列的长度为1，因此输出5。
注意: 给定的数组长度不超过 2000 并且结果一定是32位有符号整数。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5o6mrv/
来源：力扣（LeetCode）

**思路：**
1. 定义状态：一个子序列必定会以一个数结尾，那么我们可以定义`lengths[i]`和`counts[i]`为分别以nums[i]结尾的最长递增子序列的长度和以nums[i]结尾的最长递增子序列的个数。
2. 确定状态转移：
	   (1)遍历到i时，需要将i之前的所有数都看一遍
	   (2)当`nums[i] > nums[j]`时，我们将`nums[i]`添加到以`nums[j]`结尾的最长递增子序列的后面，此时当
	 `lengths[j]`比`length[i]`的长度相等或比其大的时候，此时的`lengths[i] = lengths[j] + 1`且
	 `counts[i] = counts[j]`,或者当`lengths[j] + 1`与`lengths[i]`相等的时候，
	 `counts[i]+=counts[j]`
	 综上可写出状态转移方程
	 lengths[n] = lengths[j] + 1 for j in range(n) if nums[n] > nums[j]
	 for j in range(n):
	      if nums[n] > nums[j] and lengths[j] >= lengths[n]:counts[n] = counts[j]
	      if nums[n] > nums[j] and lengths[n] = lengths[j] + 1:counts[n] += counts[j]  
	 
 3. 初始化：lengths[n] := 1 当数组中只含有一个数字的的时候，最长递增子序列长度1；
 					 counts[n] := 1 当数组中只含有一个数字的的时候，数量默认为1；
 4. 考虑边界：当数组为空的时候，只有0个，长度也为0，
 5. 考虑输出：
 						最长递增子序列的长度为max(lengths)
 						最长递增子序列的个数为sum([counts[i] for i in range(n) if length[i] == max(lengths)])
```

```python
class Solution:
    def findNumberOfLIS(self, nums: List[int]) -> int:
        n = len(nums)
        if n <= 1: return n
        lengths = [1 for _ in range(n)]
        counts = [1 for _ in range(n)]
        for i in range(n):
            for j in range(i):
                if nums[i] > nums[j]:
                    if lengths[j] >= lengths[i]:
                        lengths[i] = lengths[j] + 1
                        counts[i] = counts[j]
                    elif lengths[j] + 1 == lengths[i]:
                        counts[i] += counts[j]
        max_length = max(lengths)
        return sum([counts[i] for i in range(n) if lengths[i] == max_length])

```

**<font size=4>3. 俄罗斯套娃信封问题</font>**

```markdown
给定一些标记了宽度和高度的信封，宽度和高度以整数对形式 (w, h) 出现。当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。

说明:
不允许旋转信封。
示例:

输入: envelopes = [[5,4],[6,4],[6,7],[2,3]]
输出: 3 
解释: 最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5okoej/
来源：力扣（LeetCode）

**思路**
由问题可以分析得出，我们阔以对数组进行排序，一个信封要想套进另外一个信封时，其首先w和h都要比另外一个信封小，由此，我们可以将`envelopes=[[5,4],[6,4],[6,7],[2,3]]`进行排序，若将w由小到大排列，此时数组`envelopes=[[2,3], [5,4], [6,4], [6,7]]`,此时我们就只需要判断h的大小即可，我们将h提出来`[3,4,4,7]`，此时这个由h组成的数组的最长上升子序列的长度=最多能有多少个信封能组成一组“俄罗斯套娃”信封。
但是此时会发现一个问题，当w相等的时候，并不能组成信封，所以此时我们需要将w相等的数组进行排除，而排除的方法但又不能去掉，我们可以通过在排序的时候进行，即当w相同时，可以对h进行降序排列，当对h数组求最长上升子序列时，其w相等的不会排列出来，因为其始终是降序排列的。
此时的问题便可以演变成求数组中的h的最长上升子序列的长度。
```

```python
class Solution:

    def lengthOfLIS(self, nums: List[int]) -> int:
        '''
        动态规划+贪心+二分
        '''
        n = len(nums)
        if n <= 1:
            return n
        tail = [nums[0], ]
        for i in range(1, n):
            if nums[i] > tail[-1]:
                tail.append(nums[i])
                continue
            left = 0
            right = len(tail) - 1
            while left < right:
                mid = (left + right) >> 1   # 此时使用左中位数
                if nums[i] > tail[mid]:
                    left = mid + 1
                else:
                    right = mid
            tail[left] = nums[i]
        return len(tail)

    def maxEnvelopes(self, envelopes: List[List[int]]) -> int:
        '''
        排序+LIS
        '''
        n = len(envelopes)
        if n <=1:
            return n
        envelopes.sort(key=lambda x: (x[0], -x[1]))
        hList = [envelope[1] for envelope in envelopes]
        return self.lengthOfLIS(hList)
```

##### 单串:最大子数组和系列

**<font size=4>1. 最大子序和</font>**

```markdown
给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
示例:
输入: [-2,1,-3,4,-1,2,1,-5,4]
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
进阶:
如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rp7wr/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
1、定义状态：
	将dp[i]定义成长度为i+1数组的连续子数组的最大和。
2、确定状态转移
	要想求得dp[i]可以通过dp[i-1]+nums[i]获得,当dp[i-1] + nums[i] > dp[i-1]时，此时长度为i的数组的最大子数组和就为dp[i-1] + nums[i],当比起小或等于的时候，其等于nums[i]。
	由此可以写出其状态转移方程
	dp[n] = max(dp[n-1] + nums[i], nums[i])
3、初始化：
	dp[n] := nums[0]， 当nums只有一个的时候，其最大子数组和就是nums[0]
4、边界：
	当nums为空时，其也为空
5、输出：
	其最大和的连续子数组为dp[n] n为nums数组最后一个数的下标。
```

```python
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return None
        dp = [nums[0] for _ in range(n)]
        for i in range(1, n):
            dp[i] = max(dp[i-1] + nums[i], nums[i])
        return max(dp)
@ 优化空间：其只求最大值，我们可以通过使用一个变量来保存最大值，用另一个变量来保存当前值的最大值。
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return None
        pre = nums[0]
        result = nums[0]
        for i in range(1, n):
            pre = max(pre + nums[i], nums[i])
            result = max(result, pre)
        return result
```

**<font size=4>2. 乘积最大子组数</font>**

```markdown
给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
示例 1:

输入: [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。
示例 2:

输入: [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rmwns/
来源：力扣（LeetCode）

**思路**
与最大子序和类似，需要考虑符号
```

```python
class Solution:
    def maxProduct(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return n
        dp = [[ nums[0], nums[0]] for _ in range(n)]
        for i in range(1, n):
            dp[i] = [min([dp[i-1][0]*nums[i], nums[i], dp[i-1][1]*nums[i], ]), max([dp[i-1][0]*nums[i], nums[i], dp[i-1][1]*nums[i]])]
        return max(dp[i][1] for i in range(n))
```

**<font size=4>3. 环形子组数的最大和</font>**

```markdown
给定一个由整数数组 A 表示的环形数组 C，求 C 的非空子数组的最大可能和。
在此处，环形数组意味着数组的末端将会与开头相连呈环状。（形式上，当0 <= i < A.length 时 C[i] = A[i]，而当 i >= 0 时 C[i+A.length] = C[i]）
此外，子数组最多只能包含固定缓冲区 A 中的每个元素一次。（形式上，对于子数组 C[i], C[i+1], ..., C[j]，不存在 i <= k1, k2 <= j 其中 k1 % A.length = k2 % A.length）

示例 1：

输入：[1,-2,3,-2]
输出：3
解释：从子数组 [3] 得到最大和 3

示例 2：
输入：[5,-3,5]
输出：10
解释：从子数组 [5,5] 得到最大和 5 + 5 = 10

示例 3：
输入：[3,-1,2,-1]
输出：4
解释：从子数组 [2,-1,3] 得到最大和 2 + (-1) + 3 = 4

示例 4：
输入：[3,-2,2,-3]
输出：3
解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3

示例 5：
输入：[-2,-3,-1]
输出：-1
解释：从子数组 [-1] 得到最大和 -1

提示：
-30000 <= A[i] <= 30000
1 <= A.length <= 30000

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5r2pah/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
分治+动态规划
	可以将问题进行分类讨论:
	1、将其作为不是循环矩阵时，求其最大子数组的和(此与最大子序和问题解法相同)
	2、将其作为循环矩阵时，此时考虑跨越两个相同数组交接部分的区域的最大子数组和
当求到这两个最大子数组和后，将其比较两者的最大值(此时需要注意:当数组全为负数时，其最大值就是第一种情况的最大值)
	第二种情况思路:
		要求跨越两个相同数组交接部分的区域的最大子数组和，我们可以反向考虑，我们通过求得数组的总和-数组的最小子数组和，即可得到"跨越两个相同数组交接部分的区域的最大子数组和"此时的解法就与"最大子数组的和"解法类似。
```

```python
class Solution:
    def maxSubarraySumCircular(self, A: List[int]) -> int:
        n = len(A)
        if n == 0:
            return None
        if n == 1:
            return A[0]
        # case 1: 不考虑环形的情况下
        pre = A[0]
        noCircularMaxSum = A[0]
        for i in range(1, n):
            pre = max(pre + A[i], A[i])
            noCircularMaxSum = max(noCircularMaxSum, pre)

        # case2: 考虑环形情况下， 即在求环形情况下的最大子数组和可以等价于求非环形数组A中的最小子数组和，在用非环形情况下A总和-最小和
        preMin = A[0]
        CircularMinSum = A[0]
        total = A[0]
        for i in range(1, n):
            preMin = min(preMin + A[i], A[i])
            CircularMinSum = min(CircularMinSum, preMin)
            total += A[i]
        CircularMaxSum = total - CircularMinSum
        return max(noCircularMaxSum, CircularMaxSum) if noCircularMaxSum > 0 else noCircularMaxSum
    
        # 可将两种情况放在一个循环中
        # pre = A[0]
        # noCircularMaxSum = A[0]
        # preMin = A[0]
        # CircularMinSum = A[0]
        # total = A[0]
        # for i in range(1, n):
        #     pre = max(pre + A[i], A[i])
        #     noCircularMaxSum = max(noCircularMaxSum, pre)
        #     preMin = min(preMin + A[i], A[i])
        #     CircularMinSum = min(CircularMinSum, preMin)
        #     total += A[i]
        # CircularMaxSum = total - CircularMinSum
        # return max(noCircularMaxSum, CircularMaxSum) if noCircularMaxSum > 0 else noCircularMaxSum
```

**<font size=4>3. 最大子矩阵</font>**

```markdown
给定一个正整数和负整数组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。

返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，r2, c2 分别代表右下角的行号和列号。若有多个满足条件的子矩阵，返回任意一个均可。
注意：本题相对书上原题稍作改动

示例:
输入:
[
   [-1,0],
   [0,-1]
]
输出: [0,1,0,1]
解释: 输入中标粗的元素即为输出所表示的矩阵
说明：1 <= matrix.length, matrix[0].length <= 200

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rnep3/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
	其本质也是求最大子数组和，但是其只是将一维数组变成了二维数组，我们只需要将二维数组转变成一维数组。
	而如何将一维数组变成二维数组。
	二维数组其本质可以将其看成一个矩形，而要求二维数组的二维子数组的最大和，只需要将每列的元素相加得到一个新的一维数组，然后对新的数组求最大子数组和即可。
```

![4-1](%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92I.assets/4-1.jpeg)

![4-2](%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92I.assets/4-2.jpeg)

```python
class Solution:
    def getMaxMatrix(self, matrix: List[List[int]]) -> List[int]:
        '''将二维数组化为一维数组'''
        N = len(matrix)
        if N == 0:
            return None
        M = len(matrix[0])
        if M == 0:
            return None
        curSum = 0
        maxSum = -float("inf")
        beginN, beginW = 0, 0
        result = [0, 0, 0, 0]
        for i in range(N):
            curLine = [0 for _ in range(M)]
            for j in range(i, N):
                curSum = 0
                for k in range(M):
                    curLine[k] += matrix[j][k]
                    if curSum + curLine[k] > curLine[k]:
                        curSum += curLine[k]
                    else:
                        curSum = curLine[k]
                        beginN, beginW = i, k
                    if maxSum < curSum:
                        result[0] = beginN
                        result[1] = beginW
                        result[2] = j
                        result[3] = k
                        maxSum = curSum
        return result
```

**<font size=4>4. 矩形区域不超过 K 的最大数值和</font>**

```markdown
给定一个非空二维矩阵 matrix 和一个整数 k，找到这个矩阵内部不大于 k 的最大矩形和。

示例:

输入: matrix = [[1,0,1],[0,-2,3]], k = 2
输出: 2 
解释: 矩形区域 [[0, 1], [-2, 3]] 的数值和是 2，且 2 是不超过 k 的最大数字（k = 2）。
说明：

矩阵内的矩形区域面积必须大于 0。
如果行数远大于列数，你将如何解答呢？

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rvxrm/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
	前缀和+二分
	求sum[i,j]可以等价于求sum[0, j] - sum[0, i-1]
	sum[i, j] <= k 可以转换为 sum[0, j] - k <= sum[0, i-1]
```

```python
class Solution:
    def maxSumSubmatrix(self, matrix: List[List[int]], k: int) -> int:
        n = len(matrix)
        if n == 0:
            return None
        m = len(matrix[0])
        if m == 0:
            return None
        result = float("-inf")
        for i in range(m):
            curLines = [0 for _ in range(n)]
            for j in range(i, m):
                for v in range(n):
                    curLines[v] += matrix[v][j]
                array = [0]  # 排序数组
                curSum = 0
                for curLine in curLines:
                    curSum += curLine
                    loc = bisect.bisect_left(array, curSum - k)
                    if loc < len(array):
                        result = max(result, curSum-array[loc])
                    bisect.insort(array, curSum)
        return result
```

##### 单串：打家劫舍系列

**<font size=4>1. 打家劫舍</font>**

```markdown
你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。

给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。

 

示例 1：

输入：[1,2,3,1]
输出：4
解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
偷窃到的最高金额 = 1 + 3 = 4 。
示例 2：
输入：[2,7,9,3,1]
输出：12
解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
偷窃到的最高金额 = 2 + 9 + 1 = 12 。
作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rwy97/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
动态规划
定义状态
	dp[i]为nums长度为i+1时，能偷到的最高金额
确定状态转移
	dp[i] = max(dp[i-1], dp[i-2]+nums[i])
边界值
	dp[0] = nums[0]
	dp[1] = max(dp[0], dp[1])
输出
	由dp的定义可以得出输出应为dp[n-1]
```

```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return 0
        if n == 1:
            return nums[0]
        # dp = [0 for _ in range(n)]
        # dp[0] = nums[0]
        # dp[1] = max(nums[0], nums[1])
        # for i in range(2, n):
        #     dp[i] = max(dp[i-1], dp[i-2] + nums[i])
        # return dp[n -1]
      
      	'''空间优化为O(1)'''
        first, second = nums[0], max(nums[0], nums[1])
        for i in range(2, n):
            tmp = second
            second = max(first+nums[i], second)
            first = tmp
        return second
```

​	**<font size=4>2. 打家劫舍II</font>**

```python
你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都围成一圈，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。

给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。

示例 1:

输入: [2,3,2]
输出: 3
解释: 你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
示例 2:

输入: [1,2,3,1]
输出: 4
解释: 你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     偷窃到的最高金额 = 1 + 3 = 4 。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rds55/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
分治+动态规划
分三种情况：
	1、将第一家算进去，那么就不算最后一家
  2、不算第一家，算最后一家
  3、第一和最后都不算
可以将两种简化成一种
	因为当两家都不算的情况肯定会比1，2种情况要小。
```

```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        '''
        分两种情况:
            1、偷第一家，不偷最后一家
            2、不偷第一家，偷最后一家
        '''
        def noCircularRob(array):
            n = len(array)
            if n == 0:
                return 0
            if n == 1:
                return array[0]
            first, second = array[0], max(array[0], array[1])
            for i in range(2, n):
                first, second =second, max(first+array[i], second)
            return second
        n = len(nums)
        if n == 0:
            return 0
        return max(noCircularRob(nums[1: n]), noCircularRob(nums[0: n - 1])) if n > 1  else nums[0]
```

​	**<font size=4>3. 删除与获得点数</font>**

```markdown
给定一个整数数组 nums ，你可以对它进行一些操作。

每次操作中，选择任意一个 nums[i] ，删除它并获得 nums[i] 的点数。之后，你必须删除每个等于 nums[i] - 1 或 nums[i] + 1 的元素。

开始你拥有 0 个点数。返回你能通过这些操作获得的最大点数。

示例 1:

输入: nums = [3, 4, 2]
输出: 6
解释: 
删除 4 来获得 4 个点数，因此 3 也被删除。
之后，删除 2 来获得 2 个点数。总共获得 6 个点数。
示例 2:

输入: nums = [2, 2, 3, 3, 3, 4]
输出: 9
解释: 
删除 3 来获得 3 个点数，接着要删除两个 2 和 4 。
之后，再次删除 3 获得 3 个点数，再次删除 3 获得 3 个点数。
总共获得 9 个点数。
注意:

nums的长度最大为20000。
每个整数nums[i]的大小都在[1, 10000]范围内。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5r0kh6/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
将问题转化成打家劫舍系列。
	由题可以分析出，需要将nums[i]+1和nums[i]-1的数删除，即我们可以构建一个新的数组array，以nums[i]作为下标，以nums[i]在`nums`数组中出现的个数作为值，那么其就与打家劫舍系列类似，不能偷array[j]相邻两遍的数。
	此时的转移方程就可以写为dp[n] = max(dp[n - 1], dp[n - 2] + array[j] * j)
```

```python
class Solution:
    def deleteAndEarn(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return 0
        if n == 1:
            return nums[0]
        maxNum = max(nums)
        ary = [0 for _ in range(maxNum + 1)]
        for num in nums:
            ary[num] += 1
        first, second = ary[1] * 1, max(ary[1] * 1, ary[2] * 2)
        for i in range(3, maxNum + 1):
            first, second = second, max(first + ary[i] * i, second)
        return second
```

​	**<font size=4>4.3n块披萨</font>**

```markdoown
给你一个披萨，它由 3n 块不同大小的部分组成，现在你和你的朋友们需要按照如下规则来分披萨：

你挑选 任意 一块披萨。
Alice 将会挑选你所选择的披萨逆时针方向的下一块披萨。
Bob 将会挑选你所选择的披萨顺时针方向的下一块披萨。
重复上述过程直到没有披萨剩下。
每一块披萨的大小按顺时针方向由循环数组 slices 表示。

请你返回你可以获得的披萨大小总和的最大值。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rlibc/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

![2-3-3-4](%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92I.assets/2-3-3-4.png)

```markdown
输入：slices = [1,2,3,4,5,6]
输出：10
解释：选择大小为 4 的披萨，Alice 和 Bob 分别挑选大小为 3 和 5 的披萨。然后你选择大小为 6 的披萨，Alice 和 Bob 分别挑选大小为 2 和 1 的披萨。你获得的披萨总大小为 4 + 6 = 10 。
```

![2-3-3-4.2](%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92I.assets/2-3-3-4.2.png)

```markdown
输入：slices = [8,9,8,6,1,1]
输出：16
解释：两轮都选大小为 8 的披萨。如果你选择大小为 9 的披萨，你的朋友们就会选择大小为 8 的披萨，这种情况下你的总和不是最大的。

示例 3：
输入：slices = [4,1,2,5,8,3,1,9,7]
输出：21

示例 4：
输入：slices = [3,1,2]
输出：3

提示：
1 <= slices.length <= 500
slices.length % 3 == 0
1 <= slices[i] <= 1000

**思路**
动态规划
	可以将问题转换为：在一个3n的环形正整数列表中，取任意n个数，且每次所选取的数的相邻两位不能选取，求n个数的和的最大值。
定义状态：
	这样就与打家劫舍II相似，考虑非环形列表的情况。
	非环形列表情况下：dp[i][j]定义为在一个i位普通列表中，取得j个数的最大和。
状态转移
	dp[i][j] = max(dp[i-2][j-1] + slices[i], dp[i-1][j])
初始化
	dp[i][j] = [[0 for _ in range(n % 3)] for _ in range(n)]
边界值
	当i - 2<0时，此时的dp[i-2][j]为0。
输出
	dp[n][n % 3]
```

```python
class Solution:
    def maxSizeSlices(self, slices: List[int]) -> int:
        def maxSizeSlicesHelper(newSlice, choices):
            n = len(newSlice)
            dp = [[0 for _ in range(choices+1)] for _ in range(n+1)]
            for i in range(1, n+1):
                for j in range(1, choices+1):
                    dp[i][j] = max((dp[i-2][j-1] if i - 2 >= 0 else 0) + newSlice[i-1], dp[i-1][j])
            return dp[n][choices]
        
        return max(maxSizeSlicesHelper(slices[: -1], len(slices)//3), maxSizeSlicesHelper(slices[1: ], len(slices)//3))
```

##### 单串问题：变形，需要两个位置的情况：`dp[i][j]`以j、i结尾

> 有一些单串问题在涉及状态时需要考虑最后两位的情况，因为只考虑最后一个的化无法对状态描述清楚

​	**<font size=4>1.最长的斐波那契子序列的长度</font>**

```markdown
如果序列 X_1, X_2, ..., X_n 满足下列条件，就说它是 斐波那契式 的：
n >= 3
对于所有 i + 2 <= n，都有 X_i + X_{i+1} = X_{i+2}
给定一个严格递增的正整数数组形成序列，找到 A 中最长的斐波那契式的子序列的长度。如果一个不存在，返回  0 。
（回想一下，子序列是从原序列 A 中派生出来的，它从 A 中删掉任意数量的元素（也可以不删），而不改变其余元素的顺序。例如， [3, 5, 8] 是 [3, 4, 5, 6, 7, 8] 的一个子序列）
示例 1：

输入: [1,2,3,4,5,6,7,8]
输出: 5
解释:
最长的斐波那契式子序列为：[1,2,3,5,8] 。

示例 2：
输入: [1,3,7,11,12,14,18]
输出: 3
解释:
最长的斐波那契式子序列有：
[1,11,12]，[3,11,14] 以及 [7,11,18] 。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5ruvye/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
	将斐波那契的子序列中的两个连续项`A[i]`、`A[j]`视作单个节点(i,j)，那么整个子序列就是这些连续节点之间的路径例：A[1] = 2, A[2] = 3, A[4] = 5, A[7] = 8, 结点之间的路径为(1,2),(2, 4), (4,7).
	这样只有当A[i] + A[j] = A[k]时，结点[i, j]和[j, k]才能时连通的。
定义状态
	设longes[i, j]是以[i, j]结点结尾的最长路径，如果[i, j]和[j, k]是连通的，那么longest[j, k] = longest[i, j] + 1 i可以由A.index[A[k] - A[k]]求得
```

```python
from collections import defaultdict
class Solution:
    def lenLongestFibSubseq(self, A: List[int]) -> int:
        indexDic = {x: i for i, x in enumerate(A)}
        longest = defaultdict(lambda: 2)
        res = 0
        for k, num in enumerate(A):
            for j in range(k):
                i = indexDic.get(num - A[j], None)
                if i is not None and i < j:
                    longest[j, k] = longest[i, j] + 1
                    res = max(res, longest[j, k])
        return res if res >= 3 else 0
```

​	**<font size=4>2.最长等差数列</font>**

```markdown
给定一个整数数组 A，返回 A 中最长等差子序列的长度。
回想一下，A 的子序列是列表 A[i_1], A[i_2], ..., A[i_k] 其中 0 <= i_1 < i_2 < ... < i_k <= A.length - 1。并且如果 B[i+1] - B[i]( 0 <= i < B.length - 1) 的值都相同，那么序列 B 是等差的。

示例 1：
输入：[3,6,9,12]
输出：4
解释： 
整个数组是公差为 3 的等差数列。

示例 2：
输入：[9,4,7,2,10]
输出：3
解释：
最长的等差子序列是 [4,7,10]。

示例 3：
输入：[20,1,15,3,10,5,8]
输出：4
解释：
最长的等差子序列是 [20,15,10,5]。
 
提示：

2 <= A.length <= 2000
0 <= A[i] <= 10000

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5rta4g/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

**思路**
```





