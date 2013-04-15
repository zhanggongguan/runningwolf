package com.lexin.extractor.qiushibaike;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import spider.extrator.regex.RegexUtil;
import spider.util.SpiderUtil;
import util.SpiderConfiguration;
import util.Util;

import com.lexin.bean.Feed;
import com.lexin.bean.Regular;
import com.lexin.bean.Seed;
import com.lexin.bean.SiteProperties;
import com.lexin.db.DBUtil;
import com.lexin.server.HttpServer;

public class CommonExtractorByDB {
  private Properties dbProp;

  private Hashtable<String, List<Pattern>> nextPatterns;
  private Hashtable<String, List<Pattern>> contentPatterns;
  private Hashtable<String, String> encodings;

  private static final int REGULER_TYPE_CONTENT = 1;
  private static final int REGULER_TYPE_NEXTPAGE = 0;
  private static final int VISIT_SITE_INTERVAL = 1000;
  // private static final int MAX_VISIT_LINKS = 500;
  private static final int SETUP_EXTRACTOR_PATTERN_TIME_INTERVAL = 60000;
  private Hashtable<String, Integer> counters = null;

  private PatternOperator patternOperator;
  private DBUtil dbUtil;

  private final static Log LOG = LogFactory.getLog(CommonExtractorByDB.class);

  public CommonExtractorByDB() throws IOException, SQLException {
    counters = new Hashtable<String, Integer>();
    patternOperator = new PatternOperator();
    dbProp = Util.getProperties(SpiderConfiguration.getDBConfigFilePath());
    dbUtil = new DBUtil(dbProp);

    com.lexin.server.HttpServer hs = new HttpServer();
    hs.start();
  }

  public void counter(String host) {
    Integer counter = counters.get(host);
    if (!counters.containsKey(host)) {
      counters.put(host, 1);
    } else {
      counters.put(host, counter + 1);
    }
  }

  public void setup() throws IOException, SQLException {
    nextPatterns = new Hashtable<String, List<Pattern>>();
    contentPatterns = new Hashtable<String, List<Pattern>>();
    encodings = new Hashtable<String, String>();
    List<Regular> cRegulars = dbUtil.getCrawlerRegulars(REGULER_TYPE_CONTENT);
    List<Regular> nRegulars = dbUtil.getCrawlerRegulars(REGULER_TYPE_NEXTPAGE);
    List<SiteProperties> siteProps = dbUtil.getSiteProperties();
    if (null != cRegulars) {
      for (Regular regular : cRegulars) {
        String key = regular.getHost();
        String value = regular.getRegular();
        if (null != contentPatterns.get(key)) {
          contentPatterns.get(key).add(Pattern.compile(value));
        } else {
          List<Pattern> patterns = new ArrayList<Pattern>();
          patterns.add(Pattern.compile(value));
          contentPatterns.put(key, patterns);
        }
      }
    }
    if (null != nRegulars) {
      for (Regular regular : nRegulars) {
        String key = regular.getHost();
        String value = regular.getRegular();
        if (null != nextPatterns.get(key)) {
          nextPatterns.get(key).add(Pattern.compile(value));
        } else {
          List<Pattern> patterns = new ArrayList<Pattern>();
          patterns.add(Pattern.compile(value));
          nextPatterns.put(key, patterns);
        }
      }
    }
    if (null != siteProps) {
      for (SiteProperties sp : siteProps) {
        encodings.put(sp.getHost(), sp.getEncoding());
      }
    }

  }

  public void startExtract() throws Exception {
    List<Seed> crawlingSeeds = null;
    long preLoadTimeSpam = System.currentTimeMillis() - SETUP_EXTRACTOR_PATTERN_TIME_INTERVAL;
    while (true) {
      long curLoadTimeSpam = System.currentTimeMillis();
      if (curLoadTimeSpam - preLoadTimeSpam >= SETUP_EXTRACTOR_PATTERN_TIME_INTERVAL) {
        LOG.info("reload site regex expression comfigurations.");
        preLoadTimeSpam = curLoadTimeSpam;
        setup();
      }
      crawlingSeeds = dbUtil.getCrawlingSeeds(VISIT_SITE_INTERVAL);
      if (SpiderUtil.isEmpty(crawlingSeeds)) {
        LOG.info("waiting 10 seconds for seeds....");
        Thread.sleep(10000);
        continue;
      }

      List<Feed> results = new ArrayList<Feed>();
      List<Seed> nextpages = new ArrayList<Seed>();
      for (Seed seed : crawlingSeeds) {
        String crawlingURL = SpiderUtil.defaultHttpProctol(seed.getUrl());
        final String host = SpiderUtil.getHost(crawlingURL);
        List<Pattern> patterns = contentPatterns.get(host);
        LOG.info("start crawling " + crawlingURL);
        if (SpiderUtil.isEmpty(crawlingURL) || SpiderUtil.isEmpty(host) || SpiderUtil.isEmpty(patterns)) {
          LOG.warn("url can't be resolved or configured " + crawlingURL);
          continue;
        }
        String text = Util.downLoadHTML(crawlingURL, currentPageEncoding(host));
        if (SpiderUtil.isEmpty(text))
          continue;
        List<Feed> feeds = parseCurrentPageForContent(crawlingURL, patterns, text);
        LOG.info(crawlingURL + " hit " + feeds.size() + " targets!");

        List<Seed> seeds = parseCurrentPageForSeed(crawlingURL, host, text);
        if (!SpiderUtil.isEmpty(seeds)) {
          nextpages.addAll(seeds);
        }
        if (!SpiderUtil.isEmpty(feeds)) {
          results.addAll(feeds);
        }
        if (LOG.isDebugEnabled()) {
          SpiderUtil.printResult(feeds);
          SpiderUtil.printSeeds(seeds);
        }
        Thread.sleep(VISIT_SITE_INTERVAL);
        counter(host);
      }
      dbUtil.saveFeeds(results);// save results
      dbUtil.saveSeeds(nextpages);// save nextpages
      dbUtil.updateCrawledTimes(crawlingSeeds);// update crawling status
    }
  }

  public List<Seed> parseCurrentPageForSeed(final String crawlingURL, String host, String text) {
    List<Pattern> nps = nextPatterns.get(host);
    List<Seed> seeds = new ArrayList<Seed>();
    for (Pattern np : nps) {
      Matcher matcher = np.matcher(text);
      List<Seed> items = patternOperator.exeExtractor(crawlingURL, host, matcher);
      if (!SpiderUtil.isEmpty(items)) {
        seeds.addAll(items);
      }
    }
    return seeds;
  }

  public String currentPageEncoding(String host) {
    return (null == encodings.get(host) || "".equals(encodings.get(host))) ? "utf-8" : encodings.get(host);
  }

  public List<Feed> parseCurrentPageForContent(String crawlingURL, List<Pattern> patterns, String text) {
    Matcher matcher = null;
    String contentRegex = null;
    for (Pattern pattern : patterns) {
      matcher = pattern.matcher(text);
      if (matcher != null && matcher.find()) {
        contentRegex = pattern.pattern();
        break;
      }
    }
    List<Feed> feeds = new ArrayList<Feed>();
    if (!SpiderUtil.isEmpty(contentRegex)) {
      matcher.reset();
      List<String> namedGroups = RegexUtil.regexPatternNamedGroups(contentRegex);
      feeds = patternOperator.exeObjectExtractor(crawlingURL, matcher, namedGroups);
    }
    return feeds;
  }

  public static void main(String[] args) {
    try {
      CommonExtractorByDB nce = new CommonExtractorByDB();
      nce.startExtract();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
