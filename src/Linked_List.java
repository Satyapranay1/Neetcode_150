static class ListNode{
    int val;
    ListNode next;
    ListNode(int val){
        this.val = val;
    }
}

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
void printList(ListNode node) {
    while (node != null) {
        System.out.print(node.val);
        if (node.next != null) System.out.print(" -> ");
        node = node.next;
    }
    System.out.println();
}

//Reverse a Linked List
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode prev = null,temp = head;
    while (temp != null){
        ListNode next = temp.next;
        temp.next = prev;
        prev = temp;
        temp = next;
    }
    return prev;
}

//Merge Two Sorted Linked Lists
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode helper = new ListNode(0),ans = helper;
    while (list1 != null && list2 != null){
        if (list1.val < list2.val){
            helper.next = list1;
            list1 = list1.next;
        }
        else{
            helper.next = list2;
            list2 = list2.next;
        }
        helper = helper.next;
    }

    if (list1 != null) helper.next = list1;
    if (list2 != null) helper.next = list2;
    return ans.next;
}

//Linked List Cycle
public boolean hasCycle(ListNode head) {
    ListNode slow = head,fast = head;
    while (fast != null && fast.next != null){
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}

//Reorder list
public void reorderList(ListNode head) {
    ListNode slow = head,fast = head;
    while (fast != null && fast.next != null){
        slow = slow.next;
        fast = fast.next.next;
    }
    ListNode second = slow.next;
    slow.next = null;
    ListNode prev = null;
    while (second != null){
        ListNode next = second.next;
        second.next = prev;
        prev = second;
        second = next;
    }
    second = prev;
    ListNode first = head;
    while (second != null){
        ListNode temp1 = first.next;
        ListNode temp2 = second.next;
        first.next = second;
        second.next = temp1;
        first = temp1;
        second = temp2;
    }
}

//Remove Nth node from the end
public ListNode removeNthFromEnd(ListNode head, int n) {
    if (head == null || head.next == null) return null;
    ListNode ans = new ListNode(0);
    ans.next = head;
    ListNode slow = ans,fast = ans;
    while (n-- > 0) fast = fast.next;
    while (fast.next != null){
        slow = slow.next;
        fast = fast.next;
    }
    slow.next = slow.next.next;
    return ans.next;
}

//Copy List with Random Pointer
public Node copyRandomList(Node head) {
    HashMap<Node,Node> map = new HashMap<>();
    Node temp = head;
    while (temp != null){
        Node copy = new Node(temp.val);
        map.put(temp,copy);
        temp = temp.next;
    }
    temp = head;
    while (temp != null){
        Node copy = map.get(temp);
        copy.next = map.get(temp.next);
        copy.random = map.get(temp.random);
        temp = temp.next;
    }
    return map.get(head);
}

//Add Two Numbers
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode help = new ListNode(0),ans = help;
    int carry = 0;
    while (l1 != null || l2 != null || carry == 1){
        int sum = carry;
        if (l1 != null){
            sum += l1.val;
            l1 = l1.next;
        }

        if (l2 != null){
            sum += l2.val;
            l2 = l2.next;
        }

        carry = sum / 10;
        help.next = new ListNode(sum % 10);
        help = help.next;
    }
    return ans.next;
}

//Find the Duplicate Number
public int findDuplicate(int[] nums) {
    int slow = nums[0],fast = nums[0];
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow != fast);

    fast = nums[0];
    while (slow != fast){
        slow = nums[slow];
        fast = nums[fast];
    }
    return slow;
}

class LRUCache {
    class Node{
        int key,val;
        Node prev,next;
        Node(int key,int val){
            this.key = key;
            this.val = val;
        }
    }

    Node head = new Node(-1,-1);
    Node tail = new Node(-1,-1);
    HashMap<Integer,Node> map;
    int cap;

    public void addNode(Node newNode){
        Node temp = head.next;
        newNode.next = temp;
        newNode.prev = head;
        head.next = newNode;
        temp.prev = newNode;
    }

