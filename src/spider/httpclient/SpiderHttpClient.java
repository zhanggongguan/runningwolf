package spider.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import spider.site.renren.RenRenPost;

public class SpiderHttpClient {
  private DefaultHttpClient httpClient = new DefaultHttpClient();
  private HttpResponse response;

  private String html;

  public SpiderHttpClient() {
    initHeader();
  }

  private void initHeader() {
    httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
      @Override
      public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        if (!request.containsHeader("User-Agent")) {
          request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0");
        }
        if(!request.containsHeader("Content-Type")){
          request.addHeader("Content-Type","text/html;charset=UTF-8");
        }
      }
    });
  }

  public void executePost(HttpPost post) {
    html="";
    try {
      response = httpClient.execute(post);
      InputStream in = response.getEntity().getContent();
      BufferedReader buff = new BufferedReader(new InputStreamReader(in));
      StringBuffer context = new StringBuffer();
      String line = "";
      while (null != (line = buff.readLine())) {
        context.append(line);
      }
      in.close();
      buff.close();
      html = context.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void executeGet(String httplink) {
    html="";
    HttpGet httpget = new HttpGet(httplink);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    try {
      html = httpClient.execute(httpget, responseHandler);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      httpget.abort();
    }
  }

  public String getHtml() {
    return html;
  }

  public void shutdown() {
    httpClient.getConnectionManager().shutdown();
  }

  public static void main(String[] args) {
    SpiderHttpClient hct = new SpiderHttpClient();
    hct.executePost(RenRenPost.loginPost());
    hct.executeGet("http://www.renren.com/home");
    System.out.println(hct.getHtml());
    hct.shutdown();
  }
}
