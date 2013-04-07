package com.lexin.extractor.qiushibaike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import bean.Seed;

import util.SpiderConfiguration;
import util.Util;

public class CommonExtractor {
  private List<Seed> seeds;
  private List<ExtractThread> crawlingMap;
  private Properties dbProp;
  
  public CommonExtractor() {

  }

  public void init() throws IOException {
    seeds = new ArrayList<Seed>();
    Properties prop = Util.getProperties(SpiderConfiguration.getEXConfigFilePath());
    dbProp = Util.getProperties(SpiderConfiguration.getDBConfigFilePath());
    crawlingMap = new ArrayList<ExtractThread>(4);
    for (Entry<Object, Object> seed : prop.entrySet()) {
      Seed seedObject = getSeed(seed.getValue().toString());
      if (null != seedObject)
        seeds.add(seedObject);
    }
  }

  public Seed getSeed(String line) {
    String[] seedFields = line.trim().split(" ");
    if (seedFields.length < 3) {
      return null;
    }
    Seed seed = new Seed();
    seed.setSiteUrl(seedFields[0]);
    seed.setNextPattern(seedFields[1]);
    seed.setContentPattern(seedFields[2]);
    return seed;
  }

  public void startExtract() throws InterruptedException {
    while (true) {
      if (crawlingMap.size() >= 4 || (seeds.size() == 0 && crawlingMap.size() > 0)) {
        Thread.sleep(5000);
        for (int i = 0; i < crawlingMap.size(); i++) {
          ExtractThread et = crawlingMap.get(i);
          if (et.getState() == Thread.State.NEW) {
            et.start();
          } else if (et.getState() == Thread.State.TERMINATED) {
            crawlingMap.remove(et);
          }
        }
      }
      if (seeds.size() > 0 && crawlingMap.size() < 4) {
        for (int i = 0; i < seeds.size(); i++) {
          Seed seed = seeds.get(i);
          ExtractThread et = new ExtractThread(seed, this.dbProp);
          crawlingMap.add(et);
          seeds.remove(seed);
        }
      }
      if (seeds.size() == 0 && crawlingMap.size() == 0) {
        System.out.println("ALL TASK IS COMPLETED!");
        break;
      }
    }
  }

  public static void main(String[] args) throws IOException {
    CommonExtractor ce = new CommonExtractor();
    ce.init();
    try {
      ce.startExtract();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  class ExtractThread extends Thread {
    private Seed seed;
    private AExtractor aExtractor;

    public ExtractThread(Seed seed, Properties prop) {
      this.seed = seed;
      this.aExtractor = new AExtractor();
      try {
        this.aExtractor.init(seed.getSiteUrl(), seed.getContentPattern(), seed.getNextPattern(), prop);
      } catch (Exception e) {
        super.interrupted();
        seed.setCrawlingStatus(-1);
        e.printStackTrace();
      }
    }

    @Override
    public void run() {
      aExtractor.execute();
      seed.setCrawlingStatus(1);
    }

    public Seed getSeed() {
      return seed;
    }

    public void setSeed(Seed seed) {
      this.seed = seed;
    }

  }
}
