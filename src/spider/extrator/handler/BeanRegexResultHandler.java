package spider.extrator.handler;

import java.util.regex.Matcher;

public interface BeanRegexResultHandler<T> {
  public T handle(Matcher mr);
}
