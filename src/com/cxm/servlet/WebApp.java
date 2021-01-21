package com.cxm.servlet;

import com.cxm.server.Servlet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class WebApp {
    private static XmlHandler handler;
    private static WebContent webContent;
    static {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            handler = new XmlHandler();
            saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/cxm/servlet/web.xml"),handler);
            webContent = new WebContent(handler.getEntities(), handler.getMappings());
        }catch (Exception e){
            System.out.println("解析错误");
        }
    }

    public static Servlet getServletFromURL(String url){
        String clz = webContent.getClz(url);
        try {
            Class<?> clzz = Class.forName(clz);
            Servlet servlet = (Servlet) clzz.newInstance();
            return servlet;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("url 为空");
        }
        return null;
    }
}
