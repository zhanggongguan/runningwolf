package com.lexin.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jasper.servlet.JspServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.servlet.ServletHolder;

public class HttpServer {
  private final static Log LOG=LogFactory.getLog(HttpServer.class);
  public static void main(String[] args) {
    HttpServer hs = new HttpServer();
    hs.start();
  }

  public void start() {
    LOG.info("Initializing server...");
    final Server server = new Server(8080);
    final Context context = new Context(server, "/", Context.SESSIONS);
    context.setResourceBase("webapps");
    context.setClassLoader(Thread.currentThread().getContextClassLoader());
    context.addServlet(DefaultServlet.class, "/");
    final ServletHolder jsp = context.addServlet(JspServlet.class, "*.jsp");
    jsp.setInitParameter("classpath", context.getClassPath());
    try {
      server.setHandler(context);
      server.start();
    } catch (Exception e) {
      LOG.error("Failed to start server!");
      return;
    }

    LOG.info("Server running...");
  }
}
