import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by joshuasellers on 10/19/17.
 */
public class create_txt {
    public static void main(String[] args) throws FileNotFoundException{
        for (int i = 1; i < 11; i++) {
            Integer[] arr = new Integer[i*100000];
            for (int j = 1; j <= arr.length; j++) {
                arr[j-1] = j;
            }
            Collections.shuffle(Arrays.asList(arr));
            File f = new File("file_" + i+".txt");
            try (PrintWriter out = new PrintWriter(f)) {
                out.println(100000*i);
                for (int j = 0; j < i*100000; j++) {
                    out.print(arr[j]);
                    out.print(" ");
                }
            }
        }
    }
}
