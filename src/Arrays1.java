//Contains Duplicate
public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> set = new HashSet<>();
    for (int el : nums){
        if (set.contains(el)) return true;
        set.add(el);
    }
    return false;
}

//Valid Anagram
public boolean isAnagram(String s, String t) {
    int[] a = new int[26];
    for (int i = 0; i < s.length(); i++) a[s.charAt(i) - 'a']++;
    for (int i = 0; i < t.length(); i++) a[t.charAt(i) - 'a']--;
    for (int i = 0; i < 26; i++) if (a[i] != 0) return false;
    return true;
}

//2 Sum
public int[] twoSum(int[] nums, int target) {
    HashMap<Integer,Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++){
        if (map.containsKey(target - nums[i])) return new int[]{i,map.get(target - nums[i])};
        map.put(nums[i],i);
    }
    return new int[]{};
}

//Group Anagrams
public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> ans = new ArrayList<>();
    HashMap<String,List<String>> map = new HashMap<>();
    for (String curr : strs){
        char[] c = curr.toCharArray();
        Arrays.sort(c);
        String n1 = new String(c);
        if (!map.containsKey(n1)) map.put(n1,new ArrayList<>());
        map.get(n1).add(curr);
    }
    // System.out.println(map);
    ans.addAll(map.values());
    return ans;
}

//Top K Frequent Elements
public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer,Integer> map = new HashMap<>();
    for (int curr : nums) map.put(curr,map.getOrDefault(curr,0) + 1);
    int[] ans = new int[k];
    PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> map.get(n1) - map.get(n2));
    for (int key:map.keySet()){
        pq.add(key);
        if (pq.size() > k) pq.poll();
    }
    for (int i = 0; i < k; i++) ans[i] = pq.poll();
    return ans;
}

//Encode and Decode Strings
public String encode(List<String> strs) {
    StringBuilder ans = new StringBuilder();
    for (String curr : strs) ans.append(curr.length()).append("#").append(curr);
    return ans.toString();
}

public List<String> decode(String str) {
    List<String> ans = new ArrayList<>();
    int curr_len = 0,i = 0;
    while (i < str.length()){
        int j = i;
        while (str.charAt(j) != '#') j++;
        curr_len = Integer.parseInt(str.substring(i,j));
        j++;
        ans.add(str.substring(j,j + curr_len));
        i = j + curr_len;
    }
    return ans;
}

//Product of Array Except Self
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];

    int prod = 1;
    for (int i = 0; i < n; i++){
        prod *= nums[i];
        ans[i] = prod;
    }

    prod = 1;
    for (int i = n - 1; i > 0; i--){
        ans[i] = ans[i - 1] * prod;
        prod *= nums[i];
    }
    ans[0] = prod;
    return ans;
}

//Valid Sudoku
public boolean isValidSudoku(char[][] board) {
    for (int i = 0; i < 9; i++){
        for (int j = 0; j < 9; j++){
            if (board[i][j] == '.'){
                continue;
            }
            else{
                if (!isValid(board,i,j,board[i][j])){
                    return false;
                }
            }
        }
    }
    return true;
}

public boolean isValid(char[][] board,int row,int col,char ch){
    for (int i = 0; i < 9; i++){
        if (board[row][i] == ch && i != col){
            return false;
        }

        if (board[i][col] == ch && i != row){
            return false;
        }

        if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == ch && 3 * (row / 3) + i / 3 != row && 3 * (col / 3) + i % 3 != col){
            return false;
        }
    }
    return true;
}

//Longest Consecutive Sequence
public int longestConsecutive(int[] nums) {
    HashSet<Integer> set = new HashSet<>();
    for (int el : nums) set.add(el);
    int max = 0;
    for (int el : set){
        if (!set.contains(el - 1)){
            int curr = el;
            int streak = 1;
            while (set.contains(curr + 1)){
                curr++;
                streak++;
            }
            max = Math.max(max,streak);
        }
    }
    return max;
}
void main() {
    int[] nums = {1, 2, 3, 1};

    boolean result = containsDuplicate(nums);

    System.out.println("Contains Duplicate: " + result);

    String s = "anagram";
    String t = "nagaram";

    boolean result1 = isAnagram(s, t);

    System.out.println("Is Anagram: " + result1);

    int[] nums2 = {2, 7, 11, 15};
    int target = 9;

    int[] result2 = twoSum(nums2, target);

    System.out.println("Indices: " + Arrays.toString(result2));

    String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

    List<List<String>> result3 = groupAnagrams(strs);

    System.out.println("Grouped Anagrams: " + result3);

    int[] nums3 = {1, 1, 1, 2, 2, 3};
    int k = 2;

    int[] result4 = topKFrequent(nums3, k);

    System.out.println("Top " + k + " Frequent Elements: " + Arrays.toString(result4));

    List<String> strs2 = List.of("neet", "code", "love", "you");

    String encoded = encode(strs2);
    List<String> decoded = decode(encoded);

    System.out.println("Original: " + strs2);
    System.out.println("Encoded: " + encoded);
    System.out.println("Decoded: " + decoded);

    int[] nums4 = {1, 2, 3, 4};

    int[] result5 = productExceptSelf(nums4);

    System.out.println("Product Except Self: " + Arrays.toString(result5));

    char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
    };

    boolean result6 = isValidSudoku(board);

    System.out.println("Valid Sudoku: " + result6);

    int[] nums6 = {100, 4, 200, 1, 3, 2};

    int result7 = longestConsecutive(nums6);

    System.out.println("Longest Consecutive Sequence: " + result7);
}