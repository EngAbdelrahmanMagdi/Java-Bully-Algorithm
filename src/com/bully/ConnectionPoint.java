package com.bully;
import com.bully.UI.MainFrame;
import java.util.Date;
import java.net.*;
import com.bully.Utilities.Messenger;
import java.io.*;


public class ConnectionPoint {
    private ServerSocket serverSocket;
    private int processID;
    private int processesNumber;
    private boolean ready, failed, leader;
    private Date finalCoordinatorMessage;


    public ConnectionPoint(int processID, int processesNumber) throws IOException {
        this.processID = processID;
        this.serverSocket = new ServerSocket(Design.port + processID);
        this.failed = false;
        this.ready = true;
        this.leader = false;
        this.processesNumber = processesNumber;
        finalCoordinatorMessage = new Date() ;
    }

    public void setState (boolean state){
        this.failed = state ;
    }
    public boolean isLeader (){
        return this.leader ;
    }
    public int getProcessID() {
        return this.processID;
    }
    public void setLeaderState(boolean leader){
        this.leader = leader ;
    }
    public void setReadyProcess(boolean ready) {
        this.ready = ready;
    }
    public void setFinalCoordinatorMessage(Date lastDate){
        this.finalCoordinatorMessage = lastDate ;
    }

    public void startProcess() throws IOException {
        // Client who sends the data
        (new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (leader) {
                        try {
                            Timer.getInstance().Wait();
                            prepareCoordinator();
                        } catch (IOException | InterruptedException exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        try {
                            Timer.getInstance().Wait();
                            if (Math.abs( new Date().getTime() - finalCoordinatorMessage.getTime()) > Design.LAUNCH && (!failed || leader)){
                                ready = true;
                            }
                            startElection();
                        } catch (Exception exception) {
                        }
                    }
                }
            }
        })).start();

        // Server listening to the client
        (new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        startNewRequest();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        })).start();
    }

    public void startElection() throws InterruptedException, IOException {
        if (failed) return;
        for (int j = this.processID + 1; j <= processesNumber; j++) {
            try {
                if (!ready) return;
                Socket socket = new Socket(InetAddress.getLocalHost(), Design.port + j);
                DataOutputStream dataStream = new DataOutputStream(socket.getOutputStream());
                Messenger.message("process(" + processID + ") -> process(" + j + ") : Election Message " + String.valueOf(Design.format.format(new Date().getTime())));
                System.out.println("process(" + processID + ") -> process(" + j + ") : Election Message " + String.valueOf(Design.format.format(new Date().getTime())));
                dataStream.writeUTF(String.valueOf(Design.format.format(new Date().getTime())));
                socket.close();
                ready = false;
            } catch (IOException ex) {
                Messenger.message("process(" + processID + ") -> process(" + j + ") : No Reply Process(" + j + ") new Leader process("+processID+")" + String.valueOf(Design.format.format(new Date().getTime())));
                System.out.println("process(" + processID + ") -> process(" + j + ") : No Reply Process(" + j + ") new Leader process("+processID+")" + String.valueOf(Design.format.format(new Date().getTime())));
                this.leader = true;
            }
        }

    }

    public void startNewRequest() throws Exception {
        if(failed) return ;
        try {
            Socket socket = serverSocket.accept();
            if (failed) {
                Messenger.message("Process(" + processID + ") disconnected" + String.valueOf(Design.format.format(new Date().getTime())));
                System.out.println("Process(" + processID + ") disconnected" + String.valueOf(Design.format.format(new Date().getTime())));
                socket.close();
                serverSocket.close();
            }
            new Request(socket, this).start();

        } catch (SocketTimeoutException ignored) {
        }

    }

    public void prepareCoordinator() throws IOException {
        try {
            for (int j = 0; j < processesNumber; j++) {
                if (j == processID) continue;
                try {
                    Socket socket = new Socket(InetAddress.getLocalHost(), Design.port + j);
                    DataOutputStream dataStream = new DataOutputStream(socket.getOutputStream());
                    Messenger.message("Process(" + processID + ") ->  Process:(" + j + ") : Coordinator" + String.valueOf(Design.format.format(new Date().getTime())));
                    System.out.println("Process(" + processID + ") ->  Process:(" + j + ") : Coordinator" + String.valueOf(Design.format.format(new Date().getTime())));
                    dataStream.writeUTF("*"+String.valueOf(Design.format.format(new Date().getTime())) );
                    socket.close();
                } catch (IOException ex) {
                    Messenger.message("process(" + processID + ") -> process(" + j + "):No Reply" + String.valueOf(Design.format.format(new Date().getTime())));
                    System.out.println("process(" + processID + ") -> process(" + j + "):No Reply" + String.valueOf(Design.format.format(new Date().getTime())));
                }
            }
        } catch (Exception ex1) {
        }

    }
}
