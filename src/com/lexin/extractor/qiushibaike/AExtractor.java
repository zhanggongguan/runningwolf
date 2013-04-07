package com.lexin.extractor.qiushibaike;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.extrator.regex.RegexUtil;
import util.SpiderConfiguration;
import util.Util;
import spider.util.SpiderUtil;

import com.lexin.bean.Feed;
import com.lexin.db.DBUtil;

public class AExtractor {
  private Pattern npp;
  private Pattern cpp;
  private PatternOperator po;
  private List<String> namedGroups;

  private LinkedList<String> seeds;
  private int pageCounter;
  private Properties prop;
  private DBUtil db;
  private HashSet<String> crawledMap;

  public AExtractor() {
    seeds = new LinkedList<String>();
    crawledMap = new HashSet<String>();
  }

  public void init(String start, String cpp, String npp, Properties prop) throws Exception {
    seeds.add(start);
    if (cpp.isEmpty())
      throw new Exception("Your Content Pattern Is NULL,It's Not Do Anything!");
    this.cpp = Pattern.compile(cpp);
    this.namedGroups = RegexUtil.regexPatternNamedGroups(cpp);
    this.po = new PatternOperator();
    this.prop = prop;
    this.db = new DBUtil(prop);
    if (null != npp && !npp.isEmpty()) {
      this.npp = Pattern.compile(npp);
    }
  }

  private String popSeed() {
    String crawingUrl = seeds.pop();
    System.out.println("[INFO] " + crawingUrl + " is popped and being crawled!");
    return crawingUrl;
  }

  public void execute() {
    String crawlingUrl;
    String contentText;
    while (!seeds.isEmpty()) {
      crawlingUrl = popSeed();
      if (crawledMap.contains(crawlingUrl)) {
        continue;
      }
      contentText = Util.downLoadHTML(crawlingUrl);
      if (null == contentText) {
        System.out.println("[INFO] " + crawlingUrl + " is failed!");
        continue;
      }
      Matcher matcher = cpp.matcher(contentText);
      List<Feed> results = po.exeObjectExtractor(crawlingUrl, matcher, namedGroups);
      if (Boolean.valueOf(prop.getProperty(SpiderConfiguration.SAVE_TO_DB))) {
        db.saveFeeds(results);
      } else {
        SpiderUtil.printResult(results);
      }
      if (null == results || results.size() == 0) {
        System.out.println("[INFO] " + crawlingUrl + " is can't be matched!");
      }
      crawledMap.add(crawlingUrl);
      pageCounter++;
      if (null == npp)
        continue;
      Matcher nextPageMatcher = npp.matcher(contentText);
      while (nextPageMatcher.find()) {
        pushSeed(crawlingUrl, nextPageMatcher.group(1));
      }
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void pushSeed(String crawlingUrl, String seed) {
    String nextPageUrl = SpiderUtil.getAbsoluteUrl(crawlingUrl, seed);
    if (null == nextPageUrl || crawledMap.contains(nextPageUrl)) {
      return;
    }
    push(nextPageUrl);
  }

  public int getPageCounter() {
    return this.pageCounter;
  }

  public void push(String str) {
    seeds.push(str);
  }

}
