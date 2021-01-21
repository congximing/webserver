package com.cxm.server;

import com.cxm.servlet.WebApp;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 第二个版本
 * 通过xml解析来获得页面
 */
public class Server01 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket client = serverSocket.accept();
        Request request = new Request(client);
        Response response = new Response(client);

        String url = request.getUrl();
//        System.out.println(url);
        Servlet servlet = WebApp.getServletFromURL(url.trim());//url.trim()
        if (null != servlet){
            servlet.service(request,response);
        }else {
            //错误页面
            response.pushTOBrowser(404);
        }
    }
}
