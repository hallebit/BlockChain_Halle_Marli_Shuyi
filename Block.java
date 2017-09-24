import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    int number;
    int data;
    Hash prevHash;
    long nonce;
    Hash curHash;
    
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.number = num;
        this.data = amount;
        this.prevHash = prevHash;
        
        //abstraction?
        long possNonce = 0; //is incrementing this really the best way to go? random?     
        String msg = "" + num + amount + prevHash.toString() + possNonce;      
        MessageDigest md = MessageDigest.getInstance("sha-256"); //are we for certain using this format? 
        md.update(msg.getBytes());
        Hash possHash = new Hash(md.digest());
        
        while(!possHash.isValid()){
            possNonce++;
            msg = "" + num + amount + prevHash.toString() + possNonce;
            md = MessageDigest.getInstance("sha-256"); //are we for certain using this format? 
            md.update(msg.getBytes());
            possHash = new Hash(md.digest());
        }
        
        this.nonce = possNonce;
        this.curHash = possHash;
        // Create possible nonce variable
        // Test if the hash of the possible nonce variable and 
    }
    
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.number = num;
        this.data = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        
        String msg = "" + num + amount + prevHash.toString() + nonce;      
        MessageDigest md = MessageDigest.getInstance("sha-256"); 
        md.update(msg.getBytes());
        this.curHash = new Hash(md.digest());
    }
    
    public int getNum(){
        return this.number;
    }
    
    public int getAmount(){
        return this.data;
    }
    
    public long getNonce(){
        return this.nonce;
    }
    
    public Hash getPrevHash(){
        return this.prevHash;
    }
    
    public Hash getHash(){
        return this.curHash;
    }
    
    public String toString(){
        return "Block " + this.number + " (Amount: " + this.data + ", Nonce: " + this.nonce + 
               ", prevHash: " + this.prevHash.toString() + ",  hash: " + this.curHash.toString() + ")"; 
    }
}
