package ch.heigvd.res.lab01.impl;


import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    
    // delimiters array
    String[] delimiters = {"\n", "\r", "\r\n"}; 
    
    // Array to save result 
    // we initialize at null
    String[] stringTab = {null,null};
    
    for (String delimiter : delimiters) {
      int pos = lines.indexOf(delimiter);
      if (pos != -1){
          // save the substring before the first delimiter found in the column 0
          stringTab[0] = lines.substring(0, pos + delimiter.length());
          // save the last substring in the column 1
          stringTab[1] = lines.substring(pos + delimiter.length());
          return stringTab;
      }
    }
    
    // if there is not delimiters in the line
    stringTab[0] = "";
    stringTab[1] = lines;
    return stringTab;
          
  }

}
