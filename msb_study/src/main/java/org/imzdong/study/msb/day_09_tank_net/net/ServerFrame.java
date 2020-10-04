package org.imzdong.study.msb.day_09_tank_net.net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {

    private TextArea textArea = new TextArea();
    //TextField textField = new TextField();
    private String separator = System.getProperty("line.separator");
    private final static ServerFrame chatServer = new ServerFrame();

    public static ServerFrame getInstance(){
        return chatServer;
    }

    private ServerFrame(){
        setTitle("服务端");
        setSize(600,380);
        setLocation(380,240);
        add(textArea, BorderLayout.CENTER);
        //add(textField, BorderLayout.SOUTH);
        //窗口关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        /*textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(textArea.getText() + textField.getText());
                textField.setText("");
            }
        });*/
        setVisible(true);
    }

    public void setTextArea(String txt){
        txt += separator;
        textArea.setText(textArea.getText() + txt);
        repaint();
    }

    public static void main(String[] args) {
        ServerFrame.getInstance();
        Server instance = Server.getInstance();
        instance.startSync(9090);
    }

}
