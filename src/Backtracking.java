//Subsets
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Recur(nums,new ArrayList<>(),ans,0);
    return ans;
}

public void Recur(int[] nums,List<Integer> temp,List<List<Integer>> ans,int idx){
    if (idx == nums.length) {ans.add(new ArrayList<>(temp)); return;}
    temp.add(nums[idx]);
    Recur(nums,temp,ans,idx + 1);
    temp.removeLast();
    Recur(nums,temp,ans,idx + 1);
}

//Combination Sum
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> ans = new ArrayList<>();
    backtrack(candidates,target,new ArrayList<>(),ans,0);
    return ans;
}

public void backtrack(int[] candidates,int target,List<Integer> temp,List<List<Integer>> ans,int idx){
    if (target == 0){
        ans.add(new ArrayList<>(temp));
        return;
    }

    if (target < 0) return;

    for (int i = idx; i < candidates.length; i++){
        temp.add(candidates[i]);
        backtrack(candidates,target - candidates[i],temp,ans,i);
        temp.removeLast();
    }
}

//Combination Sum - II
public List<List<Integer>> combinationSum2(int[] nums, int target) {
    Arrays.sort(nums);
    List<List<Integer>> ans = new ArrayList<>();
    backtrack1(nums,target,new ArrayList<>(),ans,0);
    return ans;
}

public void backtrack1(int[] candidates,int target,List<Integer> temp,List<List<Integer>> ans,int idx){
    if (target == 0){
        ans.add(new ArrayList<>(temp));
        return;
    }

    if (target < 0) return;

    for (int i = idx; i < candidates.length; i++){
        if (i > idx && candidates[i] == candidates[i - 1]) continue;
        temp.add(candidates[i]);
        backtrack1(candidates,target - candidates[i],temp,ans,i + 1);
        temp.removeLast();
    }
}

//Permutations
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    List<Integer> temp = new ArrayList<>();
    backtrack(ans,temp,nums);
    return ans;
}

public void backtrack(List<List<Integer>> ans,List<Integer> temp,int[] nums){
    if (temp.size() == nums.length){
        ans.add(new ArrayList<>(temp));
    }

    for (int num : nums) {
        if (temp.contains(num)) continue;
        temp.add(num);
        backtrack(ans, temp, nums);
        temp.removeLast();
    }
}

//Subsets - II
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(nums);
    recur(ans,new ArrayList<>(),0,nums);
    return ans;
}

public void recur(List<List<Integer>> ans,List<Integer> temp,int start,int[] nums){
    ans.add(new ArrayList<>(temp));
    for (int i = start; i < nums.length; i++){
        if (i > start && nums[i] == nums[i - 1]) continue;
        temp.add(nums[i]);
        recur(ans,temp,i + 1,nums);
        temp.removeLast();
    }
}

//Generate Parentheses
public List<String> generateParenthesis(int n) {
    List<String> ans = new ArrayList<>();
    backtrack(ans,n,"",0,0,0);
    return ans;
}

public void backtrack(List<String> ans,int n,String curr,int idx,int open,int close){
    if (idx == 2 * n){
        ans.add(curr);
        return;
    }

    if (open < n) backtrack(ans,n,curr + "(",idx + 1,open + 1,close);
    if (close < open) backtrack(ans,n,curr + ")",idx + 1,open,close + 1);
}

//Word Search
public boolean exist(char[][] board, String word) {
    int m = board.length,n = board[0].length;
    boolean[][] vis = new boolean[m][n];
    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if (board[i][j] == word.charAt(0)) if (dfs(board,word,vis,i,j,0)) return true;
        }
    }
    return false;
}

public boolean dfs(char[][] board,String word,boolean[][] vis,int i,int j,int idx){
    if (idx == word.length()) return true;
    if (i < 0 || j < 0 || i == board.length || j == board[0].length || board[i][j] != word.charAt(idx) || vis[i][j]) return false;
    vis[i][j] = true;
    boolean res = dfs(board,word,vis,i + 1,j,idx + 1) || dfs(board,word,vis,i,j + 1,idx + 1) || dfs(board,word,vis,i - 1,j,idx + 1) || dfs(board,word,vis,i,j - 1,idx + 1);
    vis[i][j] = false;
    return res;
}

//Palindrome Partitioning
public List<List<String>> partition(String s) {
    List<List<String>> ans = new ArrayList<>();
    dfs(s,ans,0,new ArrayList<>());
    return ans;
}

