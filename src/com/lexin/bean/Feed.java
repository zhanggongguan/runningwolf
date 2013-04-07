package com.lexin.bean;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.MD5Util;
import util.Util;

public class Feed {
  private String title;
  private String date;
  private String author;
  private String content;
  private String hypelink;
  private String refer;
  private String md5;
  private String like;
  private String unlike;
  private String collect;
  private String comment;
  private String type;
  private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = Util.fromEncodedUnicode(title);
  }

  public String getDate() {
    if (null == date) {
      date = sdf.format(new Date());
    }
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    if (null == author || "".equals(author)) {
      this.author = "";
    } else {
      this.author = Util.fromEncodedUnicode(author);
    }
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = Util.fromEncodedUnicode(content);
    this.md5 = MD5Util.encryptToMD5(content);
  }

  public String getHypelink() {
    return hypelink;
  }

  public void setHypelink(String hypelink) {
    this.hypelink = hypelink;
    try {
      URL url = new URL(hypelink);
      this.refer = url.getHost();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  public String getLike() {
    return like;
  }

  public void setLike(String like) {
    this.like = like;
  }

  public String getUnlike() {
    return unlike;
  }

  public void setUnlike(String unlike) {
    this.unlike = unlike;
  }

  public String getCollect() {
    return collect;
  }

  public void setCollect(String collect) {
    this.collect = collect;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    String lineSeparator = System.getProperty("line.separator");
    StringBuffer sb = new StringBuffer();
    sb.append("Title:" + title + lineSeparator);
    sb.append("Crawled Date:" + date + lineSeparator);
    sb.append("Author:" + author + lineSeparator);
    sb.append("Content:" + content + lineSeparator);
    sb.append("Hypelink:" + hypelink + lineSeparator);
    sb.append("Like:" + like + lineSeparator);
    sb.append("Unlike:" + unlike + lineSeparator);
    sb.append("Collect:" + collect + lineSeparator);
    sb.append("Comment:" + comment + lineSeparator);
    sb.append("Refer:" + refer + lineSeparator);
    sb.append("MD5:" + md5 + lineSeparator);
    sb.append(lineSeparator);
    return sb.toString();
  }

  public String getRefer() {
    return refer;
  }

  public void setRefer(String refer) {
    this.refer = refer;
  }

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
