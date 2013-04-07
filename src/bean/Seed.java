package bean;

import java.util.List;

public class Seed {
  private String nextPattern;
  private String contentPattern;
  private String siteUrl;
  private int crawlingStatus;

  private List<String> nextPaterns;
  private List<String> contentPatterns;
  public String getNextPattern() {
    return nextPattern;
  }
  
  public void setNextPattern(String nextPattern) {
    this.nextPattern = nextPattern;
  }

  public String getContentPattern() {
    return contentPattern;
  }

  public void setContentPattern(String contentPattern) {
    this.contentPattern = contentPattern;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public int getCrawlingStatus() {
    return crawlingStatus;
  }

  public void setCrawlingStatus(int crawlingStatus) {
    this.crawlingStatus = crawlingStatus;
  }

  public List<String> getNextPaterns() {
    return nextPaterns;
  }

  public void setNextPaterns(List<String> nextPaterns) {
    this.nextPaterns = nextPaterns;
  }

  public List<String> getContentPatterns() {
    return contentPatterns;
  }

  public void setContentPatterns(List<String> contentPatterns) {
    this.contentPatterns = contentPatterns;
  }
  
}
