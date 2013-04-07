package spider.extrator.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.extrator.handler.BeanRegexResultHandler;

public class RegexOP<T> {
  private Pattern pattern;

  public RegexOP(Pattern pattern) {
    this.pattern = pattern;
  }

  public List<T> matchToBeanList(String context, BeanRegexResultHandler<T> beanHandler) {
    List<T> result = new ArrayList<T>();
    Matcher matcher = pattern.matcher(context);
    while (matcher.find()) {
      T t = beanHandler.handle(matcher);
      if (null != t)
        result.add(t);
    }
    return result;
  }

  public T matchToBeanObject(String context, BeanRegexResultHandler<T> beanHandler) {
    Matcher matcher = pattern.matcher(context);
    if (matcher.find()) {
      return beanHandler.handle(matcher);
    }
    return null;
  }
}
