package bean;

import java.util.List;
import java.util.regex.Pattern;

import util.Util;

public class Page {
  private String title;
  private String chareset;
  private String html;
  private List<List<String>> contentItems;
  private String nextPage;

  private Pattern patternItems;
  private Pattern nextPagePattern;

  public static final Pattern tp = Pattern.compile("<title>([^<>]+?)</title>");
  public static final Pattern cp = Pattern
      .compile("<meta[^><]+?content=[^\"]*?charset=([^\"]+?);?\\s*[^\"]+?\"[^><]+?>");

  public Page(Pattern pp, Pattern np) {
    this.patternItems = pp;
    this.nextPagePattern = np;
  }

  public void parse() {
    if (null == html)
      return;
    this.setTitle(Util.extractSingalGroup(tp, html));
    this.setChareset(Util.extractSingalGroup(cp, html));
    if (null != patternItems)
      this.setContentItems(Util.extractContent(patternItems, html));
    if (null != nextPagePattern)
      this.setNextPage(Util.extractSingalGroup(nextPagePattern, html));

  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getChareset() {
    return chareset;
  }

  public void setChareset(String chareset) {
    this.chareset = chareset;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  public List<List<String>> getContentItems() {
    return contentItems;
  }

  public void setContentItems(List<List<String>> contentItems) {
    this.contentItems = contentItems;
  }

  public String getNextPage() {
    return nextPage;
  }

  public void setNextPage(String nextPage) {
    this.nextPage = nextPage;
  }

}
