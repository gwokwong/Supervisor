package com.sttech.supervisor.crash;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Thread2 implements Runnable {
    String name;

    public Thread2(String name) {
        this.name = name;
    }


    @Override
    public void run() {
        String host = "192.168.1.38";  //要连接的服务端IP地址
        int port = 50006;   //要连接的服务端对应的监听端口
        //与服务端建立连接
        Socket client;
        try {
            Log.i("socket_client", "开始连接服务器");
            client = new Socket(host, port);
            Log.i("socket_client", "连接服务器成功");

            OutputStream os = client.getOutputStream();//字节输出流
            PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
            Log.i("socket_client", name);
            pw.write(name);
            pw.flush();
            client.shutdownOutput();//关闭输出流
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket_client", e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket_client", e.getMessage());
        }

    }
}