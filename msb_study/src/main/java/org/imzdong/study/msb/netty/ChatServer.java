package org.imzdong.study.msb.netty;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatServer extends Frame {

    private TextArea textArea = new TextArea();
    //TextField textField = new TextField();
    private String separator = System.getProperty("line.separator");
    private final static ChatServer chatServer = new ChatServer();

    public static ChatServer getInstance(){
        return chatServer;
    }

    private ChatServer(){
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

}
