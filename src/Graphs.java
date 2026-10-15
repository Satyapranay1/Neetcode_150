class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
//Number of Islands
public int numIslands(char[][] grid) {
    int m = grid.length,n = grid[0].length,ans = 0;
    boolean[][] vis = new boolean[m][n];
    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if (grid[i][j] == '1' && !vis[i][j]){
                ans++;
                dfs(grid,vis,i,j);
            }
        }
    }
    return ans;
}

public void dfs(char[][] grid, boolean[][] vis, int i, int j) {
    if (i < 0 || j < 0 || i == grid.length || j == grid[0].length || grid[i][j] == '0' || vis[i][j]) return;
    vis[i][j] = true;
    dfs(grid, vis, i + 1, j);
    dfs(grid, vis, i - 1, j);
    dfs(grid, vis, i, j + 1);
    dfs(grid, vis, i, j - 1);
}

//Max Area of a Island
public int maxAreaOfIsland(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int max = 0;
    for (int i = 0; i  < m; i++){
        for (int j = 0; j < n; j++){
            int[] count = new int[1];
            if (grid[i][j] == 1){
                dfs(grid,i,j,count);
                max = Math.max(max,count[0]);
            }
        }
    }
    return max;
}

public void dfs(int[][] grid,int i,int j,int[] count){
    if (i >= grid.length || i < 0 || j >= grid[0].length || j < 0 || grid[i][j] == 0) return ;
    grid[i][j] = 0;
    count[0]++;
    dfs(grid,i + 1,j,count);
    dfs(grid,i,j + 1,count);
    dfs(grid,i - 1,j,count);
    dfs(grid,i,j - 1,count);
}

//Clone Graph
public Node cloneGraph(Node node) {
    if (node == null) return null;
    HashMap<Integer,Node> map = new HashMap<>();
    return dfs(map,node);
}

public Node dfs(HashMap<Integer,Node> map,Node node){
    if (node == null) return null;
    if (map.containsKey(node.val)) return map.get(node.val);
    Node clone = new Node(node.val);
    map.put(node.val,clone);
    for (Node x : node.neighbors) clone.neighbors.add(dfs(map,x));
    return clone;
}

void printGraph(Node node, HashSet<Integer> visited) {
    if (node == null || visited.contains(node.val)) return;

    visited.add(node.val);

    System.out.print(node.val + " -> ");
    for (Node neighbor : node.neighbors) {
        System.out.print(neighbor.val + " ");
    }
    System.out.println();

    for (Node neighbor : node.neighbors) {
        printGraph(neighbor, visited);
    }
}

//Walls and Gates
public void islandsAndTreasure(int[][] grid) {
    Queue<int[]> q = new LinkedList<>();
    int m = grid.length;
    int n = grid[0].length;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 0) q.add(new int[] { i, j });
        }
    }
    if (q.isEmpty()) return;
    int[][] dirs = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
    while (!q.isEmpty()) {
        int[] node = q.poll();
        int row = node[0];
        int col = node[1];
        for (int[] dir : dirs) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (r >= m || c >= n || r < 0 || c < 0 || grid[r][c] != Integer.MAX_VALUE) continue;
            q.add(new int[] { r, c });
            grid[r][c] = grid[row][col] + 1;
        }
    }
}

//Roting Oranges
public int orangesRotting(int[][] grid) {
    int fresh = 0,count = -1;
    int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    Queue<int[]> q = new LinkedList<>();
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == 1) fresh++;
            if (grid[i][j] == 2) q.offer(new int[] { i, j });
        }
    }
    if (fresh == 0) return 0;
    if (q.isEmpty()) return -1;
    while (!q.isEmpty()) {
        int size = q.size();
        while (size-- > 0) {
            int[] curr = q.poll();
            int x = curr[0], y = curr[1];
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[0].length && grid[nx][ny] == 1) {
                    fresh--;
                    grid[nx][ny] = 2;
                    q.offer(new int[] { nx, ny });
                }
            }
        }
        count++;
    }
    return fresh > 0 ? -1 : count;
}

//Pacific Atlantic Water Flow
public List<List<Integer>> pacificAtlantic(int[][] heights) {
    List<List<Integer>> ans = new ArrayList<>();
    int m = heights.length;
    int n = heights[0].length;
    boolean[][] pac = new boolean[m][n];
    boolean[][] atl = new boolean[m][n];

    for (int i = 0; i < n; i++){
        dfs(0,i,pac,heights[0][i],heights);
        dfs(m - 1,i,atl,heights[m - 1][i],heights);
    }

    for (int i = 0; i < m; i++){
        dfs(i,0,pac,heights[i][0],heights);
        dfs(i,n - 1,atl,heights[i][n - 1],heights);
    }


    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++)if (pac[i][j] && atl[i][j]) ans.add(Arrays.asList(i,j));
    }
    return ans;
}

