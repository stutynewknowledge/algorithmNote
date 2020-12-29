import java.util.List;

/**
 * 二分查找
 */
public class Bisect {
    /**
     *
     * @param nums：排序的列表 List<Integer>
     * @param target：目标值 int
     * @return：int
     */
    public static int bisect_left(List<Integer> nums, int target){
        int lo = 0;
        int hi = nums.size();
        while (lo < hi){
            int mid = (lo + hi) / 2;
            if(target > nums.get(mid)){
                lo = mid + 1;
            }else{
                hi = mid;
            }
        }
        return lo;
    }

    
    public static int bisect_right(List<Integer> nums, int target){
        int lo = 0;
        int hi = nums.size();
        while (lo < hi){
            int mid = (lo + hi) / 2;
            if (target < nums.get(mid)){
                hi = mid;
            }else{
                lo = mid + 1;
            }
        }
        return lo;
    }

    /**
     * 使用二分查找到数组中第一个比目标值大的数的索引，并且将目标值插入其中
     * @param nums
     * @param target
     * @return
     */
    public void insert_right(List<Integer> nums, Integer target) {
        int lo = 0;
        int hi = nums.size();
        while (lo < hi) {
            int mid = (hi + lo) / 2;
            if (target < nums.get(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        nums.add(lo, target);
    }
}
