package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
  public static String encryptToMD5(String info) {
    byte[] digesta = null;
    try {
      MessageDigest alga = MessageDigest.getInstance("MD5");
      alga.update(info.getBytes());
      digesta = alga.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return byte2hex(digesta);
  }

  public static String encryptToMD52(String info) {
    byte[] digesta = null;
    try {
      MessageDigest alga = MessageDigest.getInstance("MD5");
      alga.update(info.getBytes());
      digesta = alga.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    String rs = digesta.toString();
    return rs;
  }

  public static String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1) {
        hs = hs + "0" + stmp;
      } else {
        hs = hs + stmp;
      }
    }
    return hs.toUpperCase();
  }
}
