package spider.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;

import spider.bean.CrawlerLogic;
import spider.bean.UrlQueue;
import spider.bean.UrlSeed;
import spider.extrator.handler.BeanRegexResultHandler;
import spider.extrator.handler.SaveResultHandler;
import spider.extrator.regex.RegexOP;
import spider.httpclient.SpiderHttpClient;
import spider.util.SpiderUtil;

public class Crawler<T> {
  private UrlQueue urlQueue = new UrlQueue();
  private SpiderHttpClient shc;
  private RegexOP<UrlSeed> seedRegex;
  private RegexOP<T> contextRegex;
  private BeanRegexResultHandler<T> resultHander;
  private SaveResultHandler<T> saveHandler;
  private HashSet<String> crawledDB;

  public Crawler(final CrawlerLogic crawlerLogic, BeanRegexResultHandler<T> resultHander,
      SaveResultHandler<T> saveHandler) {
    for (String url : crawlerLogic.getInitUrlList()) {
      UrlSeed urlSeed = new UrlSeed();
      urlSeed.setCrawled(false);
      urlSeed.setLink(url);
      urlSeed.setLinkText("init");
      urlSeed.setLayer(0);
      urlQueue.put(urlSeed);
    }
    shc = new SpiderHttpClient();
    this.seedRegex = new RegexOP<UrlSeed>(crawlerLogic.getSeedPattern());
    this.contextRegex = new RegexOP<T>(crawlerLogic.getContextPattern());
    this.resultHander = resultHander;
    this.saveHandler = saveHandler;
    this.crawledDB = new HashSet<String>();
  }

  public void start() {
    while (true) {
      final UrlSeed urlSeed = urlQueue.pop();
      if (null == urlSeed||urlSeed.getLayer()>=5) {
        break;
      }
      if (crawledDB.contains(urlSeed.getLink())) {
        continue;
      } else {
        crawledDB.add(urlSeed.getLink());
      }
      System.out.println("[INFO] crawling " + urlSeed.getLink());
      shc.executeGet(urlSeed.getLink());
      String html = shc.getHtml();
      List<T> results = contextRegex.matchToBeanList(html, resultHander);
      saveHandler.handle(results);
      List<UrlSeed> nextLayers = seedRegex.matchToBeanList(html, new BeanRegexResultHandler<UrlSeed>() {
        @Override
        public UrlSeed handle(Matcher mr) {
          UrlSeed url = new UrlSeed();
          url.setCrawled(false);
          url.setLink(SpiderUtil.getAbsoluteUrl(urlSeed.getLink(), mr.group(1)));
          url.setLinkText(mr.group(2));
          url.setLayer(urlSeed.getLayer() + 1);
          if (null == url.getLink())
            return null;
          return url;
        }
      });
      urlQueue.put(nextLayers);
    }
  }

  public SpiderHttpClient getShc() {
    return shc;
  }

  public void setShc(SpiderHttpClient shc) {
    this.shc = shc;
  }
  
}
