package com.cxm.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 终极版版（正在改了。。。）
 * 添加了Dispatcher
 */
public class Server02 {
    private ServerSocket serverSocket;
    private boolean isRunning;
    public static void main(String[] args) throws Exception {
        Server02 server = new Server02();
        server.start();
    }
    public void start(){
        try {
            serverSocket=new ServerSocket(8888);
            isRunning=true;
            while (true){
                receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void receive(){
        try {
            Socket client = serverSocket.accept();
            new Thread(new Dispatcher(client)).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接失败");
        }
    }
    private void stop(){
        isRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
