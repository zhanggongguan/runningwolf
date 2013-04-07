package com.lexin.extractor.qiushibaike;

import java.io.IOException;
import java.util.Properties;

import util.SpiderConfiguration;
import util.Util;

public class FFExtractor extends AExtractor {
  public static void main(String[] args) throws IOException {
    Properties prop = Util.getProperties(SpiderConfiguration.getDBConfigFilePath());
    String npp = "\"next\":\\s*\\{\"max_behot_time\":\\s*(\\d+)\\}";
    String cpp = "\"text\":\\s*\"(?<content>[^\"]+?)\",\\s*\"essay_user\":\\s*\\{[^\\}]+?\"name\":\\s*\"(?<author>[^\"]+?)\"\\},\\s*"
        + "\"datetime\":\\s*\"(?<date>[^\"]+?)\",[^\\}]+?\"favorite_count\":\\s*(?<like>\\d+),\\s*\"bury_count\":\\s*(?<unlike>\\d+),[^\\}]+?"
        + "\"comments_info\":\\s*\\[.+?\\],[^\\}]+?\"digg_count\":\\s*(?<collect>\\d+),[^\\}]+?\"comments_count\":\\s*(?<comment>\\d+),[^\\}]+?}";
    String startUrl = "";
    startUrl = "http://feifei.com/api/essay/recent/recent?callback=jQuery172015851923487585862_1354776122081&tag=joke&count=20&max_behot_time=1356862422&offset=0&_=1354776374268";
    startUrl = "http://feifei.com/api/essay/top/?callback=jQuery17202383930181162227_1354877401153&tag=joke&top_type=day&count=20&offset=0&_=1354877407713";
    startUrl="http://feifei.com/api/essay/top/?callback=jQuery1720327866169176083_1354877621162&tag=joke&top_type=week&count=20&offset=0&_=1354877622785";
    FFExtractor ff = new FFExtractor();
    try {
      ff.init(startUrl, cpp, npp, prop);
      ff.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void pushSeed(String crawlingUrl, String seed) {
    long curTime = System.currentTimeMillis();
    long oneMinuteAgo = curTime - 1000 * 120;
    crawlingUrl = crawlingUrl.replaceFirst("_\\d+", String.valueOf(oneMinuteAgo));
    crawlingUrl = crawlingUrl.replaceFirst("max_behot_time=\\d+", "max_behot_time=" + seed);
    crawlingUrl = crawlingUrl.replaceFirst("_=\\d+", "_=" + String.valueOf(curTime));
    System.out.println(crawlingUrl);
    super.push(crawlingUrl);
  }
}
