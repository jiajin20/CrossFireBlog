package cn.gzy;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.awt.*;
import java.io.File;
import java.net.URI;

@Slf4j
public class TomcatServer {
  public static void main(String[] args) throws Exception {
    log.info("服务启动中...");
    // 端口和上下文路路径
    int port = 8080;//Integer.parseInt(GlobalConfig.PROP.getProperty("server.port", "8080"));
    String path = "/";//GlobalConfig.PROP.getProperty("server.context.path", "/afa");
    log.info("path={} , port={}", path, port);
    Tomcat tomcat = new Tomcat();
    tomcat.setHostname("0.0.0.0");
    // 端口监听
    Connector connector = tomcat.getConnector();
    connector.setPort(port);

    // WebContent 的名称要与打包的名称对上，使用当前路径
    String dir = System.getProperty("user.dir");
    log.info("dir : {}", dir);
    String WebContent = dir + File.separator + "WebContent";
    log.info("WebContent : {}", WebContent);
    tomcat.setBaseDir(WebContent);
    tomcat.addWebapp(path, WebContent);

    // 启动
    tomcat.start();

    // 服务连接
    tomcat.getService().addConnector(connector);
    String url = "http://localhost:" + port + path + "index1.html";
    log.info("web: {}", "http://localhost:" + port + path + "guanwang/index1.html");

    // 打开浏览器
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      Desktop.getDesktop().browse(new URI("http://localhost:" + port + path + "guanwang/index1.html"));
    } else {
      log.warn("无法打开浏览器，请手动访问: {}", url);
    }
    tomcat.getServer().await();
  }
  }
