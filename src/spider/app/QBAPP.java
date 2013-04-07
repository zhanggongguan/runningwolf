package spider.app;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import spider.bean.CrawlerLogic;
import spider.crawler.Crawler;
import spider.extrator.handler.BeanRegexResultHandler;
import spider.extrator.handler.SaveResultHandler;

import com.lexin.bean.Feed;

public class QBAPP {
  public static void main(String[] args) {
    String initUrl = "http://joke.qq.com/";
    String cPattern = "div\\sid=\"ArticleTit\">([^>]+?)</div>\\s*<div\\s*id=\"ArtFrom\">\\s*<a[^>]+?>[^>]+?</a>([^>]+?)\\s*<a\\sclass=\"lblack\"\\shref=\"http://joke.qq.com/\">[^>]+?</a>[^>]*?<a[^>]+?>[^>]+?<span[^>]+?>[^>]+?</span>[^>]+?</a>\\s*</div>\\s*<div[^>]+?>\\s*<P[^>]+?><A[^>]+?>[^>]+?</A>\\s*</P>\\s*<p>([^>]+?)</p>";
    cPattern="<strong>([^>]+?)</strong></td></tr>\\s*<tr><td[^>]+?><img[^>]+?></td></tr>\\s*<tr><td[^>]+?>\\s*<a[^>]+?joke[^>]+?>http://joke.qq.com</a>[^>]+?(\\d+年\\s*\\d+月\\s*\\d+日)[^>]+?</td></tr>\\s*<tr><td id=\"textflag\" class=\"style01\">(.+?)\\s*<!--end-->";
    String nPattern = "<a\\shref=\"(http://joke.[^\"]+?|(?!http)[^\"]+?\\.htm)\"[^>]+?>([^>]+?)</a>";
    List<String> urls = new ArrayList<String>();
    urls.add(initUrl);
    CrawlerLogic cl = new CrawlerLogic(cPattern, nPattern, urls);

    Crawler<Feed> crawler = new Crawler<Feed>(cl, new BeanRegexResultHandler<Feed>() {
      @Override
      public Feed handle(Matcher mr) {
        Feed feed = new Feed();
        feed.setContent(mr.group(3));
        feed.setDate(mr.group(2));
        feed.setTitle(mr.group(1));
        return feed;
      }
    }, new SaveResultHandler<Feed>() {
      @Override
      public void handle(List<Feed> results) {
        for(Feed feed:results){
          System.out.println(feed.getTitle()+" "+feed.getDate());
          System.out.println(feed.getContent());
          System.out.println();
        }
      }
    });
    crawler.start();
  }
}
