package com.cxm.server;

import com.cxm.servlet.WebApp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 */
public class Dispatcher implements Runnable{

    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client){
        this.client = client;
        try {
            request=new Request(client);
            response=new Response(client);
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
    }
    @Override
    public void run() {
        try {
            String url = request.getUrl().trim();
            //url为空，返回主页
            if (url.equals("/") || url== null){
                //返回主页
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("in.html");
                byte[] b = new byte[1024 * 1024];
                is.read(b);
                response.print(new String(b));
                response.pushTOBrowser(200);
                is.close();
                return;
            }
            Servlet servlet = WebApp.getServletFromURL(url);
            //到指定页面
            if (null!=servlet){
                servlet.service(request,response);
            }else{
                //未找到 写页面
//                response.pushTOBrowser(404);
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("404.html");
                byte[] bytes = new byte[1024 * 1024];
                is.read(bytes);
                response.print(new String(bytes));
                response.pushTOBrowser(404);
                is.close();
            }
        }catch (Exception e){
            try {
                response.pushTOBrowser(500);//代码错误 写页面
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    private void release(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
