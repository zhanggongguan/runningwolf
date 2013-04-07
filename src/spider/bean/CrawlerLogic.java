package spider.bean;

import java.util.List;
import java.util.regex.Pattern;

public class CrawlerLogic {
  private Pattern contextPattern;
  private Pattern seedPattern;
  private List<String> initUrlList;

  public CrawlerLogic(String cPattern, String nPattern, List<String> initUrlList) {
    this.contextPattern = Pattern.compile(cPattern);
    this.seedPattern = Pattern.compile(nPattern);
    this.initUrlList = initUrlList;

  }

  public Pattern getContextPattern() {
    return contextPattern;
  }

  public void setContextPattern(Pattern contextPattern) {
    this.contextPattern = contextPattern;
  }

  public Pattern getSeedPattern() {
    return seedPattern;
  }

  public void setSeedPattern(Pattern seedPattern) {
    this.seedPattern = seedPattern;
  }

  public List<String> getInitUrlList() {
    return initUrlList;
  }

  public void setInitUrlList(List<String> initUrlList) {
    this.initUrlList = initUrlList;
  }

}
