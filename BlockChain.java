import java.security.NoSuchAlgorithmException;


public class BlockChain {
    Node first;
    Node last;
    int chainLen; 
    
    static class Node {
        Block blk ;
        Node next;
        
        public Node(Block blk, Node next) {
            this.blk = blk;
            this.next = next; 
        }
    }
    
    public BlockChain(int initial) throws NoSuchAlgorithmException{
        this.chainLen = 0;
        this.first = new Node(new Block(chainLen, initial, null), null);
        chainLen++; 
    }
    
    /**
     * Mines a new candidate block to be added to the list
     * The returned Block should be valid to add onto this list
     * @param amount, an integer
     * @return a Block
     * @throws NoSuchAlgorithmException 
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        return new Block(chainLen, amount, last.blk.getHash());
    }
    
    /**
     * Returns the size of the blockchain
     * @return an integer
     */
    public int getSize() {
        return chainLen; 
    }
    
    /**
     * Adds this block to the list
     * @throws IllegalArgumentException
     * @param blk, a Block
     */
    public void append(Block blk) throws IllegalArgumentException{
        this.last.next = new Node(blk, null);
        this.last = this.last.next;
        chainLen++;
    }
    
    /**
     * Removes the last block from the chain, returning true
     * If the chain only contains a single block, then removeLast does nothing and returns false
     * @return a boolean
     */
    public boolean removeLast() {
        if (chainLen == 1) {
            return false;
        }else {
            Node cur = this.first;
            while (cur.next != this.last){
                cur = cur.next;
            }
            this.last = cur;
            this.last.next = null;
            chainLen--;
            return true;
        }
    }
    
    /**
     * Returns the hash of the last block in the chain
     * @return a Hash
     */
    public Hash getHash() {
        return this.last.blk.getHash();
    }
    
    /**
     * Walks the blockchain and ensures that its blocks are consistent and valid
     * @return a boolean
     */
    public boolean isValidBlockChain() {
        Node cur = this.first;
        int balance = 0;
        for (int i = 0; i < chainLen; i++) {
            balance += cur.blk.data;
            if (!cur.blk.getHash().isValid() || balance < 0) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }
    
    /**
     * Prints Alice’s and Bob’s respective balances in the form "Alice: , Bob: " 
     * on a single line, e.g., Alice: 300, Bob: 0
     */
    public void printBalances() {
        Node cur = this.first;
        int balance = 0;
        while (cur.next != null) {
            balance += cur.blk.data;
            cur = cur.next;
        }
        System.out.println(balance);
    }
    
    /**
     * Returns a string representation of the BlockChain which is simply the string
     * representation of each of its blocks, earliest to latest, one per line
     * @return a string
     */
    public String toString() {
        Node cur = this.first;
        String ret = "";
        for (int i = 0; i < chainLen; i++) {
            ret += cur.blk.toString() + "\n";
        }
        return ret;
    }

}
