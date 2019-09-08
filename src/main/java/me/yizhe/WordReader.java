package me.yizhe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WordReader {

  private static final SAXReader READER = new SAXReader();


  public static Map<String, Integer> countMap(String wordFilePath, String documentPath) throws IOException {
    final Document document = readWordFile(FileReader.readClassPathFile(wordFilePath), documentPath);
    Map<String, Integer> map = new HashMap<>();
    for (Element element : document.getRootElement().elements().get(0).elements()) {
      walkXml(element, map);
    }
    return map;
  }


  public static Document readWordFile(File file, String documentPath) throws IOException {
    try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
      ZipInputStream zipInputStream = new ZipInputStream(inputStream);

      ZipEntry nextEntry;
      while ((nextEntry = zipInputStream.getNextEntry()) != null) {
        if (nextEntry.getName().equals(documentPath)) {
          ZipFile zipFile = new ZipFile(file);
          return readXmlFile(zipFile.getInputStream(nextEntry));
        }
      }
    }
    throw new IOException(String.format("path:%s not found", documentPath));
  }


  private static void walkXml(Element element, Map<String, Integer> map) {
    for (Element child : element.elements()) {
      final String name = child.getName();
      map.put(name, map.getOrDefault(name, 0) + 1);
    }
  }

  private static Document readXmlFile(InputStream inputStream) throws IOException {
    try {
      return READER.read(inputStream);
    } catch (DocumentException e) {
      throw new IOException(e);
    }
  }

}
