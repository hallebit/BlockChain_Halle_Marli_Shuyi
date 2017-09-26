import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Block {
    int number;
    int data;
    Hash prevHash;
    long nonce;
    Hash curHash;
    
    /**
     * Creates a new block from the specified parameters, performing the mining operation
     * to discover the nonce and hash for this block given these parameters
     * @param num, an integer
     * @param amount, an integer
     * @param prevHash, a Hash represented by a byte array
     * @throws NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.number = num;
        this.data = amount;
        this.prevHash = prevHash;
        
        long possNonce = 0;      
        byte[] numToByteArr = ByteBuffer.allocate(4).putInt(num).array();
        byte[] amountToByteArr = ByteBuffer.allocate(4).putInt(amount).array();
        byte[] prevHashToByteArr = prevHash.toString().getBytes();
        byte[] nonceToByteArr = ByteBuffer.allocate(8).putLong(possNonce).array();      
        byte[] basics = combineBasics(numToByteArr, amountToByteArr, prevHashToByteArr);
        byte[] msg = combineNonce(basics, nonceToByteArr);
        //byte[] msg = ArrayUtils.toPrimitive(ArrayUtils.addAll(Arrays.asList(numToByteArr), Arrays.asList(amountToByteArr), Arrays.asList(prevHashToByteArr), Arrays.asList(nonceToByteArr)).toArray());
        MessageDigest md = MessageDigest.getInstance("sha-256"); 
        md.update(msg);
        Hash possHash = new Hash(md.digest());
        
        while(!possHash.isValid()){
            possNonce++;
            nonceToByteArr = ByteBuffer.allocate(8).putLong(possNonce).array();
            msg = combineNonce(basics, nonceToByteArr);
            md = MessageDigest.getInstance("sha-256"); 
            md.update(msg);
            possHash = new Hash(md.digest());
        }
        
        this.nonce = possNonce;
        this.curHash = possHash;
        // Create possible nonce variable
        // Test if the hash of the possible nonce variable and 
    }
    
    
    private byte[] combineBasics(byte[] na, byte[] aa, byte[] pha) {
    	byte[] ret = new byte[na.length + aa.length + pha.length];
    	for(int i = 0; i < ret.length; i++) {
    		if(i / na.length == 0) {
    			ret[i] = na[i];
    		} else if (i / (na.length + aa.length) == 0) {
    			ret[i] = aa[i - (na.length - 1)];
    		} else if (i / (na.length + aa.length + pha.length) == 0) {
    			ret[i] = aa[i - (na.length + aa.length - 1)];
    		}
    	}
    	return ret;
    }
    
    private byte[] combineNonce(byte[] arr, byte[] na) {
    	byte[] ret = new byte[na.length + aa.length + pha.length];
    	for(int i = 0; i < ret.length; i++) {
    		if(i / na.length == 0) {
    			ret[i] = na[i];
    		} else if (i / (na.length + aa.length) == 0) {
    			ret[i] = aa[i - (na.length - 1)];
    		} else if (i / (na.length + aa.length + pha.length) == 0) {
    			ret[i] = aa[i - (na.length + aa.length - 1)];
    		}
    	}
    	return ret;
    }
    
    /**
     * Creates a new block from the specified parameters, using the provided nonce and 
     * additional parameters to generate the hash for the block
     * @param num, an integer
     * @param amount, an integer
     * @param prevHash, a Hash
     * @param nonce, a long
     * @throws NoSuchAlgorithmException
     */
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
    
    /**
     * Returns the number of this block
     * @return an integer
     */
    public int getNum(){
        return this.number;
    }
    
    /**
     * Returns the amount transferred that is recorded in this block
     * @return an integer
     */
    public int getAmount(){
        return this.data;
    }
    
    /**
     * Returns the nonce of this block
     * @return a long
     */
    public long getNonce(){
        return this.nonce;
    }
    
    /**
     * Returns the hash of the previous block in the blockchain
     * @return a Hash (represented by a byte array)
     */
    public Hash getPrevHash(){
        return this.prevHash;
    }
    
    /**
     * Returns the hash of this block
     * @return a Hash (represented by a byte array)
     */
    public Hash getHash(){
        return this.curHash;
    }
    
    /**
     * Returns a string representation of the block.
     * The string representation is formatted as: "Block  (Amount: , Nonce: , prevHash: , hash: )"
     * @return a string
     */
    public String toString(){
        return "Block " + this.number + " (Amount: " + this.data + ", Nonce: " + this.nonce + 
               ", prevHash: " + this.prevHash.toString() + ",  hash: " + this.curHash.toString() + ")"; 
    }
}
