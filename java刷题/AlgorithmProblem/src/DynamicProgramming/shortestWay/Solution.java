package DynamicProgramming.shortestWay;

public class Solution {
    public int shortestWay(String source, String target) {
        int sLen = source.length();
        int tLen = target.length();
        int result = 0;
        int i = 0;
        while (i < tLen){
            int temp = i, j = 0;
            while (j < sLen){
                if(source.charAt(j) == target.charAt(i)){
                    ++i;
                    if(i == tLen){
                        break;
                    }
                }
                j++;
            }

            if(temp == i) {
                return -1;
            }
            result++;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().shortestWay("abc", "abcbc"));
    }
}
