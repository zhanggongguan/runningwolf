<%@ page contentType="text/html; charset=UTF-8"
	import="com.lexin.db.DBUtil"
	import="com.lexin.bean.Seed"
	import="java.util.*"
	import="javax.servlet.*"
  import="javax.servlet.http.*"
	import="com.lexin.bean.Feed"

	%>
	<html>
					<head>
									<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
									<%
									String seed=request.getParameter("seed");	
									String nextPagePattern=request.getParameter("nextpage");
									String contentPattern=request.getParameter("content");
									String encoding=request.getParameter("encoding");
									String flag=request.getParameter("flag");
									if(seed==null){
										seed="";
									}
									if(nextPagePattern==null){
										nextPagePattern="";
									}
									if(contentPattern==null){
										contentPattern="";
									}
									if(null==encoding){
										encoding="utf-8";
									}
									if(null==flag){
										flag="0";
									}
									
									
									%>
									<script type="text/javascript">
													function save(){
																	document.getElementById('flag').value='1';
																	document.getElementById('form1').submit();

													}
									
									</script>
					</head>
					<body style="font-size:12px">
									<form name="form1" id="form1" action="/crawler.jsp" method="post"> 
									种子链接：
									<input type="text" name="seed" size="30" value="<%=seed%>"/>&nbsp;&nbsp;网站编码：<input type="text" name="encoding" size="30" value="<%=encoding%>"/><br/><br/>
									种子正则：<br/>
									<textarea name="nextpage" cols="80" rows="3"><%=nextPagePattern%></textarea><br/>例如：href="(.+?)"<br/>
									内容正则：<br/>
									<textarea name="content" cols="80" rows="10"><%=contentPattern%></textarea><br/>
									例如：href="(?&lt;title&gt;.+?)"<br/><br/>
									<input id="flag" type="hidden" name="flag" value=0 />
									<input type="submit" name="testbutton" value="测试正则"/>&nbsp;&nbsp;<input type="button" name="savebutton" value="保存入库" onclick="javascript:save();"/>
									<br/>
									<br/>
									<br/>
									<br/>
									<%
									if(!"".equals(seed)&&!"".equals(nextPagePattern)&&!"".equals(contentPattern)){
										if("0".equals(flag)){
										int i=0;
										String text=util.Util.downLoadHTML(seed, encoding);
										List<String> seedUrls=spider.util.SpiderUtil.testNextPage(seed,text,nextPagePattern);
										%>
										一、共匹配<%=seedUrls.size()%>个种子链接:<br/>
										<%
										int j=0;
										for(String urlStr:seedUrls){
											j++;
										%>
										<%=j%>、<%=urlStr%><br/>
										<%}	%>
										<br/>
										<%
										List<Feed> testFeeds=spider.util.SpiderUtil.testRegular(seed,text,contentPattern);
										%>
										二、共匹配<%=testFeeds.size()%>个结果:<br/>
										<%
											for(Feed feed:testFeeds){
											i++;
									%>
									<font color="green">ID:<%=i%></font><br/>
									Title:<%=feed.getTitle()%><br/>
									Date:<%=feed.getDate()%><br/>
									Author:<%=feed.getAuthor()%><br/>
									HyperLink:<%=feed.getHypelink()%><br/>
									Refer:<%=feed.getRefer()%><br/>
									Like:<%=feed.getLike()%><br/>
									Unlike:<%=feed.getUnlike()%><br/>
									Collect:<%=feed.getCollect()%><br/>
									Comment:<%=feed.getComment()%><br/>
									Type:<%=feed.getType()%><br/>
									Content:<%=feed.getContent()%><br/>
									MD5:<%=feed.getMd5()%><br/>
									<br/>
									<%}}
										if("1".equals(flag)){
											DBUtil dbUtil=new DBUtil();
											dbUtil.saveConfiguration(seed,encoding,nextPagePattern,contentPattern);
											System.out.println("INFO save to DB!");
										}
									}
									%>

					</form>
					</body>
	</html>
