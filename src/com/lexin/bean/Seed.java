package com.lexin.bean;

public class Seed {
  private int id;
  private String url;
  private String crawledAt;
  private String createdAt;
  private int times;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCrawledAt() {
    return crawledAt;
  }

  public void setCrawledAt(String crawledAt) {
    this.crawledAt = crawledAt;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public int getTimes() {
    return times;
  }

  public void setTimes(int times) {
    this.times = times;
  }
  @Override
  public String toString() {
    String lineSeparator = System.getProperty("line.separator");
    StringBuffer sb=new StringBuffer(lineSeparator);
    sb.append("ID:"+id+lineSeparator);
    sb.append("URL:"+url+lineSeparator);
    sb.append("CRAWLED_AT:"+crawledAt+lineSeparator);
    sb.append("CREATED_AT:"+createdAt+lineSeparator);
    sb.append("TIMES:"+times+lineSeparator);
    return sb.toString();
  }

}
