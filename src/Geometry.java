//Rotate Image
public void rotate(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++){
        for (int j = 0;  j < i; j++){
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }

    for (int i = 0; i < matrix.length; i++){
        for (int j = 0; j < matrix[0].length / 2; j++){
            int temp = matrix[i][j];
            matrix[i][j] = matrix[i][matrix[0].length - j - 1];
            matrix[i][matrix[0].length - j - 1] = temp;
        }
    }
}

//Spiral Matrix
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> ans = new ArrayList<>();
    int top = 0,left = 0,bottom = matrix.length - 1,right = matrix[0].length - 1;
    while (top <= bottom && left <= right){
        //Left
        for (int i = left; i <= right; i++) ans.add(matrix[top][i]);
        top++;
        //Down
        for (int i = top; i <= bottom; i++) ans.add(matrix[i][right]);
        right--;
        //Right
        if (top <= bottom){
            for (int i = right; i >= left; i--) ans.add(matrix[bottom][i]);
            bottom--;
        }
        //Up
        if (left <= right){
            for (int i = bottom; i >= top; i--) ans.add(matrix[i][left]);
            left++;
        }
    }
    return ans;
}

//Set Matrix Zeroes
public void setZeroes(int[][] matrix) {
    boolean is = false;
    int m = matrix.length,n = matrix[0].length;
    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if (matrix[i][j] == 0){
                if (j == 0) is = true;
                else matrix[0][j] = 0;
                matrix[i][0] = 0;
            }
        }
    }
    for (int i = 1; i < m; i++){
        for (int j = 1; j < n; j++){
            if (matrix[0][j] == 0 || matrix[i][0] == 0) matrix[i][j] = 0;
        }
    }
    if (matrix[0][0] == 0) for (int j = 0; j < n; j++) matrix[0][j] = 0;
    if (is) for (int i = 0; i < m; i++) matrix[i][0] = 0;
}

//Happy Number
public boolean isHappy(int n) {
    int slow = n,fast = squares(n);
    while (slow != fast){
        slow = squares(slow);
        fast = squares(squares(fast));
    }
    return fast == 1;
}

public int squares(int n){
    int sq = 0;
    while (n != 0){
        sq += (n % 10) * (n % 10);
        n /= 10;
    }
    return sq;
}

//Plus One
public int[] plusOne(int[] digits) {
    int n = digits.length;
    for (int i = n - 1; i >= 0; i--){
        if (digits[i] < 9){
            digits[i]++;
            return digits;
        }
        digits[i] = 0;
    }
    int[] res = new int[n + 1];
    res[0] = 1;
    return res;
}

//Pow(x,n)
public double myPow(double x, int n) {
    if ((long) n < 0) return 1.0 / fact(x, -(long) n);
    return fact(x, n);
}

public double fact(double x,long n){
    if (n == 0) return 1;
    return n % 2 == 0 ? fact(x * x,n / 2) : x * fact(x,n - 1);
}

//Multiply Strings
public String multiply(String num1, String num2) {
    if (num1.equals("0") || num2.equals("0")) return "0";
    int m = num1.length(),n = num2.length();
    int[] ans = new int[m + n];
    for (int i = m - 1; i >= 0; i--){
        for (int j = n - 1; j >= 0; j--){
            int sum = ans[i + j + 1] + (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            ans[i + j + 1] = sum % 10;
            ans[i + j] += sum /  10;
        }
    }
    StringBuilder res = new StringBuilder();
    for (int digit : ans) if (!res.isEmpty() || digit != 0) res.append(digit);
    return res.toString();
}

//Detect Squares
class DetectSquares {
    Map<Integer,Map<Integer,Integer>> map;
    public DetectSquares() {
        map = new HashMap<>();
    }

    public void add(int[] point) {
        int x = point[0],y = point[1];
        map.putIfAbsent(x,new HashMap<>());
        Map<Integer,Integer> y1 = map.get(x);
        y1.put(y,y1.getOrDefault(y,0) + 1);
    }

    public int count(int[] point) {
        int x = point[0],y = point[1],ans = 0;
        if (!map.containsKey(x)) return 0;
        for (int y2 : map.get(x).keySet()){
            if (y2 == y) continue;
            int side = Math.abs(y2 - y);
            int leftX = x - side,rightX = x + side;
            ans += map.get(x).get(y2) * getCount(leftX,y) * getCount(leftX,y2);
            ans += map.get(x).get(y2) * getCount(rightX,y) * getCount(rightX,y2);
        }
        return ans;
    }

    public int getCount(int x,int y){
        return map.getOrDefault(x,Collections.emptyMap()).getOrDefault(y,0);
    }
}

/**
 * Your DetectSquares object will be instantiated and called as such:
 * DetectSquares obj = new DetectSquares();
 * obj.add(point);
 * int param_2 = obj.count(point);
 */

void main() {
    int[][] matrix = {
            {1,  2,  3,  4},
            {5,  6,  7,  8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
    };
    rotate(matrix);
    System.out.println("Rotated Matrix:");
    for (int[] row : matrix) System.out.println(Arrays.toString(row));
    int[][] matrix2 = {
            { 1,  2,  3,  4,  5},
            { 6,  7,  8,  9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20}
    };
    List<Integer> result = spiralOrder(matrix2);
    System.out.println("Spiral Order: " + result);
    int[][] matrix3 = {
            {1, 2, 0, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 0, 13, 14, 15},
            {16, 17, 18, 19, 20}
    };
    setZeroes(matrix3);
    System.out.println("Matrix after setting zeroes:");
    for (int[] row : matrix3) System.out.println(Arrays.toString(row));
    int n = 23;
    boolean result1 = isHappy(n);
    System.out.println("Is Happy Number: " + result1);
    int[] digits = {9, 9, 9, 9};
    int[] result2 = plusOne(digits);
    System.out.println("Result: " + Arrays.toString(result2));
    double x = 2.0;
    int n1 = -10;
    double result3 = myPow(x, n1);
    System.out.println("Result: " + result3);
    String num1 = "123";
    String num2 = "478";
    String result4 = multiply(num1, num2);
    System.out.println("Product: " + result4);
    DetectSquares ds = new DetectSquares();
    ds.add(new int[]{3, 2});
    ds.add(new int[]{3, 5});
    ds.add(new int[]{0, 2});
    ds.add(new int[]{0, 5});
    ds.add(new int[]{0, 5}); // Duplicate point
    System.out.println(ds.count(new int[]{3, 2})); // 2
    System.out.println(ds.count(new int[]{3, 5})); // 2
    System.out.println(ds.count(new int[]{0, 2})); // 2
}