package spider.extrator.handler;

import java.util.List;

public interface SaveResultHandler<T> {
  public void handle(List<T> results);
}
