package com.cxm.server;

import com.cxm.servlet.WebApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 第一个版本
 *
 */
public class Server {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(8888);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器创建失败");
        }
    }
    public void receive(){
        try {
            Socket client = serverSocket.accept();
            System.out.println("客户端建立连接");
            Request request = new Request(client);
            /**
             * get请求
             */
//            String[] names = request.getParaValues("name");
//            for (String n :names) {
//                System.out.println(n);
//            }

//            InputStream is = client.getInputStream();
//            byte[] b = new byte[1024*1024];
//            int len = is.read(b);
//            String s = new String(b,0,len);
//            System.out.println(s);
//            FileWriter fw = new FileWriter("./http.txt",true);
//            fw.write(s);
//            fw.flush();
//            fw.close();
            /**
             * 响应消息
             */
//            StringBuilder content = new StringBuilder();
//            content.append("<html>");
//            content.append("<head>");
//            content.append("<title>");
//            content.append("服务器响应成功");
//            content.append("</title>");
//            content.append("</head>");
//            content.append("<body>");
//            content.append("终于回来了。。。。。"+request.getParaValue("name"));
//            content.append("</body>");
//            content.append("</html>");
//            int size = content.toString().getBytes().length;
//            StringBuilder responseInfo = new StringBuilder();
//            String blank=" ";
//            String CRLF="\r\n";
//            //响应行
//            responseInfo.append("HTTP/1.1").append(blank).append(200).append(blank).append("OK").append(CRLF);
//            //响应头 之后为空行
//            responseInfo.append("Date:").append(new Date()).append(CRLF);
//            responseInfo.append("Servlet:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
//            responseInfo.append("Content-type:text/html").append(CRLF);
//            responseInfo.append("Content-length:").append(size).append(CRLF);
//            responseInfo.append(CRLF);
//            //响应体
//            responseInfo.append(content.toString());
            Response response = new Response(client);
//            response.print(content.toString());
//            response.pushTOBrowser(200);
            Servlet loginServlet = new LoginServlet();
            loginServlet.service(request,response);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误");
        }
    }
    public void end(){

    }
}
