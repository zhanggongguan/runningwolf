package com.lexin.extractor.qiushibaike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.extrator.regex.RegexUtil;
import spider.util.SpiderUtil;
import util.SpiderConfiguration;
import util.Util;

import com.lexin.bean.Feed;
import com.lexin.db.DBUtil;

public class CommonExtractorByConf {
  private Properties dbProp;

  private List<String> crawlingSeeds;
  private HashSet<String> crawledSeeds;

  private Hashtable<String, List<Pattern>> nextPatterns;
  private Hashtable<String, List<Pattern>> contentPatterns;
  private Hashtable<String, String> encodings;

  private static final String CRAWLER_INTERFACE_SUFFIX = ".interface";
  private static final String CRAWLER_NEXTPAGE_SUFFIX = ".nextpage";
  private static final String CRAWLER_CONTENT_SUFFIX = ".content";
  private static final String CRAWLER_ENCODING_SUFFIX = ".encoding";

  private static final int VISIT_SITE_INTERVAL = 1000;

  private PatternOperator patternOperator;
  private DBUtil dbUtil;

  public CommonExtractorByConf() throws IOException {
    crawlingSeeds = new ArrayList<String>(100);
    crawledSeeds = new HashSet<String>();
    nextPatterns = new Hashtable<String, List<Pattern>>();
    contentPatterns = new Hashtable<String, List<Pattern>>();
    encodings = new Hashtable<String, String>();

    patternOperator = new PatternOperator();
    setup();
  }

  public void setup() throws IOException {
    Properties props = Util.getProperties(SpiderConfiguration.getEXConfigFilePath());
    dbProp = Util.getProperties(SpiderConfiguration.getDBConfigFilePath());
    dbUtil = new DBUtil(dbProp);
    for (Entry<Object, Object> prop : props.entrySet()) {
      if (null == prop)
        continue;
      String key = prop.getKey().toString();
      String value = prop.getValue().toString();
      if (key.contains(CRAWLER_INTERFACE_SUFFIX)) {
        String[] values = value.split(",");
        for (String va : values) {
          crawlingSeeds.add(va);
        }
      } else if (key.contains(CRAWLER_NEXTPAGE_SUFFIX)) {
        key = key.substring(0, key.indexOf(CRAWLER_NEXTPAGE_SUFFIX));
        if (null != nextPatterns.get(key)) {
          nextPatterns.get(key).add(Pattern.compile(value));
        } else {
          List<Pattern> patterns = new ArrayList<Pattern>();
          patterns.add(Pattern.compile(value));
          nextPatterns.put(key, patterns);
        }
      } else if (key.contains(CRAWLER_CONTENT_SUFFIX)) {
        key = key.substring(0, key.indexOf(CRAWLER_CONTENT_SUFFIX));
        if (null != contentPatterns.get(key)) {
          contentPatterns.get(key).add(Pattern.compile(value));
        } else {
          List<Pattern> patterns = new ArrayList<Pattern>();
          patterns.add(Pattern.compile(value));
          contentPatterns.put(key, patterns);
        }
      } else if (key.contains(CRAWLER_ENCODING_SUFFIX)) {
        key = key.substring(0, key.indexOf(CRAWLER_ENCODING_SUFFIX));
        encodings.put(key, value);
      } else {
        System.out.println("[INFO] " + key + "=" + value + " is not supported!");
      }
    }
  }

  public void startExtract() throws InterruptedException {
    while (!crawlingSeeds.isEmpty()) {
      String seed = crawlingSeeds.get(0);
      if (crawledSeeds.contains(seed)) {
        crawlingSeeds.remove(0);
        continue;
      }
      System.out.println("[INFO] crawling " + seed);
      final String host = SpiderUtil.getHost(seed);
      String encoding = (null == encodings.get(host)) ? "utf-8" : encodings.get(host);
      String text = Util.downLoadHTML(seed, encoding);
      if (null == text || "".equals(text))
        continue;
      List<Pattern> cps = contentPatterns.get(host);
      if (cps == null) {
        System.out.println("[INFO] Error Configuration: " + host);
        break;
      }
      Matcher matcher = null;
      String patternStr = null;
      for (Pattern cp : cps) {
        matcher = cp.matcher(text);
        if (matcher != null && matcher.find()) {
          matcher.reset();
          patternStr = cp.pattern();
          break;
        }
      }
      // TODO Operate Content
      List<Feed> results = null;
      if (null != matcher && matcher.find()) {
        matcher.reset();
        List<String> namedGroups = RegexUtil.regexPatternNamedGroups(patternStr);
        results = patternOperator.exeObjectExtractor(seed, matcher, namedGroups);
      }
      if (null != results) {
        dbUtil.saveFeeds(results);
        SpiderUtil.printResult(results);
      }else{
        System.out.println("[INFO] "+seed+"is not matched!");
      }
      // TODO Operate Next Page
      matcher = null;
      List<Pattern> nps = nextPatterns.get(host);
      List<String> nextpages = new ArrayList<String>();
      for (Pattern np : nps) {
        matcher = np.matcher(text);
        if (matcher != null && matcher.find()) {
          matcher.reset();
          final Matcher m = matcher;
          final String crawlingUrl = seed;
          List<String> seeds = patternOperator.exeExtractor(crawlingUrl, m, new GroupHandler<String>() {
            @Override
            public List<String> handle() {
              List<String> results = new ArrayList<String>();
              while (m.find()) {
                String url = SpiderUtil.getAbsoluteUrl(crawlingUrl, m.group(1));
                if (url.contains(host)) {
                  results.add(url);
                }
              }
              return results;
            }
          });
          if (null != seeds) {
            nextpages.addAll(seeds);
          }
        }
      }

      synchronized (crawlingSeeds) {
        crawlingSeeds.remove(0);
        if (null != nextpages) {
          crawlingSeeds.addAll(nextpages);
        }
      }
      crawledSeeds.add(seed);
      Thread.sleep(VISIT_SITE_INTERVAL);
    }
  }

  public static void main(String[] args) {
    try {
      CommonExtractorByConf nce = new CommonExtractorByConf();
      nce.startExtract();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
