package ADailyTopic.TwoZeroTwoOne.zeroOne.threeOne;

/**
 *
 */
public class Solution {
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();

        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if(check(strs[i], strs[j], m)){
                    uf.union(i, j);
                }
            }
        }
        return uf.getResult();
    }

    public boolean check(String str1, String str2, int len){
        int diffNum = 0;
        for (int i = 0; i < len; i++) {
            if(str1.charAt(i) != str2.charAt(i)){
                diffNum++;
                if(diffNum > 2){
                    return false;
                }
            }
        }
        return true;
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
            while(i != parents[i]){
                this.parents[i] = parents[parents[i]];
                i = parents[i];
            }
            return i;
        }

        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if(rootX == rootY){
                return ;
            }
            this.parents[rootX] = rootY;
            result--;
        }

        public int getResult() {
            return result;
        }
    }
}
