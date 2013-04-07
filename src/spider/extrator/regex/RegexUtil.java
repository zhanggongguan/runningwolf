package spider.extrator.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
  private static final String GROUP_PATTERN_STR = "\\(\\?<([^>]+?)>[^\\)]+?\\)";
  private static final Pattern GROUP_PATTERN = Pattern.compile(GROUP_PATTERN_STR);

  public static List<String> regexPatternNamedGroups(String pattern) {
    List<String> namedGroups = new ArrayList<String>();
    Matcher matcher = GROUP_PATTERN.matcher(pattern);
    while (matcher.find()) {
      namedGroups.add(matcher.group(1));
    }
    return namedGroups;
  }
}
