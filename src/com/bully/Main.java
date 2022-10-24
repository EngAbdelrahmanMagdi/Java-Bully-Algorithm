package com.bully;
import java.net.InetAddress;
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Date;
import java.util.Arrays;
import java.util.ArrayList;
import com.bully.UI.MainFrame;
import com.bully.Utilities.Messenger;


public class Main {
    public static int count ;
    public static ArrayList<Process> processes;
    private static MainFrame frame;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        frame = new MainFrame();
        processes = new ArrayList<>();
    }


    public static void start(int id, int processesNumber) {
        (new Thread(new Runnable() {
            public void run() {
                try {
                    ProcessClass.exec(Carrier.class, Arrays.asList(new String[]{}), id, processesNumber);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }

    public static void initializeElection(int processes) throws IOException {
        count = processes ;
        for (int j = 0; j < processes; j++) {
            start(j, processes);
        }
        serverSocket = new ServerSocket(12345);

        (new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        createServer();
                    } catch (Exception ignored) {

                    }
                }
            }
        })).start();

    }

    public static void createServer() throws IOException {
        try {
            Socket socket = serverSocket.accept();
            new Server(socket, frame).start();
        } catch (SocketTimeoutException ignored) {
        }
    }

    public static void killAllProcess() {

        for (Process process : processes) {
            System.out.println(process.isAlive());
            process.destroy();
        }
    }

    public static void cast() {
        try {
            for (int j = 0; j < count; j++) {
                Socket socket = new Socket(InetAddress.getLocalHost(), Design.port + j);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Messenger.message("Current coordinator process has been Killed" + String.valueOf(Design.format.format(new Date().getTime())));
                dos.writeUTF("Kill Current Leader If Existed" + String.valueOf(Design.format.format(new Date().getTime())));
                socket.close();
            }
        } catch (Exception ex1) {
        }
    }
}
