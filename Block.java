import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        byte[] prevHashToByteArr = "".getBytes();
        if (this.prevHash != null) {
            prevHashToByteArr = prevHash.getData();            
        }
        byte[] nonceToByteArr = ByteBuffer.allocate(8).putLong(possNonce).array(); 
        byte[] basics = combineBasics(numToByteArr, amountToByteArr, prevHashToByteArr);
        byte[] msg = combineNonce(basics, nonceToByteArr);
        MessageDigest md = MessageDigest.getInstance("sha-256"); 
        md.update(msg);
        Hash possHash = new Hash(md.digest());
        while(!possHash.isValid()){
            possNonce++;
            nonceToByteArr = ByteBuffer.allocate(8).putLong(possNonce).array();
            msg = combineNonce(basics, nonceToByteArr);
            md.update(msg);
            possHash = new Hash(md.digest());
        }
        this.nonce = possNonce;
        this.curHash = possHash;
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
        
        byte[] numToByteArr = ByteBuffer.allocate(4).putInt(num).array();
        byte[] amountToByteArr = ByteBuffer.allocate(4).putInt(amount).array();
        byte[] prevHashToByteArr = "".getBytes();
        if (this.prevHash != null) {
            prevHashToByteArr = prevHash.getData();            
        }
        byte[] nonceToByteArr = ByteBuffer.allocate(8).putLong(nonce).array();      
        byte[] basics = combineBasics(numToByteArr, amountToByteArr, prevHashToByteArr);
        byte[] msg = combineNonce(basics, nonceToByteArr);
        
        MessageDigest md = MessageDigest.getInstance("sha-256"); 
        md.update(msg);
        this.curHash = new Hash(md.digest());
    }
    
    /**
     * Merges three arrays together in order
     * @param na, the byte array converted from the number of the block
     * @param aa, the byte array converted from the amount of the block
     * @param pha, the byte array converted from the previous hash of the block
     * @return ret, a byte array of all three of the parameters combined
     */
    private byte[] combineBasics(byte[] na, byte[] aa, byte[] pha) {
        byte[] ret = new byte[na.length + aa.length + pha.length];
        for(int i = 0; i < na.length; i++){
            ret[i] = na[i];
        }
        for(int j = 0; j < aa.length; j++){
            ret[j + na.length] = aa[j];
        }
        for(int w = 0; w < pha.length; w++){
            ret[w + na.length + aa.length] = pha[w];
        }

        return ret;
    }
    
    /**
     * Merges two arrays together in order
     * @param arr, a byte array
     * @param na, the byte array converted from the nonce of the block
     * @return ret, a byte array of both parameters combined
     */
    private byte[] combineNonce(byte[] arr, byte[] na) {
        byte[] ret = new byte[arr.length + na.length];
        for(int i = 0; i < arr.length; i++) {
            ret[i] = arr[i];
        }
        for(int j = 0; j < na.length; j++) {
            ret[arr.length + j] = na[j];
        }
        return ret;
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
        String prevHashStr = null;
        String curHashStr = null;
        if (this.prevHash != null) {
           prevHashStr = this.prevHash.toString();            
        }
        if (this.curHash != null) {
            curHashStr = this.curHash.toString();         
        }
        return "Block " + this.number + " (Amount: " + this.data + ", Nonce: " + this.nonce + 
               ", prevHash: " + prevHashStr + ",  hash: " + curHashStr + ")"; 
    }
}
