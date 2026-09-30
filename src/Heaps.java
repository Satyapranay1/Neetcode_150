import java.util.PriorityQueue;

//Kth Largest Element in a Stream
static class KthLargest {
    PriorityQueue<Integer> pq;
    static int k;
    public KthLargest(int k, int[] nums) {
        pq = new PriorityQueue<>();
        KthLargest.k = k;
        for (int num : nums){
            pq.offer(num);
            while (pq.size() > k) pq.poll();
        }
    }

    public int add(int val) {
        pq.offer(val);
        if (pq.size() > k) pq.poll();
        return pq.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */


//Last Stone Weight
public int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
    for (int stone : stones) pq.offer(stone);
    while (pq.size() > 1){
        int a = pq.poll();
        int b = pq.poll();
        if (a != b) pq.offer(a - b);
    }
    return pq.isEmpty() ? 0 : pq.poll();
}

//K closest Points to Origin
public int[][] kClosest(int[][] points, int k) {
    int[][] ans = new int[k][2];
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[0] * a[0] + a[1] * a[1]));
    for (int[] point : points) pq.offer(point);
    for (int i = 0; i < k; i++){
        ans[i][0] = pq.peek()[0];
        ans[i][1] = pq.peek()[1];
        pq.poll();
    }
    return ans;
}

//Kth Largest Element in an Array
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int num : nums){
        if (pq.size() < k) pq.offer(num);
        else{
            if (pq.peek() < num){
                pq.poll();
                pq.offer(num);
            }
        }
    }
    return pq.peek();
}

//Task Scheduler
public int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char ch : tasks) freq[ch - 'A']++;
    int maxFreq = 0,m = tasks.length,ct = 0;
    for (int el : freq) maxFreq = Math.max(maxFreq,el);
    for (int el : freq) if (maxFreq == el) ct++;
    return Math.max((maxFreq - 1) * (n + 1) + ct,m);
}

public int leastInterval1(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char el : tasks) freq[el - 'A']++;
    PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
    for (int count : freq) if (count > 0) pq.offer(count);
    int time = 0;
    while (!pq.isEmpty()){
        List<Integer> wait = new ArrayList<>();
        int slots = n + 1;
        while (slots > 0 && !pq.isEmpty()){
            int count = pq.poll();
            count--;
            if (count > 0) wait.add(count);
            slots--;
            time++;
        }
        for (int ct : wait) pq.offer(ct);
        if (!pq.isEmpty()) time += slots;
    }
    return time;
}

//Design Twitter
static class Twitter {
    record Tweet(int tweetId,int time){}
    record TweetNode(int userId,int idx){}
    int time;
    HashMap<Integer,Set<Integer>> following;
    HashMap<Integer,List<Tweet>> tweets;

    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId,new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId,time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> ans = new ArrayList<>();
        var pq = new PriorityQueue<TweetNode>(
                (a, b) -> { Tweet tweetA = tweets.get(a.userId()).get(a.idx());
                    Tweet tweetB = tweets.get(b.userId()).get(b.idx());
                    return Integer.compare(tweetB.time(),tweetA.time());
                });
        Set<Integer> users = new HashSet<>();
        users.add(userId);
        if (following.containsKey(userId)) users.addAll(following.get(userId));
        for (int user : users){
            List<Tweet> userTweets = tweets.get(user);
            if (userTweets != null && !userTweets.isEmpty()){
                int lidx = userTweets.size() - 1;
                pq.offer(new TweetNode(user,lidx));
            }
        }
        while (!pq.isEmpty() && ans.size() < 10){
            TweetNode node = pq.poll();
            Tweet tweet = tweets.get(node.userId()).get(node.idx());
            ans.add(tweet.tweetId);
            if (node.idx() > 0) pq.offer(new TweetNode(node.userId,node.idx - 1));
        }
        return ans;
    }

    public void follow(int followerId, int followeeId) {
        following.putIfAbsent(followerId,new HashSet<>());
        following.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId)) following.get(followerId).remove(followeeId);
    }
}

        /**
         * Your Twitter object will be instantiated and called as such:
         * Twitter obj = new Twitter();
         * obj.postTweet(userId,tweetId);
         * List<Integer> param_2 = obj.getNewsFeed(userId);
         * obj.follow(followerId,followeeId);
         * obj.unfollow(followerId,followeeId);
         */

