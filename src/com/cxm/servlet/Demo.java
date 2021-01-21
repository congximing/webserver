package com.cxm.servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

/**
 * 测试
 */
public class Demo {
    public static void main(String[] args) throws Exception{
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();

        XmlHandler handler = new XmlHandler();
        saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/cxm/servlet/web.xml"),handler);

        /**
         * 获得容器数据
         */
        WebContent content = new WebContent(handler.getEntities(), handler.getMappings());
        String clz = content.getClz("/g");
//        Class<?> clazz = Class.forName(clz);
//        System.out.println(clz);
//        Servlet servlet = (Servlet) clazz.getConstructor().newInstance();
//        Servlet servlet = (Servlet) clazz.newInstance();
//        servlet.service();
    }
}
