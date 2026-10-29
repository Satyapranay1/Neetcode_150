//Single Number
public int singleNumber(int[] nums) {
    int xor = 0;
    for (int i : nums) xor ^= i;
    return xor;
}

//Hamming Weight
public int hammingWeight(int n) {
    int ans = 0;
    while (n != 0){
        n &= n - 1;
        ans++;
    }
    return ans;
}

//Counting Bits
public int[] countBits(int n) {
    int mul = 1;
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++){
        if (i == mul * 2) mul = i;
        dp[i] = 1 + dp[i - mul];
    }
    return dp;
}

//Reverse Bits
public int reverseBits(int n) {
    int ans = 0;
    for (int i = 0; i < 32; i++){
        ans = (ans << 1) | (n & 1);
        n >>>= 1;
    }
    return ans;
}

//Missing Number
public int missingNumber(int[] nums) {
    int n = nums.length;
    int xor = n;
    for (int i = 0; i < n; i++) xor ^= i ^ nums[i];
    return xor;
}

//Sum of two Integers
public int getSum(int a, int b) {
    while (b != 0){
        int carry = (a & b) << 1;
        a = a ^ b;
        b = carry;
    }
    return a;
}

//Reverse Integer
public int reverse(int x) {
    int rev = 0;
    while (x != 0){
        int re = x % 10;
        int temp = rev * 10 + re;
        if ((temp - re) / 10 != rev) return 0;
        rev = temp;
        x /= 10;
    }
    return rev;
}

void main() {
    int[] nums = {4, 1, 2, 1, 2};
    int result = singleNumber(nums);
    System.out.println("Single Number: " + result);
    int n = 29;
    int result1 = hammingWeight(n);
    System.out.println("Number of Set Bits: " + result1);
    int n1 = 10;
    int[] result2 = countBits(n1);
    System.out.println("Count of Set Bits: " + Arrays.toString(result2));
    int n2 = 43261596;
    int result3 = reverseBits(n2);
    System.out.println("Reversed Bits: " + result3);
    int[] nums1 = {3, 0, 1, 4};
    int result4 = missingNumber(nums1);
    System.out.println("Missing Number: " + result4);
    System.out.println("Sum of Two Integers without using + and - " + getSum(5,10));
    System.out.println("Reversed Integer : " + reverse(256971));
}