package spider.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UrlQueue {
  private static final int INIT_QUEUE_SIZE = 20;
  private ArrayList<UrlSeed> urlQueue = new ArrayList<UrlSeed>(INIT_QUEUE_SIZE);

  public void sort() {
    Collections.sort(urlQueue);
  }

  public UrlSeed pop() {
    if (isEmpty()) {
      return null;
    }
    UrlSeed urlSeed = urlQueue.get(0);
    urlQueue.remove(urlSeed);
    return urlSeed;
  }

  public synchronized void put(UrlSeed urlSeed) {
    urlQueue.add(urlSeed);
    sort();
  }

  public synchronized void put(List<UrlSeed> urlSeeds) {
    urlQueue.addAll(urlSeeds);
    sort();
  }

  public boolean isEmpty() {
    return !(urlQueue.size() > 0);
  }
}
