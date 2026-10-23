//Climbing Stairs
public int climbStairs(int n) {
    if (n < 2) return n;
    int i = 1,j = 2;
    while (n-- > 2){
        int temp = i + j;
        i = j;
        j = temp;
    }
    return j;
}

//Min Cost Climbing Stairs
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int[] dp = Arrays.copyOf(cost,n + 1);
    dp[n] = 0;
    for (int i = n - 2; i >= 0; i--) dp[i] += Math.min(dp[i + 1],dp[i + 2]);
    return Math.min(dp[0],dp[1]);
}

//House Robber
public int rob(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    if (n == 1) return nums[0];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0],nums[1]);
    for (int i = 2; i < n; i++) dp[i] = Math.max(nums[i] + dp[i - 2],dp[i - 1]);
    return dp[n - 1];
}

//House Robber - II
public int rob1(int[] nums) {
    int n = nums.length;
    if (n == 1) return nums[0];

    int lfirst = Rob1(nums, 0);
    int llast = Rob1(nums, n - 1);

    return Math.max(lfirst, llast);
}

public int Rob1(int[] nums, int idx) {
    int n = nums.length;
    int temp = nums[idx];
    nums[idx] = 0;
    int[] dp = new int[n];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    for (int i = 2; i < n; i++) {
        int take = nums[i] + dp[i - 2];
        int not = dp[i - 1];
        dp[i] = Math.max(take, not);
    }
    nums[idx] = temp;
    return dp[n - 1];
}

//Longest Palindromic Substring
public String longestPalindrome(String s) {
    int left = 0,right = 0;
    for (int i = 0; i < s.length(); i++){
        //Odd length
        int odd = Long(s,i,i);
        //Even length
        int even = Long(s,i,i + 1);
        int max = Math.max(odd,even);
        if (max > right - left){
            left = i - (max - 1) / 2;
            right = i + max / 2;
        }
    }
    return s.substring(left,right + 1);
}

public int Long(String s,int l,int r){
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
        l--;
        r++;
    }
    return r - l - 1;
}

//Palindromic Substrings
public int countSubstrings(String s) {
    int ans = 0;
    for (int i = 0; i < s.length(); i++) ans += Count(s,i,i) + Count(s,i,i + 1);
    return ans;
}

public int Count(String s,int left,int right){
    int cnt = 0;
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
        left--;
        right++;
        cnt++;
    }
    return cnt;
}

//Decode Ways
public int numDecodings(String s) {
    if (s.charAt(0) == '0') return 0;
    int[] dp = new int[s.length()];
    Arrays.fill(dp,-1);
    return TopDown(s,0,dp);
}

public int TopDown(String s,int idx,int[] dp){
    if (idx == s.length()) return 1;
    if (s.charAt(idx) == '0') return 0;
    if (dp[idx] != -1) return dp[idx];
    int one = TopDown(s,idx + 1,dp);
    int two = 0;
    if (idx + 1 < s.length()){
        int val = Integer.valueOf(s.substring(idx,idx + 2));
        if (val <= 26) two = TopDown(s,idx + 2,dp);
    }
    return dp[idx] =  one + two;
}

//Coin Change
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp,amount + 1);
    dp[0] = 0;
    for (int coin : coins) for (int i = coin; i <= amount; i++) dp[i] = Math.min(dp[i],1 + dp[i - coin]);
    return dp[amount] == amount + 1 ? -1 : dp[amount];
}

//Maximum Product Subarray
public int maxProduct(int[] nums) {
    int ans = Integer.MIN_VALUE,cmax = 1,cmin = 1;
    for (int el : nums){
        int temp = cmax * el;
        cmax = Math.max(cmax * el,Math.max(cmin * el,el));
        cmin = Math.min(temp,Math.min(cmin * el,el));
        ans = Math.max(ans,cmax);
    }
    return ans;
}

//Word Break
public boolean wordBreak(String s, List<String> wordDict) {
    int n = s.length();
    Set<String> set = new HashSet<>(wordDict);
    boolean[] dp = new boolean[n + 1];
    dp[0] = true;
    for (int i = 0; i <= n; i++){
        for (int j = 0; j < i; j++){
            if (dp[j] && set.contains(s.substring(j,i))){
                dp[i] = true;
                break;
            }
        }
    }
    return dp[n];
}

//Longest Increasing Subsequence
public int lengthOfLIS(int[] nums) {
    List<Integer> dp = new ArrayList<>();
    dp.add(nums[0]);
    int ans = 1;
    for (int i = 1; i < nums.length; i++){
        if (dp.getLast() < nums[i]){
            dp.add(nums[i]);
            ans++;
            continue;
        }

        int idx = Collections.binarySearch(dp,nums[i]);
        if (idx < 0) idx = -idx - 1;
        dp.set(idx,nums[i]);
    }
    return ans;
}

//Partition Equal Subset Sum
public boolean canPartition(int[] nums) {
    int total = 0;
    for (int i : nums) total += i;
    if (total % 2 != 0) return false;
    Boolean[][] memo = new Boolean[nums.length][total / 2 + 1];
    return dp(nums,total / 2,0,memo);
}

public boolean dp(int[] nums,int total,int idx,Boolean[][] memo){
    if (total == 0) return true;
    if (total < 0 || idx == nums.length) return false;
    if (memo[idx][total] != null) return memo[idx][total];
    return memo[idx][total] = dp(nums,total,idx + 1,memo) || dp(nums,total - nums[idx],idx + 1,memo);
}

void main() {
    int n = 5;
    int result = climbStairs(n);
    System.out.println("Number of ways to climb " + n + " stairs: " + result);
    int[] cost = {10, 15, 20};
    int result1 = minCostClimbingStairs(cost);
    System.out.println("Minimum Cost: " + result1);
    int[] nums2 = {2, 7, 9, 3, 1};
    int result2 = rob(nums2);
    System.out.println("Maximum Amount: " + result2);
    int[] nums = {2, 3, 2};
    System.out.println("Maximum Amount: " + rob1(nums));
    String s = "babad";
    String result3 = longestPalindrome(s);
    System.out.println("Longest Palindromic Substring: " + result3);
    String s1 = "aaa";
    int result4 = countSubstrings(s1);
    System.out.println("Count of Palindromic Substrings: " + result4);
    String s2 = "226";
    int result5 = numDecodings(s2);
    System.out.println("Number of Decodings: " + result5);
    int[] coins = {1, 2, 5};
    int amount = 11;
    int result6 = coinChange(coins, amount);
    System.out.println("Minimum Coins Required: " + result6);
    int[] nums3 = {2, 3, -2, 4};
    int result7 = maxProduct(nums3);
    System.out.println("Maximum Product Subarray: " + result7);
    String s3 = "leetcode";
    List<String> wordDict = List.of("leet", "code");
    boolean result8 = wordBreak(s3, wordDict);
    System.out.println("Can Word Be Segmented: " + result8);
    int[] nums4 = {10, 9, 2, 5, 3, 7, 101, 18};
    int result9 = lengthOfLIS(nums4);
    System.out.println("Length of LIS: " + result9);
    int[] nums5 = {1, 5, 11, 5};
    boolean result10 = canPartition(nums5);
    System.out.println("Can Partition into Equal Subsets: " + result10);
}