//Best time to Buy and Sell Stock
public int maxProfit(int[] prices) {
    int min = prices[0],ans = 0;
    for (int i = 0; i < prices.length; i++){
        if (min > prices[i]) min = prices[i];
        ans = Math.max(ans,prices[i] - min);
    }
    return ans;
}

//Longest Substring without Repeating Characters
public int lengthOfLongestSubstring(String s) {
    int[] last = new int[128];
    Arrays.fill(last,-1);
    int left = 0,ans = 0;
    for (int right = 0; right < s.length(); right++){
        char c = s.charAt(right);
        if (last[c] != -1) left = Math.max(left,last[c] + 1);
        last[c] = right;
        ans = Math.max(ans,right - left + 1);
    }
    return ans;
}

//Longest Repeating Character Replacement
public int characterReplacement(String s, int k) {
    int[] freq = new int[26];
    int left = 0,ans = 0,maxi = 0;
    for (int right = 0; right < s.length(); right++){
        int idx = s.charAt(right)- 'A';
        freq[idx]++;
        maxi = Math.max(maxi,freq[idx]);
        while ((right - left + 1) - maxi > k) freq[s.charAt(left++) - 'A']--;
        ans = Math.max(ans,right - left + 1);
    }
    return ans;
}

//Permutation in String
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) {
        return false;
    }

    int[] s1Count = new int[26];
    int[] s2Count = new int[26];
    for (int i = 0; i < s1.length(); i++) {
        s1Count[s1.charAt(i) - 'a']++;
        s2Count[s2.charAt(i) - 'a']++;
    }

    int matches = 0;
    for (int i = 0; i < 26; i++) {
        if (s1Count[i] == s2Count[i]) {
            matches++;
        }
    }

    int l = 0;
    for (int r = s1.length(); r < s2.length(); r++) {
        if (matches == 26) {
            return true;
        }

        int index = s2.charAt(r) - 'a';
        s2Count[index]++;
        if (s1Count[index] == s2Count[index]) {
            matches++;
        } else if (s1Count[index] + 1 == s2Count[index]) {
            matches--;
        }

        index = s2.charAt(l) - 'a';
        s2Count[index]--;
        if (s1Count[index] == s2Count[index]) {
            matches++;
        } else if (s1Count[index] - 1 == s2Count[index]) {
            matches--;
        }
        l++;
    }
    return matches == 26;
}

//Minimum Window Substring
public String minWindow(String s, String t) {
    int[] need = new int[128];
    int[] window = new int[128];

    int left = 0;
    int found = 0;
    int min = Integer.MAX_VALUE;
    int start = 0;

    for (char c : t.toCharArray()) need[c]++;

    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        window[c]++;
        if (window[c] <= need[c]) found++;
        while (found == t.length()) {

            if (right - left + 1 < min) {
                min = right - left + 1;
                start = left;
            }

            char left1 = s.charAt(left);
            window[left1]--;

            if (window[left1] < need[left1]) found--;
            left++;
        }
    }

    if (min == Integer.MAX_VALUE) return "";
    return s.substring(start, start + min);
}

//Sliding Window Maximum
public int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> dq = new LinkedList<>();
    int[] ans = new int[nums.length - k + 1];
    for (int i = 0; i < nums.length; i++){
        while (!dq.isEmpty() && dq.peek() <= i - k) dq.pollFirst();    //Remove elements that are out of window
        while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) dq.pollLast(); //Remove elements from the end that are greater than curr element
        dq.offer(i);
        if (i >= k - 1) ans[i - k + 1] = nums[dq.peekFirst()];
    }
    return ans;
}
void main() {
    int[] prices = {7, 1, 5, 3, 6, 4};

    int result = maxProfit(prices);

    System.out.println("Maximum Profit: " + result);

    String s = "abcabcbb";

    int result1 = lengthOfLongestSubstring(s);

    System.out.println("Longest Substring Length: " + result1);

    String s1 = "AABABBA";
    int k = 1;

    int result2 = characterReplacement(s1, k);

    System.out.println("Longest Repeating Character Replacement: " + result2);

    String s13 = "ab";
    String s2 = "eidbaooo";

    boolean result4 = checkInclusion(s13, s2);

    System.out.println("Permutation Present: " + result4);

    String s4 = "ADOBECODEBANC";
    String t = "ABC";

    String result5 = minWindow(s4, t);

    System.out.println("Minimum Window: " + result5);

    int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
    int k1 = 3;

    int[] result6 = maxSlidingWindow(nums, k1);

    System.out.println("Sliding Window Maximum: " + Arrays.toString(result6));
}