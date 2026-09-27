//Valid Parentheses
public boolean isValid(String s) {
    Stack<Character> st = new Stack<>();
    for (char c : s.toCharArray()){
        if (c == '(') st.push(')');
        else if (c == '[') st.push(']');
        else if (c == '{') st.push('}');
        else if (st.isEmpty() || st.pop() != c) return false;
    }
    return st.isEmpty();
}

//Min_Stack
class MinStack {
    Stack<int[]> st;
    int min;
    public MinStack() {
        st = new Stack<>();
        min = Integer.MAX_VALUE;
    }

    public void push(int value) {
        if (min >= value) min = value;
        st.push(new int[]{value,min});
    }

    public void pop() {
        st.pop();
        min = (st.isEmpty()) ? Integer.MAX_VALUE : st.peek()[1];
    }

    public int top() {
        return st.peek()[0];
    }

    public int getMin() {
        return st.peek()[1];
    }
}

//Evaluate Reverse Polish Notation
public int evalRPN(String[] tokens) {
    Stack<Integer> st1 = new Stack<>();
    for (String s : tokens){
        if (s.equals("+")) st1.push(st1.pop() + st1.pop());
        else if (s.equals("-")) st1.push(-(st1.pop() - st1.pop()));
        else if (s.equals("*")) st1.push(st1.pop() * st1.pop());
        else if (s.equals("/")){
            int val1 = st1.pop();
            int val2 = st1.pop();
            st1.push(val2 / val1);
        }
        else st1.push(Integer.parseInt(s));
    }
    return st1.pop();
}

//Daily Temperatures
public int[] dailyTemperatures(int[] temperatures) {
    Stack<Integer> st = new Stack<>();
    int n = temperatures.length;
    int[] ans = new int[n];
    for (int i = n - 1; i >= 0; i--){
        while (!st.isEmpty() && temperatures[st.peek()] <= temperatures[i]) st.pop();
        ans[i] = (st.isEmpty() ? 0 : st.peek() - i);
        st.push(i);
    }
    return ans;
}

//Car Fleet
public int carFleet(int target, int[] position, int[] speed) {
    int n = position.length;
    int[][] cars = new int[n][2];
    for (int i = 0; i < n; i++){
        cars[i][0] = position[i];
        cars[i][1] = speed[i];
    }
    int ans = 0;
    double fleet = 0;
    Arrays.sort(cars,(a,b) -> Integer.compare(b[0],a[0]));
    for (int[] car : cars) {
        double time = (double) (target - car[0]) / car[1];
        if (time > fleet) {
            fleet = time;
            ans++;
        }
    }
    return ans;
}

//Largest Rectangle in Histogram
public int largestRectangleArea(int[] heights) {
    if (heights.length == 1) return heights[0];
    int[] nse = new int[heights.length];
    int[] pse = new int[heights.length];
    Stack<Integer> st = new Stack<>();
    for (int i = 0; i < heights.length; i++){
        while (!st.isEmpty() && heights[st.peek()] >= heights[i]) st.pop();
        pse[i] = (st.isEmpty() ? 0 : st.peek() + 1);
        st.push(i);
    }
    st.clear();

    for (int i = heights.length - 1; i >= 0; i--){
        while (!st.isEmpty() && heights[st.peek()] >= heights[i]) st.pop();
        nse[i] = (st.isEmpty() ? heights.length - 1 : st.peek() - 1);
        st.push(i);
    }
    int max = 0;
    for (int i = 0; i < heights.length; i++) max = Math.max(max,(nse[i] - pse[i] + 1) * heights[i]);
    return max;
}

void main() {
    String s = "{[()]}";

    boolean result = isValid(s);

    System.out.println("Valid Parentheses: " + result);

    MinStack stack = new MinStack();

    // Test 1: Normal values
    stack.push(5);
    stack.push(3);
    stack.push(7);
    stack.push(2);
    stack.push(4);

    System.out.println("Top: " + stack.top());       // 4
    System.out.println("Min: " + stack.getMin());   // 2

    // Remove top
    stack.pop();
    System.out.println("Top: " + stack.top());      // 2
    System.out.println("Min: " + stack.getMin());   // 2

    // Remove current minimum
    stack.pop();
    System.out.println("Top: " + stack.top());      // 7
    System.out.println("Min: " + stack.getMin());   // 3

    // Test 2: Duplicate minimum
    stack.push(3);
    stack.push(3);

    System.out.println("Min: " + stack.getMin());   // 3

    stack.pop();
    stack.pop();

    System.out.println("Min: " + stack.getMin());   // 3

    String[] tokens = {
            "10", "6", "9", "3", "+", "-11", "*",
            "/", "*", "17", "+", "5", "+"
    };

    int result1 = evalRPN(tokens);

    System.out.println("RPN Result: " + result1);

    int[] temperatures = {
            73, 74, 75, 71, 69, 72, 76, 73
    };

    int[] result2 = dailyTemperatures(temperatures);

    System.out.println("Days Until Warmer Temperature: " + Arrays.toString(result2));

    int target = 30;

    int[] position = {26, 20, 18, 15, 10, 6, 2};
    int[] speed = {2, 5, 3, 4, 2, 6, 7};

    int result3 = carFleet(target, position, speed);

    System.out.println("Number of Car Fleets: " + result3);

    int[] heights = {4, 4, 1, 3, 3, 3, 2, 5};

    int result4 = largestRectangleArea(heights);

    System.out.println("Largest Rectangle Area: " + result4);
}