    public void deleNode(Node delNode){
        Node delPrev = delNode.prev;
        Node delNext = delNode.next;
        delPrev.next = delNext;
        delNext.prev = delPrev;
    }
    public LRUCache(int capacity) {
        map  = new HashMap<>();
        cap = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (map.containsKey(key)){
            Node currNode = map.get(key);
            int val = currNode.val;
            map.remove(key);
            deleNode(currNode);
            addNode(currNode);
            map.put(key,head.next);
            return val;
        }

        return -1;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)){
            Node currNode = map.get(key);
            map.remove(key);
            deleNode(currNode);
        }

        if (map.size() == cap){
            Node lru = tail.prev;
            deleNode(lru);
            map.remove(lru.key);
        }

        addNode(new Node(key,value));
        map.put(key,head.next);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

void main() {
    ListNode first = new ListNode(7);
    first.next = new ListNode(12);
    first.next.next = new ListNode(4);
    first.next.next.next = new ListNode(19);
    first.next.next.next.next = new ListNode(2);

    ListNode result = reverseList(first);

    printList(result);

    ListNode list1 = new ListNode(2);
    list1.next = new ListNode(6);
    list1.next.next = new ListNode(11);
    list1.next.next.next = new ListNode(18);

    ListNode list2 = new ListNode(1);
    list2.next = new ListNode(5);
    list2.next.next = new ListNode(7);
    list2.next.next.next = new ListNode(13);
    list2.next.next.next.next = new ListNode(20);

    ListNode result1 = mergeTwoLists(list1, list2);
    printList(result1);

    ListNode first2 = new ListNode(7);
    first2.next = new ListNode(12);
    first2.next.next = new ListNode(4);
    first2.next.next.next = new ListNode(19);
    first2.next.next.next.next = new ListNode(8);

    first2.next.next.next.next.next = first2.next.next;

    System.out.println("Cycle exists: " + hasCycle(first2));

    ListNode first1 = new ListNode(11);
    first1.next = new ListNode(4);
    first1.next.next = new ListNode(18);
    first1.next.next.next = new ListNode(7);
    first1.next.next.next.next = new ListNode(25);
    first1.next.next.next.next.next = new ListNode(13);

    reorderList(first1);

    printList(first1);

    ListNode first3 = new ListNode(14);
    first3.next = new ListNode(7);
    first3.next.next = new ListNode(23);
    first3.next.next.next = new ListNode(9);
    first3.next.next.next.next = new ListNode(31);
    first3.next.next.next.next.next = new ListNode(18);

    ListNode result3 = removeNthFromEnd(first3, 4);

    printList(result3);

    Node first4 = new Node(11);
    Node second = new Node(24);
    Node third = new Node(7);
    Node fourth = new Node(35);
    Node fifth = new Node(18);

    first4.next = second;
    second.next = third;
    third.next = fourth;
    fourth.next = fifth;

    first4.random = third;
    second.random = first4;
    third.random = fifth;
    fourth.random = second;
    fifth.random = fourth;

    Node copy = copyRandomList(first4);

    while (copy != null) {
        System.out.println(
                "Value: " + copy.val +
                        ", Random: " + (copy.random == null ? "null" : copy.random.val)
        );
        copy = copy.next;
    }

    ListNode l1 = new ListNode(9);
    l1.next = new ListNode(8);
    l1.next.next = new ListNode(7);
    l1.next.next.next = new ListNode(6);

    ListNode l2 = new ListNode(5);
    l2.next = new ListNode(4);
    l2.next.next = new ListNode(9);

    ListNode result5 = addTwoNumbers(l1, l2);

    printList(result5);

    int[] nums = {4, 2, 6, 1, 5, 3, 7, 6};

    int result6 = findDuplicate(nums);

    System.out.println("Duplicate: " + result6);

    LRUCache cache = new LRUCache(3);

    cache.put(10, 100);
    cache.put(20, 200);
    cache.put(30, 300);

    System.out.println(cache.get(10));

    cache.put(40, 400);

    System.out.println(cache.get(20));
    System.out.println(cache.get(30));
    System.out.println(cache.get(40));

    cache.put(50, 500);

    System.out.println(cache.get(10));
    System.out.println(cache.get(30));
    System.out.println(cache.get(50));

    cache.put(30, 999);

    System.out.println(cache.get(30));

    cache.put(60, 600);

    System.out.println(cache.get(40));
    System.out.println(cache.get(50));
    System.out.println(cache.get(60));
}