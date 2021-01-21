package com.cxm.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.logging.SocketHandler;

public class Response {
    private BufferedWriter bw;
    private StringBuilder content;
    private StringBuilder headInfo;
    private int len;//正文字节数
    private String blank=" ";
    private String CRLF="\r\n";

    public Response(Socket client) throws IOException {
        this(client.getOutputStream());
    }
    public Response(OutputStream os){
        bw = new BufferedWriter(new OutputStreamWriter(os));
        content = new StringBuilder();
        headInfo = new StringBuilder();
    }
    //创建头信息
    private void creatHeadInfo(int code){
        //响应行
        headInfo.append("HTTP/1.1").append(blank).append(code).append(blank);
        switch (code){
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(CRLF);
                break;
            case 505:
                headInfo.append("SERVER ERROR").append(CRLF);
                break;
        }
        //响应头
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Servlet:").append("cxm Server/0.0.1;charset=utf-8").append(CRLF);
        headInfo.append("Content-Type:text/html").append(CRLF);
        headInfo.append("Content-Length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }

    //动态添加内容
    public Response print(String info){
        content.append(info);
        this.len += info.getBytes().length;
        return this;
    }

    public void pushTOBrowser(int code) throws IOException {
        if (null == headInfo){
            code = 505;
        }
        creatHeadInfo(code);
        bw.append(headInfo);//BufferedReader append();方法的使用？？？
        bw.append(content);
        bw.flush();
    }
}
