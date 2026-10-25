//Unique Paths
public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if (i == 0 || j == 0) dp[i][j] = 1;
            else dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
    }
    return dp[m - 1][n - 1];
}

//Longest Common Subsequence
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(),n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++){
        for (int j = 1; j <= n; j++){
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) dp[i][j] = 1 + dp[i - 1][j - 1];
            else dp[i][j] = Math.max(dp[i - 1][j],dp[i][j - 1]);
        }
    }
    return dp[m][n];
}

//Best time to Buy and Sell Stock with Cooldown
public int maxProfit(int[] prices) {
    int[][] memo = new int[prices.length][2];
    for (int[] in : memo) Arrays.fill(in,-1);
    return dp(prices,0,1,memo);
}

public int dp(int[] prices,int idx,int buy,int[][] memo){
    if (idx >= prices.length) return 0;
    if (memo[idx][buy] != -1) return memo[idx][buy];
    int profit = 0;
    if (buy == 1){
        int take = -prices[idx] + dp(prices,idx + 1,0,memo);
        int skip = dp(prices,idx + 1,1,memo);
        profit = Math.max(take,skip);
    }
    else{
        int sell = prices[idx] + dp(prices,idx + 2,1,memo);
        int skip = dp(prices,idx + 1,0,memo);
        profit = Math.max(sell,skip);
    }
    return memo[idx][buy] = profit;
}

//Coin Change - II
public int change(int amount, int[] coins) {
    int[][] memo = new int[coins.length][amount + 1];
    for (int[] in : memo) Arrays.fill(in,-1);
    return dp(amount,coins,0,memo);
}

public int dp(int amount,int[] coins,int idx,int[][] memo){
    if (amount == 0) return 1;
    if (idx == coins.length) return 0;
    if (memo[idx][amount] != -1) return memo[idx][amount];
    int take = 0;
    if (coins[idx] <= amount) take = dp(amount - coins[idx],coins,idx,memo);
    int skip = dp(amount,coins,idx + 1,memo);
    return memo[idx][amount] = take + skip;
}

//Target sum
public int findTargetSumWays(int[] nums, int target) {
    int total = 0;
    for (int el : nums) total += el;
    int[][] dp = new int[2 * total + 2][nums.length];
    for (int[] ints : dp) Arrays.fill(ints, -1);
    return dfs(nums,target,0,0,dp,total);
}

public int dfs(int[] nums,int target,int idx,int curr,int[][] dp,int total){
    if (idx == nums.length){
        if (curr == target) return 1;
        else return 0;
    }
    if (dp[curr + total][idx] != -1) return dp[curr + total][idx];
    return dp[curr + total][idx] = dfs(nums, target, idx + 1, curr + nums[idx], dp, total) + dfs(nums, target, idx + 1, curr - nums[idx], dp, total);
}

//Interleaving String
public boolean isInterleave(String s1, String s2, String s3) {
    if (s1.length() + s2.length() != s3.length()) return false;
    Boolean[][] dp = new Boolean[s1.length() + 1][s2.length() + 1];
    return dfs(s1,s2,s3,0,0,dp);
}

public boolean dfs(String s1,String s2,String s3,int i,int j,Boolean[][] dp){
    if (i + j == s3.length()) return true;
    if (dp[i][j] != null) return dp[i][j];
    boolean take1 = false,take2 = false;
    if (i < s1.length() && s1.charAt(i) == s3.charAt(i + j)) take1 = dfs(s1,s2,s3,i + 1,j,dp);
    if (j < s2.length() && s2.charAt(j) == s3.charAt(i + j)) take2 = dfs(s1,s2,s3,i,j + 1,dp);
    return dp[i][j] = take1 || take2;
}

//Longest Increasing Path in a Matrix
public int longestIncreasingPath(int[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;
    int[][] dp = new int[m][n];
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int max = 0;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) max = Math.max(max, dfs(matrix, i, j, dp, directions));
    }
    return max;
}

private int dfs(int[][] matrix, int r, int c, int[][] dp, int[][] directions) {
    if (dp[r][c] != 0) return dp[r][c];
    dp[r][c] = 1;
    for (int[] dir : directions) {
        int nr = r + dir[0];
        int nc = c + dir[1];
        if (nr >= 0 && nr < matrix.length && nc >= 0 && nc < matrix[0].length && matrix[nr][nc] > matrix[r][c]) dp[r][c] = Math.max(dp[r][c],1 + dfs(matrix, nr, nc, dp, directions));
    }
    return dp[r][c];
}

//Number of Distinct Subsequences
public int numDistinct(String s, String t) {
    if (t.length() > s.length()) return 0;
    int[][] dp = new int[s.length()][t.length()];
    for (int[] ins : dp) Arrays.fill(ins,-1);
    return dfs(s,t,0,0,dp);
}

