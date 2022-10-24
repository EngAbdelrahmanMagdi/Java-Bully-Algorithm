package com.bully.UI;
import com.bully.Main;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.SwingConstants;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.*;
import java.awt.event.ActionListener;
import com.bully.Design;

public class MainFrame extends JFrame {
    JFormattedTextField pCount = new JFormattedTextField();
    JPanel logsPanel = new JPanel() ;
    JPanel contentPanel = new JPanel() ;
    JButton submit = new JButton("Start") ;
    JLabel processesCount = new JLabel() ;
    JPanel input = new JPanel() ;
    JButton coordinator = new JButton("Terminate Coordinator") ;
    JLabel newLine = new JLabel("<html><body><br/></body></html>", SwingConstants.CENTER);

    public  MainFrame()
    {
        setTitle("Bully Algorithms");
        setSize(Design.width, Design.height);
        logsPanel.setLayout(new BoxLayout(logsPanel,BoxLayout.Y_AXIS));
        input.setLayout(new FlowLayout());
        pCount.setColumns(5);
        pCount.setPreferredSize(new Dimension(50 , 30));
        submit.setPreferredSize(new Dimension(80 , 30));
        processesCount.setText("Enter Number of Processes");
        processesCount.setFont(new Font("Tahoma", Font.PLAIN, 16 ));
        input.setSize(new Dimension(105,105));


        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                 submit.setEnabled(false);
                try {
                    Main.initializeElection(Integer.parseInt(pCount.getText()));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } );
        coordinator.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Main.cast();
            }
        } );
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.out.println("Exit");
                Main.killAllProcess();
                System.exit(0);
            }
        });

        input.add(processesCount);
        input.add(pCount) ;
        input.add(submit) ;
        input.add(coordinator);
        input.add(newLine);
        input.add(helpMessages("Election messages between Processes will be in black")) ;
        input.add(newLine);
        input.add(helpMessages("Process winning election(New Leader) will be in blue")) ;
        input.add(newLine);
        input.add(helpMessages("Coordinator messages' will be in green to other processes")) ;
        input.add(newLine);
        input.add(helpMessages("Killed Process or disconnection in a process will be in Red")) ;
        input.add(newLine);
        add(input) ;
        contentPanel.add(logsPanel,BorderLayout.EAST);
        getContentPane().add(new JScrollPane(contentPanel),BorderLayout.EAST);
        setVisible(true);
        addLogsOutput("Waiting For Election Messages");
    }

    public void addLogsOutput (String  log){
        JLabel label = new JLabel(log);
        if (log.contains("down") || log.contains("Kill") || log.contains("disconnected")){
            label.setForeground(new Color(177,0,6));
        }else if (log.contains("Coordinator")){
            label.setForeground(new Color(0,108,67));
        }else if (log.contains("Leader")){
            label.setForeground(Color.blue);
        }
        label.setFont(new Font("Tahoma", Font.PLAIN, 16 ));
        logsPanel.add(label);
        logsPanel.getRootPane().revalidate();

    }
    public JLabel helpMessages (String message ){
        JLabel label = new JLabel(message,SwingConstants.CENTER);
        if (message.contains("down") || message.contains("Kill") || message.contains("disconnected")){
            label.setForeground(new Color(177,0,6));
        }else if (message.contains("Coordinator")){
            label.setForeground(new Color(0,108,67));
        }else if (message.contains("Leader")){
            label.setForeground(Color.blue);
        }
        label.setFont(new Font("Tahoma", Font.PLAIN, 16 ));
        return label;

    }

}
