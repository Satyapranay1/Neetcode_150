//Binary Search
public int search(int[] nums, int target) {
    int left = 0,right = nums.length - 1;
    while (left <= right){
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}

//Search in a 2d Matrix
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length,n = matrix[0].length;
    int left = 0,right = m * n - 1;
    while (left <= right){
        int mid = left + (right - left) / 2;
        int row = mid / n,col = mid % n;
        if (matrix[row][col] < target) left = mid + 1;
        else if (matrix[row][col] > target) right = mid - 1;
        else return true;
    }
    return false;
}

//Koko Eating Bananas
public int minEatingSpeed(int[] piles, int h) {
    int low = 1,high = 0;
    for (int pile : piles) high = Math.max(high,pile);
    while (low <= high){
        int mid = low + (high - low) / 2;
        long hours = 0;
        for (int pile : piles) hours += (pile + mid - 1) / mid;
        if (hours > h) low = mid + 1;
        else high = mid - 1;
    }
    return low;
}

//Find Minimum in Rotated Sorted Array
public int findMin(int[] nums) {
    int low = 0,high = nums.length - 1;
    int ans = nums[0];
    while (low <= high){
        int mid = low + (high - low) / 2;
        if (nums[low] <= nums[mid]){
            ans = Math.min(ans,nums[low]);
            low = mid + 1;
        }
        else{
            ans = Math.min(ans,nums[mid]);
            high = mid - 1;
        }
    }
    return ans;
}

//Search in a Rotated Sorted Array
public int search1(int[] nums, int target) {
    int low = 0,high = nums.length - 1;
    while (low <= high){
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[low] <= nums[mid]){
            if (nums[low] <= target && target <= nums[mid]) high = mid - 1;
            else low = mid + 1;
        }
        else{
            if (nums[mid] <= target && target <= nums[high]) low = mid + 1;
            else high = mid - 1;
        }
    }
    return -1;
}

//Time Based Key Value Store
class TimeMap {
    HashMap<String,TreeMap<Integer,String>> map;
    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        map.computeIfAbsent(key,k -> new TreeMap<>()).put(timestamp,value);
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) return "";
        TreeMap<Integer, String> timestamps = map.get(key);
        Map.Entry<Integer, String> entry = timestamps.floorEntry(timestamp);
        return entry == null ? "" : entry.getValue();
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */

//Median of 2 sorted arrays
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int m = nums1.length,n = nums2.length;
    if (m > n) return findMedianSortedArrays(nums2,nums1);
    int total = m + n,half = (total + 1) / 2;
    int left = 0,right = m;
    while (left <= right){
        int cut1 = left + (right - left) / 2,cut2 = half - cut1;
        int a1 = cut1 > 0 ? nums1[cut1 - 1] : Integer.MIN_VALUE;
        int a2 = cut1 < nums1.length ? nums1[cut1] : Integer.MAX_VALUE;
        int b1 = cut2 > 0 ? nums2[cut2 - 1] : Integer.MIN_VALUE;
        int b2 = cut2 < nums2.length ? nums2[cut2] : Integer.MAX_VALUE;
        if (a1 <= b2 && a2 >= b1) return total % 2 == 0 ? (Math.max(a1,b1) + Math.min(a2,b2)) / 2.0  : Math.max(a1,b1);
        else if (a1 > b2) right = cut1 - 1;
        else left = cut1 + 1;
    }
    return -1;
}
void main() {
    int[] nums = {-18, -7, 0, 4, 9, 13, 21, 35, 42};
    int target = 21;

    int result = search(nums, target);

    System.out.println("Index: " + result);

    int[][] matrix = {
            {-25, -18, -10, -3},
            {2, 7, 14, 19},
            {24, 31, 38, 45},
            {51, 63, 72, 89}
    };

    int target1 = 38;

    boolean result1 = searchMatrix(matrix, target1);

    System.out.println("Target found: " + result1);

    int[] piles = {23, 11, 17, 8, 14};
    int h = 10;

    int result2 = minEatingSpeed(piles, h);

    System.out.println("Minimum Eating Speed: " + result2);

    int[] nums3 = {31, 38, 42, 47, 3, 8, 12, 19, 25};

    int result3 = findMin(nums3);

    System.out.println("Minimum: " + result3);

    int[] nums4 = {42, 47, 53, 61, 4, 9, 15, 23, 31};
    int target4 = 23;

    int result4 = search1(nums4, target4);

    System.out.println("Target index: " + result4);

    TimeMap timeMap = new TimeMap();

    timeMap.set("foo", "A", 2);
    timeMap.set("foo", "B", 5);
    timeMap.set("foo", "C", 9);
    timeMap.set("foo", "D", 14);
    timeMap.set("foo", "E", 20);

    timeMap.set("bar", "X", 3);
    timeMap.set("bar", "Y", 8);
    timeMap.set("bar", "Z", 15);

    System.out.println(timeMap.get("foo", 1));   // ""
    System.out.println(timeMap.get("foo", 2));   // A
    System.out.println(timeMap.get("foo", 7));   // B
    System.out.println(timeMap.get("foo", 12));  // C
    System.out.println(timeMap.get("foo", 20));  // E
    System.out.println(timeMap.get("foo", 30));  // E

    System.out.println(timeMap.get("bar", 1));   // ""
    System.out.println(timeMap.get("bar", 5));   // X
    System.out.println(timeMap.get("bar", 10));  // Y
    System.out.println(timeMap.get("bar", 15));  // Z
    System.out.println(timeMap.get("bar", 25));  // Z

    System.out.println(timeMap.get("unknown", 10)); // ""

    int[] nums1 = {3, 8, 12, 17};
    int[] nums2 = {1, 5, 9, 14, 20, 25, 31};

    double result5 = findMedianSortedArrays(nums1, nums2);

    System.out.println("Median: " + result5);
}