public int dfs(String s,String t,int i,int j,int[][] dp){
    if (j == t.length()) return 1;
    if (i == s.length()) return 0;
    if (dp[i][j] != -1) return dp[i][j];
    return dp[i][j] = dfs(s,t,i + 1,j,dp) + (s.charAt(i) == t.charAt(j) ? dfs(s,t,i + 1,j + 1,dp) : 0);
}

//Edit Distance
public int minDistance(String word1, String word2) {
    int[][] dp = new int[word1.length()][word2.length()];
    return dfs1(word1,word2,0,0,dp);
}

public int dfs1(String word1,String word2,int i,int j,int[][] dp){
    if (i == word1.length()) return word2.length() - j;
    if (j == word2.length()) return word1.length() - i;
    if (dp[i][j] != 0) return dp[i][j];
    if (word1.charAt(i) == word2.charAt(j)) return dp[i][j] = dfs1(word1,word2,i + 1,j + 1,dp);
    return dp[i][j] = 1 + Math.min(dfs1(word1,word2,i + 1,j,dp),Math.min(dfs1(word1,word2,i + 1,j + 1,dp),dfs1(word1,word2,i, j + 1,dp)));
}

//Burst Ballons
public int maxCoins(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n + 2];
    ans[0] = 1;
    System.arraycopy(nums, 0, ans, 1, n);
    int[][] dp = new int[n + 2][n + 2];
    ans[n + 1] = 1;
    return dfs(ans,1,n,dp);
}

public int dfs(int[] nums,int left,int right,int[][] dp){
    if (left > right) return 0;
    if (dp[left][right] != 0) return dp[left][right];
    int max = 0;
    for (int i = left; i <= right; i++) max = Math.max(max,(nums[left - 1] * nums[i] * nums[right + 1]) + dfs(nums,left,i - 1,dp) + dfs(nums,i + 1,right,dp));
    return dp[left][right] = max;
}

//Regular Expression Matching
public boolean isMatch(String text, String pattern) {
    Boolean[][] memo = new Boolean[text.length() + 1][pattern.length() + 1];
    return dfs(text, pattern, 0, 0, memo);
}

public boolean dfs(String text, String pattern, int i, int j, Boolean[][] memo) {
    if (memo[i][j] != null) return memo[i][j];
    if (j == pattern.length()) return memo[i][j] = (i == text.length());
    boolean first_match = i < text.length() && (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) == '.');
    return memo[i][j] = (j + 1 < pattern.length() && pattern.charAt(j + 1) == '*') ? dfs(text, pattern, i, j + 2, memo) || (first_match && dfs(text, pattern, i + 1, j, memo)) : first_match && dfs(text, pattern, i + 1, j + 1, memo);
}

void main() {
    int m = 3;
    int n = 7;
    int result = uniquePaths(m, n);
    System.out.println("Unique Paths: " + result);
    String text1 = "abcde";
    String text2 = "ace";
    int result1 = longestCommonSubsequence(text1, text2);
    System.out.println("Longest Common Subsequence Length: " + result1);
    int[] prices = {1, 2, 3, 0, 2};
    int result2 = maxProfit(prices);
    System.out.println("Maximum Profit: " + result2);
    int amount = 5;
    int[] coins = {1, 2, 5};
    int result3 = change(amount, coins);
    System.out.println("Number of Ways: " + result3);
    int[] nums = {1, 1, 1, 1, 1};
    int target = 3;
    int result4 = findTargetSumWays(nums, target);
    System.out.println("Number of Ways to get the target with + and - : " + result4);
    String s1 = "aabcc";
    String s2 = "dbbca";
    String s3 = "aadbbcbcac";
    boolean result5 = isInterleave(s1, s2, s3);
    System.out.println("Is Interleaving: " + result5);
    int[][] matrix = {
            {9, 9, 4},
            {6, 6, 8},
            {2, 1, 1}
    };
    int result6 = longestIncreasingPath(matrix);
    System.out.println("Longest Increasing Path: " + result6);
    String s = "rabbbit";
    String t = "rabbit";
    int result7 = numDistinct(s, t);
    System.out.println("Number of Distinct Subsequences: " + result7);
    String word1 = "horse";
    String word2 = "ros";
    int result8 = minDistance(word1, word2);
    System.out.println("Minimum Edit Distance: " + result8);
    int[] nums1 = {3, 1, 5, 8};
    int result9 = maxCoins(nums1);
    System.out.println("Maximum Coins: " + result9);
    String text = "aab";
    String pattern = "c*a*b";
    boolean result11 = isMatch(text, pattern);
    System.out.println("Regex Match: " + result11);
}