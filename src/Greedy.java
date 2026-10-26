//Maximum Subarray
public int maxSubArray(int[] nums) {
    int curr = nums[0],max = nums[0];
    for (int i = 1; i < nums.length; i++){
        if (curr < 0) curr = 0;
        curr += nums[i];
        if (max < curr) max = curr;
    }
    return max;
}

//Jump game
public boolean canJump(int[] nums) {
    int goal = nums.length - 1;
    for (int i = nums.length - 2; i >= 0; i--) if (i + nums[i] >= goal) goal = i;
    return goal == 0;
}

//Jump game - II
public int jump(int[] nums) {
    int curr = 0,far = 0,ans = 0,n = nums.length;
    for (int i = 0; i < n - 1; i++){
        if (i + nums[i] > far) far = nums[i] + i;
        if (i == curr){
            curr = far;
            ans++;
        }
    }
    return ans;
}

//Gas Stations
public int canCompleteCircuit(int[] gas, int[] cost) {
    int curr = 0,total = 0,start = 0;
    for (int i = 0; i < gas.length; i++){
        int net = gas[i] - cost[i];
        total += net;
        curr += net;
        if (curr < 0){
            curr = 0;
            start = i + 1;
        }
    }
    return total >= 0 ? start : -1;
}

//Hand of Straights
public boolean isNStraightHand(int[] hand, int groupSize) {
    int n = hand.length;
    if (n % groupSize != 0) return false;
    TreeMap<Integer,Integer> map = new TreeMap<>();
    for (int el : hand) map.put(el,map.getOrDefault(el,0) + 1);
    while (!map.isEmpty()){
        int start = map.firstKey();
        for (int card = start; card < start + groupSize; card++){
            if (!map.containsKey(card)) return false;
            int count = map.get(card);
            if (count == 1) map.remove(card);
            else map.put(card,count - 1);
        }
    }
    return true;
}

//Partition Labels
public List<Integer> partitionLabels(String s) {
    int[] last = new int[26];
    List<Integer> ans = new ArrayList<>();
    int maxd = 0,group = 0;
    for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
    for (int i = 0; i < s.length(); i++){
        maxd = Math.max(maxd,last[s.charAt(i) - 'a']);
        if (maxd == i) {
            ans.add(i - group + 1);
            group = i + 1;
        }
    }
    // System.out.println(Arrays.toString(last));
    return ans;
}

//Valid Parentheses String
public boolean checkValidString(String s) {
    Stack<Integer> left = new Stack<>(),star = new Stack<>();
    for (int i = 0; i < s.length(); i++){
        char c = s.charAt(i);
        if (c == '(') left.push(i);
        else if (c == '*') star.push(i);
        else {
            if (left.isEmpty() && star.isEmpty()) return false;
            if (!left.isEmpty()) left.pop();
            else star.pop();
        }
    }

    while (!left.isEmpty() && !star.isEmpty()) if (left.pop() > star.pop()) return false;
    return left.isEmpty();
}

//Merge Triplets to form Target Triplet
public boolean mergeTriplets(int[][] triplets, int[] target) {
    boolean first = false,second = false,third = false;
    for (int[] trip : triplets){
        if (trip[0] <= target[0] && trip[1] <= target[1] && trip[2] <= target[2]){
            if (trip[0] == target[0]) first = true;
            if (trip[1] == target[1]) second = true;
            if (trip[2] == target[2]) third = true;
        }
    }
    return first && second && third;
}

void main() {
    int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    int result = maxSubArray(nums);
    System.out.println("Maximum Subarray Sum: " + result);
    int[] nums1 = {2, 3, 1, 1, 4};
    boolean result2 = canJump(nums1);
    System.out.println("Can Reach Last Index: " + result2);
    int[] nums2 = {2, 3, 1, 1, 4};
    int result3 = jump(nums2);
    System.out.println("Minimum Jumps: " + result3);
    int[] gas  = {1, 2, 3, 4, 5};
    int[] cost = {3, 4, 5, 1, 2};
    int result4 = canCompleteCircuit(gas, cost);
    System.out.println("Starting Gas Station: " + result4);
    int[] hand = {1,5,2,3,4,2,3,6,7};
    System.out.println("Does it make pairs with the group size : " + isNStraightHand(hand,3));
    String s = "ababcbacadefegdehijhklij";
    System.out.println("Length of the groups that have at most one characters : " + partitionLabels(s));
    String s1 = "((((()(()()()*()(((((*)()*(**(())))))(())()())(((())())())))))))(((((())*)))()))(()((*()*(*)))(*)()";
    System.out.println("Is it a valid Parentheses String : " + checkValidString(s1));
    int[][] triplets = {{2,5,3},{1,8,4},{1,7,5}};
    int[] target = {2,7,5};
    System.out.println("Does it make a target triplet with the given triplets : " + mergeTriplets(triplets,target));
}