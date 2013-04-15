package com.lexin.extractor.qiushibaike;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import spider.util.SpiderUtil;
import util.Util;

import com.lexin.bean.Feed;
import com.lexin.bean.Seed;

public class PatternOperator {
  public <T> List<T> exeExtractor(String crawlingUrl, List<String> namedGroups, Matcher matcher, GroupHandler<T> handler) {
    List<T> results = handler.handle();
    return results;
  }

  public <T> List<T> exeExtractor(String crawlingUrl, Matcher matcher, GroupHandler<T> handler) {
    List<T> results = handler.handle();
    return results;
  }

  public List<Feed> exeObjectExtractor(final String crawlingUrl, final Matcher matcher, final List<String> namedGroups) {
    return exeExtractor(crawlingUrl, namedGroups, matcher, new GroupHandler<Feed>() {

      @Override
      public List<Feed> handle() {
        List<Feed> feeds = new ArrayList<Feed>();
        HashMap<String, Method> methods = new HashMap<String, Method>();
        Method[] feedMethods = Feed.class.getDeclaredMethods();
        for (int i = 0; i < feedMethods.length; i++) {
          Method method = feedMethods[i];
          methods.put(method.getName().toLowerCase(), method);
        }
        try {
          while (matcher.find()) {
            Feed feed = new Feed();
            for (String group : namedGroups) {
              Method method = methods.get("set" + group.toLowerCase());
              if (null != method) {
                method.invoke(feed, new Object[] { matcher.group(group) });
              }
            }
            feed.setHypelink(crawlingUrl);
            feeds.add(feed);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return feeds;
      }
    });
  }

  public final List<Seed> exeExtractor(final String crawlingUrl, final String host, final Matcher matcher) {
    GroupHandler<Seed> groupHandler = new GroupHandler<Seed>() {
      @Override
      public List<Seed> handle() {
        List<Seed> results = new ArrayList<Seed>();
        while (matcher.find()) {
          String seedURL = SpiderUtil.getAbsoluteUrl(crawlingUrl, matcher.group(1));
          if (seedURL.contains(host)) {
            seedURL = SpiderUtil.defaultHttpProctol(seedURL);
            if (null == seedURL) {
              continue;
            }
            Seed seed = new Seed();
            seed.setCrawledAt(Util.getCurrentDate());
            seed.setCreatedAt(Util.getCurrentDateTime());
            seed.setUrl(seedURL);
            results.add(seed);
          }
        }
        return results;
      }
    };
    List<Seed> seeds = this.exeExtractor(crawlingUrl, matcher, groupHandler);
    return seeds;
  }

  public final List<String> exeExtractor(String crawlingUrl, final Matcher matcher) {
    return exeExtractor(crawlingUrl, null, matcher, new GroupHandler<String>() {
      List<String> results = new ArrayList<String>();

      @Override
      public List<String> handle() {
        while (matcher.find()) {
          results.add(matcher.group(1));
        }
        return results;
      }
    });
  }
}
