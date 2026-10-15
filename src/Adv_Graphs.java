//Network Delay Time
public int networkDelayTime(int[][] times, int n, int k) {
    List<List<int[]>> adj = new ArrayList<>();
    for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
    for (int i = 0; i < times.length; i++) adj.get(times[i][0]).add(new int[]{times[i][1],times[i][2]});
    int[] dist = new int[n + 1];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
    Arrays.fill(dist,(int)1e9);
    dist[k] = 0;
    pq.offer(new int[]{0,k});
    while (!pq.isEmpty()){
        int[] curr = pq.poll();
        int dis = curr[0];
        int node = curr[1];
        if (dis > dist[node]) continue;
        for (int[] nei : adj.get(node)){
            int next = nei[0],wt = nei[1];
            if (dis + wt < dist[next]){
                dist[next] = dis + wt;
                pq.offer(new int[]{dist[next],next});
            }
        }
    }
    int ans = 0;
    for (int i = 1; i <= n; i++) {
        if (dist[i] == (int)1e9) return -1;
        ans = Math.max(ans,dist[i]);
    }
    return ans;
}

//Reconstruct Itinerary
public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> adj = new HashMap<>();
    for (List<String> ticket : tickets) adj.computeIfAbsent(ticket.get(0),k -> new PriorityQueue<>()).add(ticket.get(1));
    List<String> res = new ArrayList<>();
    Stack<String> st = new Stack<>();
    st.push("JFK");
    while (!st.isEmpty()){
        String curr = st.peek();
        if (!adj.containsKey(curr) || adj.get(curr).isEmpty()) res.addFirst(st.pop());
        else st.push(adj.get(curr).poll());
    }
    return res;
}

//Min Cost to Connect All Points
public int minCostConnectPoints(int[][] points) {
    int n = points.length;
    Map<Integer, List<int[]>> adj = new HashMap<>();
    for (int i = 0; i < n; i++) {
        int x1 = points[i][0], y1 = points[i][1];
        for (int j = i + 1; j < n; j++) {
            int x2 = points[j][0], y2 = points[j][1];
            int dist = Math.abs(x1 - x2) + Math.abs(y1 - y2);
            adj.computeIfAbsent(i, k -> new ArrayList<>()).add(new int[]{j, dist});
            adj.computeIfAbsent(j, k -> new ArrayList<>()).add(new int[]{i, dist});
        }
    }

    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
    Set<Integer> vis = new HashSet<>();
    pq.offer(new int[]{0, 0});
    int ans = 0;

    while (vis.size() < n) {
        int[] curr = pq.poll();
        int node = curr[0];
        int dist = curr[1];
        if (vis.contains(node)) continue;
        ans += dist;
        vis.add(node);
        for (int[] nei : adj.getOrDefault(node, Collections.emptyList())) {
            int next = nei[0];
            int dis = nei[1];
            if (!vis.contains(next)) pq.offer(new int[]{next, dis});
        }
    }
    return ans;
}

//Swim in Rising Water
public int swimInWater(int[][] grid) {
    int n = grid.length;
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
    boolean[][] vis = new boolean[n][n];
    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    pq.offer(new int[]{grid[0][0],0,0});
    vis[0][0] = true;
    while (!pq.isEmpty()){
        int[] curr = pq.poll();
        int dist = curr[0],r = curr[1],c = curr[2];
        vis[r][c] = true;
        if (r == n - 1 && c == n - 1) return dist;
        for (int[] dir : dirs){
            int nx = r + dir[0],ny = c + dir[1];
            if (nx >= 0 && ny >= 0 && nx < n && ny < n && !vis[nx][ny]) pq.offer(new int[]{Math.max(grid[nx][ny],dist),nx,ny});
        }
    }
    return n;
}

