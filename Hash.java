import java.util.*;
public class Hash {
    public byte[] data; 

    /**
     * Constructs a new Hash object that contains the given hash (as an array of bytes)
     * @param data
     */
    public Hash(byte[] data) {
        this.data = data; 
    }

    /**
     * Returns the hash contained in this object.
     * @return a byte array
     */
    public byte[] getData() {
        return this.data; 
    }

    /**
     * Returns true if this hash's first three indices contain zeroes and false otherwise
     * @return a boolean
     */
    public boolean isValid() {
        if(this.data.length > 3 &&
                this.data[0] == 0 && 
                this.data[1] == 0 && 
                this.data[2] == 0) {
            return true; 		
        } else {
            return false; 
        }
    }

    /**
     * Returns the string representation of the hash as a string
     * of hexadecimal digits, 2 digits per byte.
     * @return a string
     */
    public String toString() {
        String ret = "";
        for(int i = 0; i < this.data.length; i++){
            ret += String.format("%02x", Byte.toUnsignedInt(this.data[i]));
        }
        return ret;
    }

    /**
     * Returns true if this hash is structurally equal to the argument and false otherwise.
     * @return a boolean
     */
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash temp = (Hash) other;
            return Arrays.equals(temp.getData(), this.data);
        }
        return false; 
    }
}
