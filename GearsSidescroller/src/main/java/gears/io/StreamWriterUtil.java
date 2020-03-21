package gears.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Matt Crow
 */
public class StreamWriterUtil {
    public static final String NEWLINE = System.lineSeparator();
    
    /**
     * Writes the given String to an output stream.
     * 
     * @param out the stream to write to.
     * @param content the String to write to the stream.
     * @throws IOException if any errors occur while writing to the stream.
     */
    public static void writeStream(OutputStream out, String content) throws IOException{
        try (BufferedWriter write = new BufferedWriter(new OutputStreamWriter(out))) {
            write.write(content);
            write.flush();
        }
    }
}
