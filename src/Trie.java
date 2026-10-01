//Implement Trie
class Node{
    Node[] children = new Node[26];
    boolean end = false;
}

class Node1{
    Node1[] child = new Node1[26];
    String word;
}
class Trie {
    Node root;
    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node curr = root;
        for (char c : word.toCharArray()){
            if (curr.children[c - 'a'] == null) curr.children[c - 'a'] = new Node();
            curr = curr.children[c - 'a'];
        }
        curr.end = true;
    }

    public boolean search(String word) {
        Node curr = root;
        for (char c : word.toCharArray()){
            if (curr.children[c - 'a'] == null) return false;
            curr = curr.children[c - 'a'];
        }
        return curr.end;
    }

    public boolean startsWith(String prefix) {
        Node curr = root;
        for (char c : prefix.toCharArray()){
            if (curr.children[c - 'a'] == null) return false;
            curr = curr.children[c - 'a'];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

//Word Search - II
public List<String> findWords(char[][] board, String[] words) {
    Node1 root = new Node1();
    for (String word : words){
        Node1 curr = root;
        for (char c : word.toCharArray()){
            if (curr.child[c - 'a'] == null) curr.child[c - 'a'] = new Node1();
            curr = curr.child[c - 'a'];
        }
        curr.word = word;
    }

    List<String> ans = new ArrayList<>();
    for (int i = 0; i < board.length; i++){
        for (int j = 0; j < board[0].length; j++){
            dfs(board,ans,i,j,root);
        }
    }
    return ans;
}

public void dfs(char[][] board,List<String> ans,int i,int j,Node1 root){
    if (i < 0 || j < 0 || i == board.length || j == board[0].length || board[i][j] == '#') return;
    char c = board[i][j];
    Node1 next = root.child[c - 'a'];
    if (next == null) return;
    if (next.word != null){
        ans.add(next.word);
        next.word = null;
    }

    board[i][j] = '#';
    dfs(board,ans,i + 1,j,next);
    dfs(board,ans,i - 1,j,next);
    dfs(board,ans,i,j + 1,next);
    dfs(board,ans,i,j - 1,next);
    board[i][j] = c;
}

void main() {
    Trie trie = new Trie();

    trie.insert("code");
    trie.insert("coder");
    trie.insert("coding");
    trie.insert("cat");
    trie.insert("car");

    System.out.println("search(code): " + trie.search("code"));
    System.out.println("search(cod): " + trie.search("cod"));
    System.out.println("search(coder): " + trie.search("coder"));
    System.out.println("search(coding): " + trie.search("coding"));
    System.out.println("search(codes): " + trie.search("codes"));

    System.out.println("startsWith(co): " + trie.startsWith("co"));
    System.out.println("startsWith(cod): " + trie.startsWith("cod"));
    System.out.println("startsWith(codi): " + trie.startsWith("codi"));
    System.out.println("startsWith(ca): " + trie.startsWith("ca"));
    System.out.println("startsWith(x): " + trie.startsWith("x"));


    char[][] board = {
            {'s', 'e', 'a', 't'},
            {'r', 'a', 'd', 'o'},
            {'t', 'e', 'a', 'm'},
            {'p', 'l', 'n', 'k'}
    };

    String[] words = {
            "sea",
            "seat",
            "sead",
            "team",
            "read"
    };

    List<String> result = findWords(board, words);

    System.out.println("Words found:");
    for (String word : result) {
        System.out.println(word);
    }
}