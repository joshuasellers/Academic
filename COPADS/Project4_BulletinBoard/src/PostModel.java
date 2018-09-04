/**
 * Class PostModel provides the application logic for a post in the project.
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import java.io.IOException;

public class PostModel {
    // Hidden data members.
    private byte[] message;
    private PostListener listener;
    private byte[] key;

    // Exported constructors.

    /**
     * Construct a new post model object.
     *
     * @param  message   Message to post
     * @param  listener  Post listener.
     * @param  key       key for authentication
     */
    public PostModel(byte[] message, PostListener listener, byte[] key) throws IOException{
        this.message = message;
        this.listener = listener;
        this.key = key;
        // create HMAC object
        HMAC hmac = new HMAC(message,key);
        // get tag
        byte[] tag = hmac.getTag();
        // post message
        listener.post(message,tag);
    }


}
