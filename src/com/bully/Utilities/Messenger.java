package com.bully.Utilities;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.io.DataOutputStream;

public class Messenger {
    public static void message(String mes) throws IOException {
        Socket socketMain = new Socket(InetAddress.getLocalHost(), 12345);
        DataOutputStream dataStream = new DataOutputStream(socketMain.getOutputStream());
        dataStream.writeUTF(mes);
        socketMain.close();
    }

}