//Insert Interval
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> ans = new ArrayList<>();
    int i = 0,n = intervals.length;
    while (i < n && intervals[i][1] < newInterval[0]) ans.add(intervals[i++]);
    while (i < n && intervals[i][0] <= newInterval[1]){
        newInterval[0] = Math.min(newInterval[0],intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1],intervals[i++][1]);
    }
    ans.add(newInterval);
    while (i < n) ans.add(intervals[i++]);
    return ans.toArray(new int[0][]);
}

//Merge Intervals
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    List<int[]> ans = new ArrayList<>();
    for (int[] in : intervals) {
        if (ans.isEmpty() || ans.getLast()[1] < in[0]) ans.add(in);
        else ans.getLast()[1] = Math.max(ans.getLast()[1], in[1]);
    }
    return ans.toArray(new int[0][]);
}

//Non Overlapping Intervals
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    int prev = intervals[0][1],ans = 0;
    for (int i = 1; i < intervals.length; i++){
        if (intervals[i][0] >= prev) prev = intervals[i][1];
        ans++;
        prev = Math.min(prev,intervals[i][1]);
    }
    return ans;
}

//Meeting Rooms
public boolean canAttendMeetings(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    for (int i = 1; i < intervals.length; i++) if (intervals[i - 1][1] > intervals[i][0]) return false;
    return true;
}

//Meeting Rooms - II
public int minMeetingRooms(int[][] intervals) {
    int n = intervals.length;
    int[] start = new int[n], end = new int[n];
    for (int i = 0; i < n; i++) {
        start[i] = intervals[i][0];
        end[i] = intervals[i][1];
    }
    Arrays.sort(start);
    Arrays.sort(end);
    int rooms = 0, maxRooms = 0, j = 0;
    for (int i = 0; i < n; i++) {
        if (start[i] < end[j]) rooms++;
        rooms--;
        j++;
        rooms++;
        maxRooms = Math.max(maxRooms, rooms);
    }
    return maxRooms;
}

//Minimum Interval to Include in Each query
public int[] minInterval(int[][] intervals, int[] queries) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    int n = queries.length;
    int[] ans = new int[n];
    int[][] sortedQueries = new int[n][2];
    for (int i = 0; i < n; i++) {
        sortedQueries[i][0] = queries[i];
        sortedQueries[i][1] = i;
    }
    Arrays.sort(sortedQueries, Comparator.comparingInt(a -> a[0]));
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
    int idx = 0;
    for (int[] query : sortedQueries) {
        int curr = query[0];
        int originalIdx = query[1];
        while (idx < intervals.length && intervals[idx][0] <= curr) {
            int left = intervals[idx][0];
            int right = intervals[idx][1];
            int len = right - left + 1;
            pq.offer(new int[]{len, right});
            idx++;
        }
        while (!pq.isEmpty() && pq.peek()[1] < curr) pq.poll();
        ans[originalIdx] = pq.isEmpty() ? -1 : pq.peek()[0];
    }
    return ans;
}

void main() {
    int[][] intervals = {{1, 3},{6, 9}};
    int[] newInterval = {2, 5};
    int[][] result = insert(intervals, newInterval);
    System.out.println("Merged Intervals:");
    for (int[] interval : result) System.out.print(Arrays.toString(interval) + ", ");
    System.out.println();
    int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}, {17, 20}};
    int[][] result1 = merge(intervals1);
    System.out.println("Merged Intervals:");
    for (int[] interval : result1) System.out.print(Arrays.toString(interval) + ",");
    int[][] intervals2 = {{1, 4}, {2, 3}, {3, 5}, {4, 6}, {6, 8}, {7, 9}};
    int result2 = eraseOverlapIntervals(intervals2);
    System.out.println("Minimum Intervals to Remove: " + result2);
    int[][] meetings1 = {{0, 30}, {5, 10}, {15, 20}};
    System.out.println("Can attend all the meetings : " + canAttendMeetings(meetings1));
    int[][] meetings3 = {{1, 5}, {2, 6}, {3, 7}, {8, 9}};
    System.out.println("Number of meeting rooms required: " + minMeetingRooms(meetings3));
    int[][] intervals3 = {{2, 7}, {4, 5}, {1, 4}, {6, 9}, {10, 15}, {3, 12}};
    int[] queries = {4, 8, 1, 11, 14, 16, 6};
    int[] result3 = minInterval(intervals3, queries);
    System.out.println("Minimum Interval Lengths: " + Arrays.toString(result3));
}