public void dfs(int row,int col,boolean[][] vis,int prev,int[][] height){
    if (row < 0 || row >= height.length || col < 0 || col >= height[0].length || vis[row][col] || prev > height[row][col]) return;
    vis[row][col] = true;
    dfs(row + 1,col,vis,height[row][col],height);
    dfs(row - 1,col,vis,height[row][col],height);
    dfs(row,col + 1,vis,height[row][col],height);
    dfs(row,col - 1,vis,height[row][col],height);
}

//Surrounded Regions
public void solve(char[][] board) {
    int m = board.length,n = board[0].length;
    for (int i = 0; i < n; i++){
        if (board[0][i] == 'O') dfs(0,i,board);
        if (board[m - 1][i] == 'O') dfs(m - 1,i,board);
    }

    for (int i = 0; i < m; i++){
        if (board[i][0] == 'O') dfs(i,0,board);
        if (board[i][n - 1] == 'O') dfs(i,n - 1,board);
    }

    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if (board[i][j] == 'O') board[i][j] = 'X';
            if (board[i][j] == 'P') board[i][j] = 'O';
        }
    }
}

public void dfs(int i,int j,char[][] board){
    if (i < 0 || j < 0 || i == board.length || j == board[0].length || board[i][j] != 'O') return;
    board[i][j] = 'P';
    dfs(i + 1, j, board);
    dfs(i - 1, j, board);
    dfs(i, j - 1, board);
    dfs(i, j + 1, board);
}

//Course Schedule
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<>();
    int[] indegree = new int[numCourses];
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
    for (int[] req : prerequisites){
        adj.get(req[1]).add(req[0]);
        indegree[req[0]]++;
    }
    for (int i = 0; i < indegree.length; i++) if (indegree[i] == 0) q.offer(i);
    while (!q.isEmpty()){
        int curr = q.poll();
        numCourses--;
        for (int nei : adj.get(curr)){
            indegree[nei]--;
            if (indegree[nei] == 0) q.offer(nei);
        }
    }
    return numCourses == 0;
}

//Course Schedule - II
public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<>();
    int[] indegree = new int[numCourses];
    Queue<Integer> q = new LinkedList<>();
    List<Integer> ans = new ArrayList<>();

    for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());

    for (int[] preq : prerequisites) {
        int curr = preq[0], prev = preq[1];
        adj.get(prev).add(curr);
        indegree[curr]++;
    }

    for (int i = 0; i < numCourses; i++) if (indegree[i] == 0) q.offer(i);

    while (!q.isEmpty()) {
        int curr = q.poll();
        ans.add(curr);
        for (int nei : adj.get(curr)) {
            indegree[nei]--;
            if (indegree[nei] == 0) q.offer(nei);
        }
    }

    if (ans.size() != numCourses) return new int[0];

    return ans.stream().mapToInt(Integer::intValue).toArray();
}

//Graph Valid Tree
public boolean validTree(int n, int[][] edges) {
    if (edges.length > n - 1) return false;

    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

    for (int[] edge : edges) {
        adj.get(edge[0]).add(edge[1]);
        adj.get(edge[1]).add(edge[0]);
    }

    Set<Integer> visit = new HashSet<>();
    Queue<int[]> q = new LinkedList<>();
    q.offer(new int[]{0, -1});
    visit.add(0);

    while (!q.isEmpty()) {
        int[] pair = q.poll();
        int node = pair[0], parent = pair[1];
        for (int nei : adj.get(node)) {
            if (nei == parent) continue;
            if (visit.contains(nei)) return false;
            visit.add(nei);
            q.offer(new int[]{nei, node});
        }
    }

    return visit.size() == n;
}

//Strongly Connected Components in a Undirected Graph
public int countComponents(int n, int[][] edges) {
    List<List<Integer>> adj = new ArrayList<>();

    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

    for (int[] edge : edges) {
        adj.get(edge[0]).add(edge[1]);
        adj.get(edge[1]).add(edge[0]);
    }

    boolean[] vis = new boolean[n];
    int count = 0;

    for (int i = 0; i < n; i++) {
        if (!vis[i]) {
            count++;
            dfs(i, adj, vis);
        }
    }

    return count;
}

public void dfs(int node, List<List<Integer>> adj, boolean[] vis) {
    vis[node] = true;
    for (int nei : adj.get(node)) if (!vis[nei]) dfs(nei, adj, vis);
}

//Redundant Connection
public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
    for (int[] edge : edges){
        adj.get(edge[0]).add(edge[1]);
        adj.get(edge[1]).add(edge[0]);
    }

    for (int i = n - 1; i >= 0; i--){
        int u = edges[i][0],v = edges[i][1];
        adj.get(u).remove(Integer.valueOf(v));
        adj.get(v).remove(Integer.valueOf(u));
        boolean[] vis = new boolean[n + 1];
        if (dfs(adj,vis,u,v)) return edges[i];
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
    return new int[]{};
}

