package spider.site.renren;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class RenRenPost {
  public static HttpPost loginPost() {
    HttpPost httpPost = new HttpPost("http://www.renren.com/PLogin.do");
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("email", "111"));
    nvps.add(new BasicNameValuePair("password", "1111"));
    nvps.add(new BasicNameValuePair("autoLogin", "true"));
    nvps.add(new BasicNameValuePair("icode", ""));
    nvps.add(new BasicNameValuePair("origURL", "http://www.renren.com/home"));
    nvps.add(new BasicNameValuePair("domain", "renren.com"));
    nvps.add(new BasicNameValuePair("key_id", "1"));
    nvps.add(new BasicNameValuePair("captcha_type", "web_login"));
    httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
    return httpPost;
  }
}
