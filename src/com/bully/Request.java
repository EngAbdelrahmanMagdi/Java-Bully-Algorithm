package com.bully;
import com.bully.Utilities.Messenger;
import java.net.Socket;
import java.io.DataInputStream;
import java.util.Date;
import java.text.ParseException;
import java.io.IOException;

public class Request extends Thread {

    private ConnectionPoint connectionPoint;
    private Socket socket;

    public Request(Socket clientSocket, ConnectionPoint connectionPoint) {
        this.connectionPoint = connectionPoint;
        this.socket = clientSocket;
    }

    public void run() {
        try {
            DataInputStream dos = new DataInputStream(socket.getInputStream());
            String strDate = dos.readUTF();

            System.out.println(strDate.contains("Kill") +" "+ connectionPoint.isLeader() + connectionPoint.getProcessID());
            if (strDate.charAt(0) == '*') {
                strDate = strDate.substring(1);
                connectionPoint.setReadyProcess(false);
                Date date = Design.format.parse(strDate);
                connectionPoint.setFinalCoordinatorMessage(date);
            }else if (strDate.contains("Kill") && connectionPoint.isLeader()){
                connectionPoint.setState(true);
                connectionPoint.setLeaderState(false);
                Messenger.message("Leader Process(" + connectionPoint.getProcessID() + ") is down " + String.valueOf(Design.format.format(new Date().getTime()) ));
                System.out.println("Leader Process(" + connectionPoint.getProcessID() + ") is down " + String.valueOf(Design.format.format(new Date().getTime()) ));
            }

        } catch (IOException | ParseException e) {
           return ;
        }
    }
}
