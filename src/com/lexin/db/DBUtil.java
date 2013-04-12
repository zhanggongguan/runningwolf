package com.lexin.db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import spider.util.SpiderUtil;
import util.SpiderConfiguration;
import util.Util;

import com.lexin.bean.Feed;
import com.lexin.bean.Regular;
import com.lexin.bean.RegularTypeEnum;
import com.lexin.bean.Seed;
import com.lexin.bean.SiteProperties;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBUtil {

  private QueryRunner qr;
  private MysqlDataSource ds;
  private Properties prop;
  private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  public DBUtil() throws IOException {
    this.prop = Util.getProperties(SpiderConfiguration.getDBConfigFilePath());
    init();

  }

  public DBUtil(Properties prop) {
    this.prop = prop;
    init();
  }

  private void init() {
    ds = new MysqlDataSource();
    ds.setUrl(prop.getProperty(SpiderConfiguration.DB_URL));
    ds.setUser(prop.getProperty(SpiderConfiguration.DB_USER));
    ds.setPassword(prop.getProperty(SpiderConfiguration.DB_PASSWD));
    qr = new QueryRunner(ds);
  }

  public QueryRunner getQueryRunner() {
    return qr;
  }

  public void save(Feed feed) throws SQLException {
    Object[] params = new Object[12];
    params[0] = feed.getTitle();
    params[1] = feed.getDate();
    params[2] = feed.getAuthor();
    params[3] = feed.getContent();
    params[4] = feed.getHypelink();
    params[5] = feed.getRefer();
    params[6] = feed.getMd5();
    params[7] = feed.getLike();
    params[8] = feed.getUnlike();
    params[9] = feed.getCollect();
    params[10] = feed.getComment();
    params[11] = feed.getType();
    String sql = "replace into feed(title,date,author,content,hypelink,refer,md5,`like`,unlike,collect,"
        + "`comment`,`type`)values(?,?,?,?,?,?,?,?,?,?,?,?)";
    qr.update(sql, params);
  }

  public void save(Seed seed) throws SQLException {
    String sql = "replace into seed(url,crawled_at,created_at)values(?,?,?)";
    Object[] params = new Object[3];
    params[0] = seed.getUrl();
    params[1] = seed.getCrawledAt();
    params[2] = seed.getCreatedAt();
    qr.update(sql, params);
  }

  public void save(Regular regular) throws SQLException {
    String sql = "replace into reguler(host,reguler,created_at,md5,type)values(?,?,?,?,?)";
    Object[] params = new Object[5];
    params[0] = regular.getHost();
    params[1] = regular.getRegular();
    params[2] = regular.getCreatedAt();
    params[3] = regular.getMd5();
    params[4] = regular.getType();
    qr.update(sql, params);
  }

  public void save(SiteProperties sp) throws SQLException {
    String sql = "replace into siteprop(host,encoding)values(?,?);";
    Object[] params = new Object[2];
    params[0] = sp.getHost();
    params[1] = sp.getEncoding();
    qr.update(sql, params);
  }

  public void saveSeeds(List<Seed> seeds) throws SQLException {
    for (Seed seed : seeds) {
      save(seed);
    }
  }

  public String specialSingal(String text) {
    if (null == text || "".equals(text.trim())) {
      return "";
    }
    return text.replace("'", "''").replace("\\", "\\\\");
  }

  public void saveRegulars(List<Regular> regulars) throws SQLException {
    for (Regular regular : regulars) {
      save(regular);
    }
  }

  public List<Seed> getCrawlingSeeds(int interval) throws SQLException {
    String sql = "select id,url,(case when (crawled_at is null or crawled_at='') then now() else crawled_at end) as crawled_at,"
        + "(case when (created_at is null or created_at='') then CURDATE() else created_at end) as created_at,times from seed"
        + " where (times=0 or crawled_at <date_sub(now(), interval "
        + interval
        + " day)) and url is not null and url !='' limit 10;";
    return qr.query(sql, new ResultSetHandler<List<Seed>>() {

      @Override
      public List<Seed> handle(ResultSet rs) throws SQLException {
        List<Seed> seeds = new ArrayList<Seed>();
        while (rs.next()) {
          Seed seed = new Seed();
          seed.setId(rs.getInt("id"));
          seed.setCrawledAt(rs.getString("crawled_at").trim());
          seed.setUrl(rs.getString("url").trim());
          seed.setCreatedAt(rs.getString("created_at").trim());
          seed.setTimes(rs.getInt("times"));
          seeds.add(seed);
        }
        return seeds;
      }

    });
  }

  public List<Regular> getCrawlerRegulars(int type) throws SQLException {
    String sql = "select id,host,reguler,created_at from reguler where type=" + type
        + " and reguler is not null and reguler !='';";
    return qr.query(sql, new ResultSetHandler<List<Regular>>() {
      @Override
      public List<Regular> handle(ResultSet rs) throws SQLException {
        List<Regular> regulars = new ArrayList<Regular>();
        while (rs.next()) {
          Regular regular = new Regular();
          regular.setId(rs.getInt("id"));
          regular.setHost(rs.getString("host"));
          regular.setCreatedAt(rs.getString("created_at"));
          regular.setRegular(rs.getString("reguler").trim());
          regulars.add(regular);
        }
        return regulars;
      }
    });
  }

  public void updateCrawledTimes(List<Seed> seeds) throws SQLException {
    String ids = null;
    for (Seed seed : seeds) {
      String id = String.valueOf(seed.getId());
      if (null == ids) {
        ids = id;
      } else {
        ids = ids + "," + id;
      }
    }
    String sql = "update seed set times=times+1,crawled_at=now() where id in(" + ids + ");";
    qr.update(sql);
  }

  public void saveFeeds(List<Feed> feeds) {
    Object[][] params = new Object[feeds.size()][12];
    for (int i = 0; i < feeds.size(); i++) {
      Feed feed = feeds.get(i);
      params[i][0] = feed.getTitle();
      params[i][1] = feed.getDate();
      params[i][2] = feed.getAuthor();
      params[i][3] = feed.getContent();
      params[i][4] = feed.getHypelink();
      params[i][5] = feed.getRefer();
      params[i][6] = feed.getMd5();
      params[i][7] = feed.getLike();
      params[i][8] = feed.getUnlike();
      params[i][9] = feed.getCollect();
      params[i][10] = feed.getComment();
      params[i][11] = feed.getType();
    }
    String sql = "replace into feed(title,date,author,content,hypelink,refer,md5,`like`,unlike,collect,"
        + "`comment`,`type`)values(?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
      qr.batch(sql, params);
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
  }

  public List<SiteProperties> getSiteProperties() throws SQLException {
    String sql = "select host,encoding from siteprop";
    return qr.query(sql, new ResultSetHandler<List<SiteProperties>>() {
      @Override
      public List<SiteProperties> handle(ResultSet rs) throws SQLException {
        List<SiteProperties> props = new ArrayList<SiteProperties>();
        while (rs.next()) {
          SiteProperties sp = new SiteProperties();
          sp.setHost(rs.getString("host"));
          sp.setEncoding(rs.getString("encoding"));
          props.add(sp);
        }
        return props;
      }
    });
  }

  public static void main(String[] args) {
    try {
      DBUtil db = new DBUtil();
      String sql = "select id,name from content";
      String[] results = db.getQueryRunner().query(sql, new ResultSetHandler<String[]>() {
        @Override
        public String[] handle(ResultSet rs) throws SQLException {
          if (!rs.next()) {
            return null;
          }
          int cols = rs.getMetaData().getColumnCount();
          String[] results = new String[cols];
          for (int i = 0; i < cols; i++) {
            results[i] = rs.getString(i + 1);
          }
          return results;
        }
      });

      for (int i = 0; i < results.length; i++) {
        System.out.println("RESULT[" + i + "]:" + results[i]);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void saveConfiguration(String seedUrl, String encoding, String nextPattern, String contentPattern) {
    String date = sdf.format(new Date());
    String host = SpiderUtil.getHost(seedUrl);
    Seed seed = new Seed();
    seed.setTimes(0);
    seed.setUrl(seedUrl);
    seed.setCreatedAt(date);
    seed.setCrawledAt(date);

    Regular regular = new Regular();
    regular.setCreatedAt(date);
    regular.setHost(host);
    regular.setRegular(contentPattern);
    regular.setType(RegularTypeEnum.CONTENT.ordinal());

    Regular nextpage = new Regular();
    nextpage.setCreatedAt(date);
    nextpage.setHost(host);
    nextpage.setRegular(nextPattern);
    nextpage.setType(RegularTypeEnum.NEXTPAGE.ordinal());

    SiteProperties sp = new SiteProperties();
    sp.setEncoding(encoding);
    sp.setHost(host);
    try {
      save(seed);
      save(nextpage);
      save(regular);
      save(sp);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
