package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private int lineNumber;
    private int previousChar;
    private boolean backSlashNdetected;
    private boolean backSlashRdetected;
    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        this.write(str.toCharArray(), off, len);

    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            this.write(cbuf[i]);
        }
    }

    @Override
    public void write(int c) throws IOException {

        // if new entry on the file
        if (lineNumber == 0) {
            lineNumber++;
            String str = Integer.toString(lineNumber);
            super.write(str.charAt(0));
            super.write('\t');
        }

        if (c == '\n') {
            if (backSlashRdetected) {
                // here we are treated the windows return carriage
                super.write('\r');
                super.write('\n');
                lineNumber++;
                //convert lineNumer to char
                // first we convert line number to string
                // then we write character in the thile
                String str = Integer.toString(lineNumber);
                super.write(str.charAt(0));
                super.write('\t');
                backSlashRdetected = false;
            } else {
                // Here we are treated unix return carriage
                super.write('\n');
                lineNumber++;
                // convert lineNumer to char
                // first we convert line number to string
                // then we write character in the thile
                String str = Integer.toString(lineNumber);
                super.write(str.charAt(0));
                super.write('\t');
            }
            
        } else if (c == '\r') {
            // we have detected '\r'
            backSlashRdetected = true;
            
        } else {
            if (backSlashRdetected) {
                // Here we are treated MAC return carriage
                super.write('\r');
                lineNumber++;
                // convert lineNumer to char
                // first we convert line number to string
                // then we write character in the thile
                String ch = Integer.toString(lineNumber);
                super.write(ch.charAt(0));
                super.write('\t');

            }
            super.write(c);
            backSlashRdetected = false;
        }
    }
}
