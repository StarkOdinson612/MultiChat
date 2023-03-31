package com.starkodinson;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1700,900);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        JPanel rPanel = new JPanel();

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane messagePane = new JScrollPane(messageArea);

        messagePane.setPreferredSize(new Dimension((int) (frame.getWidth() * .71875), (int) (frame.getHeight() * .85)));

        BoxLayout rLayout = new BoxLayout(rPanel, BoxLayout.Y_AXIS);

        JPanel lPanel = new JPanel();

        lPanel.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.28125),(int) (frame.getHeight() * .95)));
        rPanel.setPreferredSize(new Dimension(frame.getWidth() * 11 / 16, (int) (frame.getHeight() * .95)));

        JPanel inputPanel = new JPanel();
        GroupLayout inputLayout = new GroupLayout(inputPanel);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(frame.getWidth() * 10 / 16,frame.getHeight() / 16));
        JButton sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(frame.getWidth() * 3 / 32, frame.getHeight() / 16));


        inputLayout.setHorizontalGroup(
                inputLayout.createSequentialGroup()
                .addComponent(textField)
                .addComponent(sendButton)
        );


        rPanel.add(messagePane);
        rPanel.add(inputPanel);

        frame.add(lPanel);
        frame.add(rPanel);


        frame.setVisible(true);
    }

    class ListenClient implements Runnable
    {
        private Socket sSocket;
        private BufferedReader br;

        public ListenClient(Socket s) throws IOException {
            sSocket = s;
            br = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
        }

        @Override
        public void run() {
            while (!sSocket.isClosed())
            {

            }
        }
    }
}
