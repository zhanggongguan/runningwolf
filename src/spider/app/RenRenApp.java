package spider.app;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import spider.bean.CrawlerLogic;
import spider.crawler.Crawler;
import spider.extrator.handler.BeanRegexResultHandler;
import spider.extrator.handler.SaveResultHandler;
import spider.site.renren.RenRenPost;

import com.lexin.bean.Feed;

public class RenRenApp {
  public static void main(String[] args) {
    String initUrl = "http://www.renren.com/home";
    String cPattern = "<aaaaaaaaaa>";
    String nPattern = "<a[^>]+?href=\"([^\";]+?)\"[^>]+?>([^>]+?)</a>";
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
        for (Feed feed : results) {
          System.out.println(feed.getTitle() + " " + feed.getDate());
          System.out.println(feed.getContent());
          System.out.println();
        }
      }
    });
    crawler.getShc().executePost(RenRenPost.loginPost());
    crawler.start();
  }
}
