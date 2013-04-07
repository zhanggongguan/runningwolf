package com.lexin.bean;

import util.MD5Util;

public class Regular {
  private int id;
  private String host;
  private String regular;
  private String createdAt;
  private String md5;

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  private int type;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getRegular() {
    return regular;
  }

  public void setRegular(String regular) {
    this.regular = regular;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getMd5() {
    md5 = MD5Util.encryptToMD5(regular);
    return md5;
  }

}
