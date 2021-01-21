package com.cxm.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

public class Request {
    private String requestInfo;
    private String method;
    private String url;
    private String queryStr;
    private Map<String,List<String>> paramMap;

    /**
     * 为什么怎加方法不执行？？？？
     * @param client
     * @throws IOException
     */
    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }
    public Request(InputStream is){
        byte[] b = new byte[1024*1024];
        paramMap = new HashMap<String, List<String>>();
        try {
            int len = is.read(b);
            this.requestInfo = new String(b,0,len);
//            System.out.println(this.requestInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseRequestInfo();
    }

    /**
     * 解析请求数据
     */
    private void parseRequestInfo(){
        this.method = this.requestInfo.substring(0,this.requestInfo.indexOf("/")).toLowerCase().trim();
        System.out.println("请求方式--->"+this.method);

        int start = this.requestInfo.indexOf("/");
        int end = this.requestInfo.indexOf("HTTP/1.1");
        this.url = this.requestInfo.substring(start,end);
        System.out.println("url--->"+this.url);
        int i = this.url.indexOf("?");
        /**
         * get获取参数的方法
         */
        if (i>0){
            String[] urlArry = this.url.split("\\?");//转移字符
            this.url = urlArry[0];
            System.out.println("url--->"+this.url);
            this.queryStr = urlArry[1];
        }
        /**
         * post请求方式
         */
        if (this.method.equals("post")){
            String qStr = requestInfo.substring(requestInfo.lastIndexOf("\r\n"));
            if (qStr != null){
                if (queryStr == null){
                    queryStr = qStr;
                }else {
                    queryStr += "&" + qStr;
                }
                queryStr = null == queryStr?"":queryStr;
                System.out.println("参数--->"+queryStr.trim());
                convertMap();
            }
        }
    }

    /**
     * 解决中文问题
     * @param value
     * @param encode
     * @return
     */
    private String decode(String value,String encode){
        try {
            return java.net.URLDecoder.decode(value,encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 封装为Map
     */
    private void convertMap(){
        String[] keyValues = queryStr.split("&");
        for (String k : keyValues) {
            String[] kv = k.split("=");
            kv = Arrays.copyOf(kv, 2);// name= &name1=ccc  的情况

            String key = kv[0].trim();
            String value= kv[1] == null? null:decode(kv[1],"utf-8");
            if (!paramMap.containsKey(key)) {
                paramMap.put(key, new ArrayList<String>());
            }
                paramMap.get(key).add(value);
        }
    }

    public String[] getParaValues(String key){
        List<String> values = paramMap.get(key);
        if (values.size() <1 || values == null){
            return null;
        }else {
            return values.toArray(new String[0]);
        }
    }
    public String getParaValue(String key){
        return paramMap.get(key)==null? null:paramMap.get(key).get(0);
    }

    public String getUrl() {
        return url;
    }
}
