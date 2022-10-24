package com.bully;
import java.net.Socket;
import java.io.*;
import com.bully.UI.MainFrame;

public class Server extends Thread {
    private MainFrame frame ;
    private Socket socket;

    public Server(Socket clientSocket, MainFrame frame) {
        this.frame = frame ;
        this.socket = clientSocket;
    }

    public void run() {
        try {
            DataInputStream dos = new DataInputStream(socket.getInputStream());
            frame.addLogsOutput(dos.readUTF());
        } catch (IOException e) {
            return;
        }
    }
}