//Valid Palindrome
public boolean isPalindrome(String s) {
    s = s.toLowerCase().replaceAll("[^a-z0-9]","");
    int left = 0,right = s.length() - 1;
    while (left <= right) if (s.charAt(left++) != s.charAt(right--)) return false;
    return true;
}

//Two sum - II(Input Array is Sorted)
public int[] twoSum(int[] numbers, int target) {
    int left = 0,right = numbers.length - 1;
    while (left <= right){
        if (numbers[left] + numbers[right] == target) return new int[]{left + 1,right + 1};
        else if (numbers[left] + numbers[right] < target) left++;
        else right--;
    }
    return new int[]{};
}

//3Sum
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++){
        if (i > 0 && nums[i] == nums[i - 1]) continue;
        int j = i + 1,k = nums.length - 1;
        while (j < k){
            int curr = nums[i] + nums[j] + nums[k];
            if (curr == 0){
                List<Integer> in = new ArrayList<>();
                in.add(nums[i]);
                in.add(nums[j]);
                in.add(nums[k]);
                ans.add(in);
                j++;
                k--;
                while (j < k && nums[j] == nums[j - 1]) j++;
                while (j < k && nums[k] == nums[k + 1]) k--;
            }
            else if (curr < 0) j++;
            else k--;
        }
    }
    return ans;
}

//Container with Most Water
public int maxArea(int[] height) {
    int left = 0,right = height.length - 1,ans = 0;
    while (left <= right){
        ans = Math.max(ans,(right - left) * (Math.min(height[left],height[right])));
        if (height[left] <= height[right]) left++;
        else right--;
    }
    return ans;
}

//Trapping Rain Water
public int trap(int[] height) {
    int ans = 0,lmax = 0,rmax = 0,left = 0,right = height.length - 1;
    while (left <= right){
        if (height[left] <= height[right]){
            lmax = Math.max(lmax,height[left]);
            ans += (lmax - height[left]);
            left++;
        }
        else{
            rmax = Math.max(rmax,height[right]);
            ans += (rmax - height[right]);
            right--;
        }
    }
    return ans;
}

void main(){
    String s = "A man, a plan, a canal: Panama";
    boolean result = isPalindrome(s);
    System.out.println("Is Palindrome: " + result);

    int[] numbers = {2, 7, 11, 15};
    int target = 9;

    int[] result2 = twoSum(numbers, target);

    System.out.println("Indices: " + Arrays.toString(result2));

    int[] nums = {-1, 0, 1, 2, -1, -4};

    List<List<Integer>> result3 = threeSum(nums);

    System.out.println("Triplets: " + result3);

    int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};

    int result4 = maxArea(height);

    System.out.println("Maximum Water: " + result4);

    int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};

    int result5 = trap(height1);

    System.out.println("Trapped Rain Water: " + result5);
}