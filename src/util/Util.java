package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
  public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static List<List<String>> extract(Pattern pattern, String url) {
    String html = downLoadHTML(url);
    if (null == html || "".equals(html.trim()))
      return null;
    return extractContent(pattern, html);
  }

  public static List<List<String>> extractContent(Pattern pattern, String content) {
    Matcher m = pattern.matcher(content);
    ArrayList<List<String>> items = new ArrayList<List<String>>();
    while (m.find()) {
      ArrayList<String> item = new ArrayList<String>();
      for (int i = 1; i <= m.groupCount(); i++) {
        item.add(m.group(i));
      }
      items.add(item);
    }
    return items;
  }

  public static String downLoadHTML(String urlAddr) {
    StringBuffer content = new StringBuffer();
    try {
      URL url = new URL(urlAddr);
      URLConnection con = url.openConnection();
      con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0");
      con.setRequestProperty("Refer", url.getHost());
      BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) con.getContent(), "utf-8"));
      String line = null;
      while ((line = br.readLine()) != null) {
        content.append(line);
      }
    } catch (IOException e) {
    }
    return content.length() > 0 ? content.toString() : null;
  }

  public static String downLoadHTML(String urlAddr, String encoding) {
    StringBuffer content = new StringBuffer();
    try {
      URL url = new URL(urlAddr);
      URLConnection con = url.openConnection();
      con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0");
      con.setRequestProperty("Refer", url.getHost());
      BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) con.getContent(), encoding));
      String line = null;
      while ((line = br.readLine()) != null) {
        content.append(line);
      }
    } catch (IOException e) {
    }
    return content.length() > 0 ? content.toString() : null;
  }

  public static final Pattern href = Pattern.compile("<a[^<>]+?href=\"?'?([^\"'\\s]+?)'?\"\\s?[^<>]+?>(.+?)</a>");

  public List<List<String>> extractHypeLink(String content) {
    return extractContent(href, content);
  }

  public static String extractSingalGroup(Pattern p, String content) {
    Matcher m = p.matcher(content);
    return m.find() ? m.group(1) : null;
  }

  public static String readFile(String filePath) throws IOException {
    StringBuffer sb = new StringBuffer();
    FileInputStream fis = new FileInputStream(new File(filePath));
    BufferedReader buff = new BufferedReader(new InputStreamReader(fis));
    String line = "";
    while ((line = buff.readLine()) != null) {
      sb.append(line);
    }
    buff.close();
    fis.close();
    return sb.toString();
  }

  public static void printResults(List<List<String>> results) {
    for (List<String> result : results) {
      for (int i = 0; i < result.size(); i++) {
        String field = result.get(i) == null ? "" : result.get(i);
        char[] fields = field.toCharArray();
        field = fromEncodedUnicode(fields, 0, fields.length);
        System.out.print(field.trim().replaceAll(System.getProperty("line.separator"), "")
            + ((i == result.size() - 1) ? System.getProperty("line.separator") : "||||"));
      }
    }
  }

  public static String fromEncodedUnicode(String content) {
    char[] chars = content.toCharArray();
    return fromEncodedUnicode(chars, 0, chars.length);
  }

  public static Properties getProperties(String fileName) throws IOException {
    Properties prop = new Properties();
    File file = new File(fileName);
    InputStream in = new FileInputStream(file);
    prop.load(in);
    in.close();
    return prop;
  }

  public static final String getCurrentDate() {
    return SDF_DATE.format(System.currentTimeMillis());
  }

  public static final String getCurrentDateTime() {
    return SDF_DATETIME.format(System.currentTimeMillis());
  }

  public static String fromEncodedUnicode(char[] in, int off, int len) {
    char aChar;
    char[] out = new char[len]; // 只短不长
    int outLen = 0;
    int end = off + len;
    while (off < end) {
      aChar = in[off++];
      if (aChar == '\\') {
        aChar = in[off++];
        if (aChar == 'u') {
          int value = 0;
          for (int i = 0; i < 4; i++) {
            aChar = in[off++];
            switch (aChar) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
              value = (value << 4) + aChar - '0';
              break;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
              value = (value << 4) + 10 + aChar - 'a';
              break;
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
              value = (value << 4) + 10 + aChar - 'A';
              break;
            default:
              throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
            }
          }
          out[outLen++] = (char) value;
        } else {
          if (aChar == 't') {
            aChar = '\t';
          } else if (aChar == 'r') {
            aChar = '\r';
          } else if (aChar == 'n') {
            aChar = '\n';
          } else if (aChar == 'f') {
            aChar = '\f';
          }
          out[outLen++] = aChar;
        }
      } else {
        out[outLen++] = (char) aChar;
      }
    }
    return new String(out, 0, outLen);
  }

  public static void main(String[] args) throws IOException, InterruptedException {
//    String directory = "/home/ggzh/desktop/files/";
//    File dir = new File(directory);
//    String[] files = dir.list();
//    Util util = new Util();
//    for (String file : files) {
//      String content = Util.readFile(directory + file);
//      List<List<String>> hrefs = util.extractHypeLink(content);
//      for (List<String> href : hrefs) {
//        System.out.println(href.get(0) + ":" + href.get(1).replaceAll("<[^><]+?>", ""));
//      }
//    }
    System.out.println(getCurrentDate());
    System.out.println(getCurrentDateTime());
    Thread.sleep(1000);
    System.out.println(getCurrentDate());
    System.out.println(getCurrentDateTime());
  }

}
