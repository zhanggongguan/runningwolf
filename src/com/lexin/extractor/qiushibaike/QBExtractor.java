package com.lexin.extractor.qiushibaike;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import util.SpiderConfiguration;
import util.Util;

public class QBExtractor extends AExtractor {

  public void pushSeed(String crawlingUrl, String seed) {
    URI uri;
    try {
      uri = new URI(crawlingUrl);
      super.push(uri.resolve(seed).toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) throws IOException {
    Properties prop = Util.getProperties(SpiderConfiguration.getDBConfigFilePath());
    String npp_qiushibaike = "<a\\shref=\"([^\"]+?)\">下一页\\s*</a>";
    String cpp_qiushibaike = "(?:<br/>(?:<a[^>]+?>)?([^>]+?)(?:</a>)?<br/>)?<br/>([^>]{16,})<br/>.*?<br/><img\\ssrc=\"/static/qiushi/good.gif[^>]+?/><a[^>]+?>(\\d+)</a>[^>]+?<img\\ssrc=\"/static/qiushi/bad.gif[^>]+?><a[^>]+?>-(\\d+)</a>[^>]+?<a[^>]+?>(\\d+)[^\\d>]+?</a><br/>";
    String startUrl = "http://www.qiushibaike.com/month/page/807/?s=4520575";
    startUrl = "http://www.qiushibaike.com/history/2007/1/9";
    cpp_qiushibaike = "<div\\sclass=\"block\\suntagged\"[^\"]+?>\\s*<div\\sclass=\"detail\">\\s*<a[^>]+?>(?<title>[^>]+?)</a>\\s*</div>\\s*<div\\sclass=\"content\"\\stitle=\"(?<date>[^\"]+?)\">(?<content>.+?)</div>\\s*<div[^>]+?class=\"bar\">\\s*<ul>\\s*<li[^>]+?class=\"up\">\\s*<a[^>]+?>(?<like>[^>]+?)</a>\\s*</li>\\s*<li[^>]+?class=\"down\">\\s*<a[^>]+?>(?<unlike>[^>]+?)</a>\\s*</li>\\s*<li\\sclass=\"comment\">\\s*<a[^>]+?class=\"qiushi_comments\"[^>]+?>(?<comment>[^>]+?)</a>\\s*</li>";
    QBExtractor qbe = new QBExtractor();
    try {
      qbe.init(startUrl, cpp_qiushibaike, npp_qiushibaike, prop);
      qbe.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