//Find Median from Data Stream
public static class MedianFinder {

    private final Queue<Integer> smallHeap;
    private final Queue<Integer> largeHeap;

    public MedianFinder() {
        largeHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a));
        smallHeap = new PriorityQueue<>((a, b) -> b - a);
    }

    public void addNum(int num) {
        smallHeap.add(num);
        if (smallHeap.size() - largeHeap.size() > 1 || !largeHeap.isEmpty() && smallHeap.peek() > largeHeap.peek()) largeHeap.add(smallHeap.poll());
        if (largeHeap.size() - smallHeap.size() > 1) smallHeap.add(largeHeap.poll());
    }

    public double findMedian() {
        if (smallHeap.size() == largeHeap.size()) return (double) (largeHeap.peek() + smallHeap.peek()) / 2;
        else if (smallHeap.size() > largeHeap.size()) return (double) smallHeap.peek();
        else return (double) largeHeap.peek();
    }
}

        /**
         * Your MedianFinder object will be instantiated and called as such:
         * MedianFinder obj = new MedianFinder();
         * obj.addNum(num);
         * double param_2 = obj.findMedian();
         */
void main() {
    int[] nums = {12, 7, 19, 4, 15, 22};

    KthLargest obj = new KthLargest(3, nums);

    System.out.println(obj.add(10));
    System.out.println(obj.add(25));
    System.out.println(obj.add(6));
    System.out.println(obj.add(18));

    int[] stones = {17, 9, 23, 14, 8, 31, 12, 6};

    int result = lastStoneWeight(stones);

    System.out.println("Last Stone Weight: " + result);

    int[][] points = {
            {7, 2},
            {-3, 4},
            {1, 1},
            {6, -5},
            {-2, 0},
            {4, 3}
    };

    int k = 3;

    int[][] result1 = kClosest(points, k);

    for (int[] point : result1) System.out.println(Arrays.toString(point));
    int[] nums4 = {18, 7, 25, 11, 30, 14, 21, 9, 27};
    int k1 = 4;

    int result2 = findKthLargest(nums4, k1);

    System.out.println("Kth Largest: " + result2);

    char[] tasks = {
            'A', 'A', 'A', 'A',
            'B', 'B', 'B',
            'C', 'C',
            'D', 'D',
            'E'
    };

    int n = 2;

    int result4 = leastInterval(tasks, n);

    System.out.println("Minimum Intervals: " + result4);

    char[] tasks1 = {
            'A', 'A', 'A', 'A',
            'B', 'B', 'B',
            'C', 'C',
            'D', 'D',
            'E'
    };

    int n1 = 2;

    int result5 = leastInterval1(tasks1, n1);

    System.out.println("Minimum Intervals: " + result5);

    Twitter twitter = new Twitter();

    twitter.postTweet(1, 101);
    twitter.postTweet(2, 201);
    twitter.postTweet(3, 301);

    twitter.follow(1, 2);
    twitter.follow(1, 3);

    twitter.postTweet(2, 202);
    twitter.postTweet(1, 102);
    twitter.postTweet(3, 302);
    twitter.postTweet(2, 203);

    System.out.println("Feed after following 2 and 3:");
    System.out.println(twitter.getNewsFeed(1));

    twitter.unfollow(1, 2);

    System.out.println("Feed after unfollowing 2:");
    System.out.println(twitter.getNewsFeed(1));

    twitter.postTweet(3, 303);
    twitter.postTweet(1, 103);

    System.out.println("Feed after new tweets:");
    System.out.println(twitter.getNewsFeed(1));

    MedianFinder mf = new MedianFinder();

    int[] nums6 = {17, 4, 29, 11, 23, 8, 35, 14};

    for (int num : nums6) {
        mf.addNum(num);
        System.out.println("Added: " + num + " -> Median: " + mf.findMedian());
    }
}