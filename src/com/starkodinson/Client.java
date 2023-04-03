package com.starkodinson;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static javax.swing.JButton sendButton;
    private static javax.swing.JPanel lPanel;
    private static javax.swing.JPanel rPanel;
    private static javax.swing.JPanel ruPanel;
    private static javax.swing.JPanel rlPanel;
    private static javax.swing.JScrollPane textScroll;
    private static javax.swing.JScrollPane memberScroll;
    private static javax.swing.JTextField inputField;
    private static JTextArea textArea;

    public static void main(String[] args) throws IOException {
        FlatDarkLaf.setup();
        JFrame overallFrame = new JFrame();
        overallFrame.setPreferredSize(new Dimension(1200,700));
        overallFrame.setMinimumSize(new Dimension(700,600));

        overallFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        overallFrame.setBackground(new java.awt.Color(146, 144, 164));

        Socket socket = new Socket("localhost", 49999);
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

        lPanel = new JPanel();
        memberScroll = new JScrollPane();
        rPanel = new JPanel();
        ruPanel = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textScroll = new JScrollPane(textArea);
        rlPanel = new JPanel();
        sendButton = new JButton();
        inputField = new JTextField();




        lPanel.setMinimumSize(new java.awt.Dimension(50, 400));
        lPanel.setPreferredSize(new java.awt.Dimension(250, 900));
        lPanel.setMaximumSize(new Dimension(250,1000));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(lPanel);
        lPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(memberScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(memberScroll)
        );

        rPanel.setMinimumSize(new java.awt.Dimension(400, 400));
        rPanel.setPreferredSize(new java.awt.Dimension(1350, 900));

        ruPanel.setMinimumSize(new java.awt.Dimension(400, 50));
        ruPanel.setPreferredSize(new java.awt.Dimension(1350, 800));

        textScroll.setMinimumSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(ruPanel);
        ruPanel.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );

        rlPanel.setMinimumSize(new java.awt.Dimension(400, 50));
        rlPanel.setPreferredSize(new java.awt.Dimension(1350, 100));
        rlPanel.setMaximumSize(new Dimension(400,100));

        sendButton.setText("Send");
        sendButton.addActionListener(evt -> sendMessage(pw));

        inputField.setText("jTextField1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(rlPanel);
        rlPanel.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(inputField, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                        .addComponent(inputField)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(rPanel);
        rPanel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ruPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                        .addComponent(rlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ruPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(overallFrame.getContentPane());
        overallFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                        .addComponent(rPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
        );

        overallFrame.setVisible(true);

        textArea.append("Enter Username Now");

        Thread listenThread = new Thread(new ListenClient(socket));
        listenThread.start();
    }

    static void sendMessage(PrintWriter pw)
    {
        String currentMsg = inputField.getText();
        inputField.setText("");
        pw.println(currentMsg);
    }

    static class ListenClient implements Runnable
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
                try {
                    String message = br.readLine();
                    textArea.append(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
