package me.yizhe;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class WordReaderTest {

  private static final String WORD_FILE_PATH = "wordTest.docx";

  @Test
  public void countMap() throws IOException {
    final Map<String, Integer> countMap = WordReader.countMap(WORD_FILE_PATH, "word/document.xml");
    Assert.assertNotNull(countMap);
  }

  @Test(expected = IOException.class)
  public void testWrongFilePath() throws IOException {
    WordReader.countMap("", "");
  }

  @Test(expected = NullPointerException.class)
  public void testNullFilePath() throws IOException {
    WordReader.countMap(null, "");
  }

  @Test(expected = IOException.class)
  public void testWrongDocumentPath() throws IOException {
    WordReader.countMap(WORD_FILE_PATH, "");
  }
}