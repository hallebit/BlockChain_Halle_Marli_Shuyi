import java.util.*;
public class Hash {
    public byte[] data; 

    // constructs a new Hash object that contains the given hash (as an array of bytes).
    public Hash(byte[] data) {
        this.data = data; 
    }

    //returns the hash contained in this object.
    public byte[] getData() {
        return this.data; 
    }

    //Returns true if this hash meets the criteria for validity, i.e., 
    //its first three indices contain zeroes.
    public boolean isValid() {
        if(this.data.length >= 3 &&
                this.data[0] == 0 && 
                this.data[1] == 0 && 
                this.data[2] == 0) {
            return true; 		
        } else {
            return false; 
        }
    }

    //returns the string representation of the hash as
    //a string of hexadecimal digits, 2 digits per byte.
    public String toString() {
        String ret = "";
        for(int i = 0; i < this.data.length; i++){
            ret += String.format("%x", Byte.toUnsignedInt(this.data[i]));
        }
        return ret;
    }

    //returns true if this hash is structurally 
    //equal to the argument.
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash temp = (Hash) other;
            return Arrays.equals(temp.getData(), this.data);
        }
        return false; 
    }
}
