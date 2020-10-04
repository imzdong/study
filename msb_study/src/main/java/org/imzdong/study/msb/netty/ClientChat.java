package org.imzdong.study.msb.netty;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientChat extends Frame {

    private final static ClientChat CLIENT_CHAT = new ClientChat();

    public static ClientChat getInstance(){
        return CLIENT_CHAT;
    }

    TextArea textArea = new TextArea();
    TextField textField = new TextField();
    private NettyClient nettyClient;
    private String separator = System.getProperty("line.separator");

    private ClientChat(){
        setTitle("QQ");
        setSize(600,380);
        setLocation(380,240);
        add(textArea, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
        //窗口关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                nettyClient.close();
                System.exit(0);
            }
        });
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                System.out.println(input);
                nettyClient.send(input);
                input += separator;
                textArea.setText(textArea.getText() + input);
                textField.setText("");
            }
        });
        //谨记不能写在这里，写在这里会阻塞
        //setVisible(true);
        //这是个阻塞方法
        //connectNetty();
    }

    private void connectNetty() {
        nettyClient = new NettyClient();
        nettyClient.connect();
    }

    public void updateTxt(String txt){
        txt += separator;
        textArea.setText(textArea.getText() + txt);
        repaint();
    }

    public static void main(String[] args) {
        ClientChat instance = getInstance();
        instance.setVisible(true);
        //阻塞方法
        instance.connectNetty();
    }
}