//Alien Dictionary
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> adj = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    for (String word : words) {
        for (char ch : word.toCharArray()) {
            adj.putIfAbsent(ch, new HashSet<>());
            indegree.putIfAbsent(ch, 0);
        }
    }

    for (int i = 0; i < words.length - 1; i++) {
        String first = words[i];
        String second = words[i + 1];
        int len = Math.min(first.length(), second.length());
        int j = 0;

        while (j < len && first.charAt(j) == second.charAt(j)) j++;
        if (j == len) {
            if (first.length() > second.length()) return "";
            continue;
        }
        char from = first.charAt(j);
        char to = second.charAt(j);
        if (adj.get(from).add(to)) indegree.put(to, indegree.get(to) + 1);
    }

    Queue<Character> q = new ArrayDeque<>();
    for (char ch : indegree.keySet()) if (indegree.get(ch) == 0) q.offer(ch);
    StringBuilder order = new StringBuilder();
    while (!q.isEmpty()) {
        char curr = q.poll();
        order.append(curr);
        for (char nei : adj.get(curr)) {
            indegree.put(nei, indegree.get(nei) - 1);
            if (indegree.get(nei) == 0) q.offer(nei);
        }
    }

    return order.length() == indegree.size() ? order.toString() : "";
}

//Cheapest Flight Within K stops
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    List<List<int[]>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] flight : flights) adj.get(flight[0]).add(new int[] {flight[1], flight[2]});
    Queue<int[]> q = new LinkedList<>();
    q.offer(new int[] {src, 0});
    int[] minCost = new int[n];
    Arrays.fill(minCost, Integer.MAX_VALUE);
    int stops = 0;
    while (!q.isEmpty() && stops <= k) {
        int size = q.size();
        while (size-- > 0) {
            int[] curr = q.poll();
            for (int[] neighbour : adj.get(curr[0])) {
                int price = neighbour[1], neighbourNode = neighbour[0];
                if (price + curr[1] >= minCost[neighbourNode]) continue;
                minCost[neighbourNode] = price + curr[1];
                q.offer(new int[] {neighbourNode, minCost[neighbourNode]});
            }
        }
        stops++;
    }
    return minCost[dst] == Integer.MAX_VALUE ? -1 : minCost[dst];
}

void main() {
    int n = 5;
    int k = 2;

    int[][] times = {
            {2, 1, 1},
            {2, 3, 4},
            {1, 3, 2},
            {3, 4, 1},
            {1, 4, 7},
            {4, 5, 3}
    };

    int result = networkDelayTime(times, n, k);

    System.out.println("Network Delay Time: " + result);

    List<List<String>> tickets = new ArrayList<>(List.of(
            List.of("JFK", "SFO"),
            List.of("JFK", "ATL"),
            List.of("SFO", "ATL"),
            List.of("ATL", "JFK"),
            List.of("ATL", "SFO")
    ));

    List<String> result1 = findItinerary(tickets);

    System.out.println("Itinerary: " + result1);

    int[][] points = {
            {0, 0},
            {2, 2},
            {3, 10},
            {5, 2},
            {7, 0}
    };

    int result2 = minCostConnectPoints(points);

    System.out.println("Minimum Cost: " + result2);

    int[][] grid = {
            {0,  2,  1,  3},
            {4,  7,  5,  6},
            {8,  9, 10, 11},
            {12, 13, 14, 15}
    };

    int result3 = swimInWater(grid);

    System.out.println("Minimum Time Required: " + result3);

    String[] words = {"wrt", "wrf", "er", "ett", "rftt"};

    String result4 = alienOrder(words);

    System.out.println("Alien Dictionary Order: " + result4);

    int n1 = 10;

    int[][] flights = {
            {0, 1, 100},
            {0, 2, 300},
            {1, 2, 50},
            {1, 3, 200},
            {2, 3, 100},
            {2, 4, 150},
            {3, 5, 100},
            {4, 5, 50},
            {4, 6, 200},
            {5, 6, 100},
            {5, 7, 250},
            {6, 8, 100},
            {7, 8, 50},
            {7, 9, 300},
            {8, 9, 100}
    };

    int src = 0;
    int dst = 9;
    int k1 = 4;

    int result5 = findCheapestPrice(n1, flights, src, dst, k1);

    System.out.println("Cheapest Flight Cost: " + result5);
}