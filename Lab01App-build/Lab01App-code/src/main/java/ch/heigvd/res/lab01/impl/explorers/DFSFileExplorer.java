package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    // List of directories
    List<File> directories = new ArrayList<>();
    
    vistor.visit(rootDirectory);
    
    if(rootDirectory.listFiles() != null){
        for (File file : rootDirectory.listFiles()){
          // Add directory in the list if the file is a directory else visit file
          if (file.isDirectory()) {
              directories.add(file);
          }
          else{
            vistor.visit(file);
          }
        }
        // call method recursive on all direcrories in the list
        for (File directory : directories){
            explore(directory, vistor); 
        }
    }
  }
}
  