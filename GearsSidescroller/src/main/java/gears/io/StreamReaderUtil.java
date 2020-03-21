package gears.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Matt Crow
 */
public class StreamReaderUtil {
    public static final String NEWLINE = System.lineSeparator();
    
    /**
     * Reads the contents of an InputStream, returning it as a String.
     * 
     * @param in the stream to read
     * @return the contents of the stream as a String
     * @throws IOException if any errors occur while reading the stream.
     */
    public static String readStream(InputStream in) throws IOException{
        StringBuilder b = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new InputStreamReader(in))) {
            while(read.ready()){
                b.append(read.readLine()).append(NEWLINE);
            }
        }
        return b.toString();
    }
}
