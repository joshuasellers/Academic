/**
 * Class HMAC provides the authentication for bulletin board messages in the
 * project based on SHA-256.
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import edu.rit.crypto.SHA256;

public class HMAC {
    // Hidden data members.
    private byte[] message;
    private byte[] key;
    private int B = 64;
    private byte[] tag;

    /**
     * Construct a new HMAC object.
     *
     * @param  message  post message.
     * @param  key      Private key
     */
    public HMAC(byte[] message, byte[] key){
        this.message = message;
        this.key = key;
        this.tag = findTag();
    }
    // exported operations

    /**
     * Return the tag.
     * @return byte[]
     */
    public byte[] getTag(){return this.tag;}


    public boolean compareTags(byte[] t){
        boolean same = true;
        for (int i = 0; i < t.length; i++){
            if (this.tag[i] != t[i]) same = false;
        }
        return same;
    }
    /**
     * Get authentication tag for board
     *
     * @return byte[]  tag
     */
    public byte[] findTag(){
        byte[] k = new byte[B];
        // space out key
        for (int i = 0; i < this.key.length; i++){
            k[i] = this.key[i];
        }
        byte[] ipad = new byte[B];
        byte[] opad = new byte[B];
        // make ipad and opad
        for (int i = 0; i < B; i++){
            ipad[i] = 0b0110110;
            opad[i] = 0b1011100;
        }
        // interim byte arrays for algorithm
        byte[] interim1 = new byte[B];
        byte[] interim2 = new byte[B];
        // run XOR
        for (int i = 0; i < B; i++) {
            byte xor1 = (byte)(0xff & ((int)k[i]) ^ ((int)ipad[i]));
            interim1[i] = xor1;
            byte xor2 = (byte)(0xff & ((int)k[i]) ^ ((int)opad[i]));
            interim2[i] = xor2;
        }
        // create SHA256 object
        SHA256 sh = new SHA256();
        // has the data
        sh.hash(interim1);
        sh.hash(this.message);
        // get the digest
        byte[] out = new byte[sh.digestSize()];
        sh.digest(out);
        // hash the data
        sh.hash(interim2);
        sh.hash(out);
        //get digest
        byte[] tag = new byte[sh.digestSize()];
        sh.digest(tag);
        // return tag
        return tag;

    }


}