public void dfs(String s,List<List<String>> ans,int idx,List<String> temp){
    if (idx == s.length()) {
        ans.add(new ArrayList<>(temp));
        return;
    }

    for (int i = idx; i < s.length(); i++){
        if (check(s,idx,i)){
            temp.add(s.substring(idx,i + 1));
            dfs(s,ans,i + 1,temp);
            temp.removeLast();
        }
    }
}

public boolean check(String s,int left,int right){
    while (left <= right) if (s.charAt(left++) != s.charAt(right--)) return false;
    return true;
}

//Letter Combinations of a Phone Number
public List<String> letterCombinations(String digits) {
    String[] map = {"abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    List<String> ans = new ArrayList<>();
    dfs(digits,map,ans,new StringBuilder(),0);
    return ans;
}

public void dfs(String digits,String[] map,List<String> ans,StringBuilder temp,int idx){
    if (idx == digits.length()){
        ans.add(temp.toString());
        return;
    }

    String curr = map[digits.charAt(idx) - '2'];
    for (char c : curr.toCharArray()){
        temp.append(c);
        dfs(digits,map,ans,temp,idx + 1);
        temp.deleteCharAt(temp.length() - 1);
    }
}

//N - Queens
public List<List<String>> solveNQueens(int n) {
    List<List<String>> ans = new ArrayList<>();
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row,'.');
    dfs(ans,board,n,0);
    return ans;
}

public void dfs(List<List<String>> ans,char[][] board,int n,int col){
    if (col == n){
        List<String> temp = new ArrayList<>();
        for (char[] row : board) temp.add(new String(row));
        ans.add(temp);
        return;
    }

    for (int row = 0; row < n; row++){
        if (safe(board,row,col)){
            board[row][col] = 'Q';
            dfs(ans,board,n,col + 1);
            board[row][col] = '.';
        }
    }
}

public boolean safe(char[][] board,int row,int col){
    for (int j = 0; j < col; j++) if (board[row][j] == 'Q') return false;//Check row
    for (int i = row,j = col; i >= 0 && j >= 0; i--,j--) if (board[i][j] == 'Q') return false;//Check Diagonal Left Upper
    for (int i = row,j = col; i < board.length && j >= 0; i++,j-- ) if (board[i][j] == 'Q') return false;//Check Diagonal Left down
    return true;
}
void main(){
    int[] nums = {4, 7, 9};

    List<List<Integer>> result = subsets(nums);

    for (List<Integer> subset : result) System.out.println(subset);

    int[] candidates = {2, 3, 5, 7};
    int target = 12;
    List<List<Integer>> result2 = combinationSum(candidates, target);

    for (List<Integer> combination : result2) System.out.println(combination);
    int[] nums1 = {10, 1, 2, 7, 6, 1, 5};
    int target1 = 8;

    List<List<Integer>> result1 = combinationSum2(nums1, target1);

    for (List<Integer> combination : result1) System.out.println(combination);

    int[] nums2 = {4, 7, 9};

    List<List<Integer>> result3 = permute(nums2);

    for (List<Integer> permutation : result3) System.out.println(permutation);

    int[] nums3 = {4, 2, 4, 1, 2};

    var result4 = subsetsWithDup(nums3);

    result4.forEach(System.out::println);

    int n = 4;

    List<String> result5 = generateParenthesis(n);

    result5.forEach(System.out::println);

    char[][] board = {
            {'M', 'N', 'O', 'P', 'Q'},
            {'R', 'S', 'T', 'U', 'V'},
            {'W', 'X', 'Y', 'Z', 'A'},
            {'B', 'C', 'D', 'E', 'F'}
    };

    String word = "STYZ";
    boolean e = exist(board,word);
    System.out.println(word + (e ? " exists in board" : " not exists in board"));

    String s = "levelnoon";

    List<List<String>> result6 = partition(s);

    for (List<String> part : result6) System.out.println(part);

    String digits = "729";

    List<String> result7 = letterCombinations(digits);

    IO.println(result7);
    System.out.println("Total combinations: " + result7.size());

    int n1 = 5;

    List<List<String>> result8 = solveNQueens(n1);

    System.out.println("Number of solutions: " + result8.size());

    for (List<String> board1 : result8) {
        for (String row : board1) {
            System.out.println(row);
        }
        System.out.println();
    }
}