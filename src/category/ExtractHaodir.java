package category;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import bean.Page;

import util.Util;

public class ExtractHaodir {
  private static final String haodir = "http://www.haodir.net/";

  public void extractPage(Pattern pp, Pattern np, String url, List<Page> pages) {
    String html = Util.downLoadHTML(url);
    Page page = new Page(pp, np);
    page.setHtml(html);
    page.parse();
    pages.add(page);
    if (null != page.getNextPage())
      extractPage(pp, np, haodir + page.getNextPage(), pages);
  }

  public static void main(String[] args) {
    new ExtractHaodir().parse();
  }

  public void parse() {
    String pattern = "<dl class=\"clear catebox\">\\s+<dt>(<a[^<>]+?>[^<>]+?</a>)</dt>\\s+<dd><ul[^<>]+?>(.+?)</ul></dd>\\s+</dl>";

    Util util = new Util();
    Pattern p = Pattern.compile(pattern);

    Pattern np = Pattern.compile("<a href=\"([^\"]+?)\">下一页</a>");
    Pattern pp = Pattern
        .compile("<li><a[^<>]+?><img[^<>]+?></a><strong><a[^<>]+?>([^<>]+?)</a></strong>.+?<address><span\\sclass=\"green\">([^<>]+?)</span>.+?</address></li>");

    List<List<String>> items = Util.extract(p, haodir);
    for (List<String> item : items) {
      String category1 = util.extractHypeLink(item.get(0)).get(0).get(1);
      for (int i = 1; i < item.size(); i++) {
        List<List<String>> links = util.extractHypeLink(item.get(i));
        for (List<String> link : links) {
          String category2 = link.get(1);
          List<Page> pages = new ArrayList<Page>();
          extractPage(pp, np, haodir + link.get(0), pages);
          for (Page page : pages) {
            if (null == page.getContentItems() || page.getContentItems().size() == 0)
              continue;
            for (List<String> citems : page.getContentItems()) {
              System.out.print(category1 + " " + category2 + " ");
              for (String citem : citems) {
                System.out.print(citem + " ");
              }
              System.out.println();
            }
          }
        }
      }
    }
  }
}
