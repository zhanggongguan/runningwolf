package util;

public class SpiderConfiguration {
  public static final String DB_CONFIG = "/conf/db.properties";
  public final static String EX_CONFIG = "/conf/extractor.properties";
  public static final String SAVE_TO_DB = "result.save.db";
  public static final String DB_URL = "db.url";
  public static final String DB_USER = "db.user";
  public static final String DB_PASSWD = "db.password";

  public static String getDBConfigFilePath() {
    return System.getProperty("user.dir") + DB_CONFIG;
  }

  public static String getEXConfigFilePath() {
    return System.getProperty("user.dir") + EX_CONFIG;
  }
}
