package me.yizhe;

import com.google.common.base.Preconditions;
import java.io.File;

public class FileReader {

  public static File readClassPathFile(String filePath) {
    Preconditions.checkNotNull(filePath);
    return new File(FileReader.class.getClassLoader().getResource(filePath).getFile());
  }

}
