package spider.bean;

import spider.util.SpiderUtil;

public class UrlSeed implements Comparable<UrlSeed> {
  private String link;
  private String linkText;
  private int layer = Integer.MAX_VALUE;
  private boolean isCrawled;

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getLinkText() {
    return linkText;
  }

  public void setLinkText(String linkText) {
    this.linkText = linkText;
  }

  public int getLayer() {
    return layer;
  }

  public void setLayer(int layer) {
    this.layer = layer;
  }

  public boolean isCrawled() {
    return isCrawled;
  }

  public void setCrawled(boolean isCrawled) {
    this.isCrawled = isCrawled;
  }

  @Override
  public int compareTo(UrlSeed urlSeed) {
    return Integer.valueOf(this.layer).compareTo(urlSeed.layer);
  }

  @Override
  public String toString() {
    return SpiderUtil.getURLPathPattern(link) + "\t" + link.trim() + "\t" + linkText.trim() + "\t" + layer;
  }
}