public boolean dfs(List<List<Integer>> adj,boolean[] vis,int node,int target){
    if (node == target) return true;
    vis[node] = true;
    for (int nei : adj.get(node)) if (!vis[nei] && dfs(adj,vis,nei,target)) return true;
    return false;
}

//Word Ladder
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> words = new HashSet<>(wordList);
    if (!words.contains(endWord)) return 0;

    Queue<String> q = new ArrayDeque<>();
    q.offer(beginWord);
    words.remove(beginWord);

    int count = 1;

    while (!q.isEmpty()) {
        int size = q.size();

        while (size-- > 0) {
            String word = q.poll();

            if (word.equals(endWord)) return count;

            char[] arr = word.toCharArray();

            for (int i = 0; i < arr.length; i++) {
                char original = arr[i];

                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (ch == original) continue;
                    arr[i] = ch;
                    String next = new String(arr);
                    if (words.remove(next)) q.offer(next);
                }

                arr[i] = original;
            }
        }
        count++;
    }
    return 0;
}
void main() {
    char[][] grid = {
            {'1', '1', '0', '0', '0', '1'},
            {'1', '0', '0', '1', '1', '1'},
            {'0', '0', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '1', '1'},
            {'0', '1', '0', '0', '1', '0'}
    };

    int result = numIslands(grid);

    System.out.println("Number of Islands: " + result);

    int[][] grid1 = {
            {1, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 1, 1},
            {0, 0, 1, 0, 0, 0},
            {1, 1, 0, 0, 1, 1},
            {0, 1, 0, 0, 1, 0}
    };

    int result1 = maxAreaOfIsland(grid1);

    System.out.println("Maximum Area of Island: " + result1);

    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);

    node1.neighbors.add(node2);
    node1.neighbors.add(node4);

    node2.neighbors.add(node1);
    node2.neighbors.add(node3);

    node3.neighbors.add(node2);
    node3.neighbors.add(node4);

    node4.neighbors.add(node1);
    node4.neighbors.add(node3);

    Node cloned = cloneGraph(node1);

    System.out.println("Original graph:");
    printGraph(node1, new HashSet<>());

    System.out.println("\nCloned graph:");
    printGraph(cloned, new HashSet<>());

    int INF = Integer.MAX_VALUE;

    int[][] grid2 = {
            {INF, -1,  0, INF, INF},
            {INF, INF, INF, -1, INF},
            {INF, -1, INF, -1, INF},
            {0,  -1, INF, INF, INF},
            {INF, INF, INF, -1, INF}
    };

    islandsAndTreasure(grid2);

    for (int[] row : grid2) System.out.println(Arrays.toString(row));

    int[][] grid3 = {
            {2, 1, 0, 2, 1},
            {1, 0, 1, 2, 1},
            {1, 0, 0, 2, 1},
            {0, 1, 1, 1, 1}
    };

    int result2 = orangesRotting(grid3);

    System.out.println("Minimum minutes: " + result2);

    int[][] heights = {
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
    };

    List<List<Integer>> result3 = pacificAtlantic(heights);

    System.out.println("Cells that can reach both oceans:");
    System.out.println(result3);

    char[][] board = {
            {'X', 'O', 'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X', 'O', 'X'},
            {'X', 'X', 'O', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X', 'O', 'X'},
            {'X', 'O', 'O', 'O', 'X', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X'}
    };

    solve(board);

    for (char[] row : board) System.out.println(Arrays.toString(row));

    int numCourses = 6;

    int[][] prerequisites = {
            {1, 0},
            {2, 0},
            {3, 1},
            {3, 2},
            {4, 3},
            {5, 4}
    };

    boolean result4 = canFinish(numCourses, prerequisites);

    System.out.println("Can finish all courses: " + result4);

    int numCourses1 = 6;

    int[][] prerequisites1 = {
            {2, 0},
            {2, 1},
            {3, 1},
            {4, 2},
            {4, 3},
            {5, 4}
    };

    int[] result5 = findOrder(numCourses1, prerequisites1);

    System.out.println("Course order: " + Arrays.toString(result5));

    int n = 5;
    int[][] edges = {
            {0, 1},
            {1, 2},
            {2, 3},
            {3, 0}
    };

    System.out.println("Is valid tree: " + validTree(n, edges));

    int n1 = 5;
    int[][] edges1 = {
            {0, 1},
            {1, 2},
            {3, 4}
    };

    System.out.println("No.of strongly connected components : " + countComponents(n1, edges1));

    int[][] edges2 = {
            {1, 2},
            {1, 3},
            {2, 3}
    };

    int[] result6 = findRedundantConnection(edges2);

    System.out.println("Redundant edge: " + Arrays.toString(result6));

    String beginWord = "hit";
    String endWord = "cog";

    List<String> wordList = new ArrayList<>(List.of(
            "hot", "dot", "dog", "lot", "log", "cog"
    ));

    int result7 = ladderLength(beginWord, endWord, wordList);

    System.out.println("Shortest transformation length: " + result7);
}