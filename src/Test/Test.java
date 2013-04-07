package Test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Util;

public class Test {
  public static void main(String[] args) throws UnsupportedEncodingException {
    SortedMap<String, String> maps = new TreeMap<String, String>();
    maps.put("zip", "111");
    maps.put("bz2", "test");
    SortedMap<String, String> subMap = maps.headMap("bz.bash");
    if (!subMap.isEmpty())
      System.out.println(":T:" + subMap.lastKey());
    System.out.println("END");
    String str = "\u513f\u5b50\u770b\u7740\u624b\u673a\u5c4f\u5e55\u4e00\u5b57\u4e00\u987f\u5730\u8bfb\u8d77\u6765\uff1a\u201c\u4e2d-\u56fd-\u591a-\u4e91\u3002\u201d\u542c\u5230\u513f\u5b50\u7684\u64ad\u62a5\uff0c\u6211\u597d\u5947\u5730\u95ee\u4ed6\uff1a\u201c\u4f60\u8fd9\u662f\u54ea\u91cc\u7684\u5929\u6c14\u9884\u62a5\uff0c\u9762\u79ef\u8fd9\u4e48\u5927\uff1f\u201d\u513f\u5b50\u62ff\u7740\u6211\u7684\u624b\u673a\u8bf4\uff1a\u201c\u624b\u673a\u9884\u62a5\u7684\u3002\u201d\u6211\u63a5\u8fc7\u624b\u673a\u4e00\u770b\uff0c\u539f\u6765\u513f\u5b50\u5f53\u8d77\u4e86\u534a\u5b57\u5148\u751f\uff0c\u624b\u673a\u5c4f\u5e55\u4e0a\u4e0d\u8fc7\u662f\u201c\u4e2d\u56fd\u79fb\u52a8\u201d\u56db\u4e2a\u5b57\uff01";
    char[] c = str.toCharArray();
    System.out.println(Util.fromEncodedUnicode(c, 0, c.length));
    System.out.println(calculateDate(1357048919L));
    System.out.println(calculateDate(1356915541L));

    System.out.println(calculateDate(1357105883650L));
    System.out.println(calculateDate(1354776131422L));

    String source = "a1111+a1111+a1222_";
    String pStr = "a(?<number>\\d+)";
    Pattern p = Pattern.compile(pStr);
    Matcher m = p.matcher(source);
    while (m.find()) {
    }
  }

  public static String calculateDate(long dateLong) {
    java.util.Calendar cal = java.util.Calendar.getInstance();
    cal.setTimeInMillis(dateLong);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(cal.getTime());
  }

}
