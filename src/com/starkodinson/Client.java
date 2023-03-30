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
        frame.setSize(1600,900);
        frame.setLayout(new BorderLayout());

        JPanel rPanel = new JPanel();
        JPanel lPanel = new JPanel();

        lPanel.setPreferredSize(new Dimension(450,900));
        rPanel.setPreferredSize(new Dimension(1150,900));

        JPanel inputPanel = new JPanel();
        LayoutManager inputLayout = new GroupLayout(inputPanel);

        JTextField t = new JTextField();
        t.setPreferredSize(new Dimension(1000,100));
        JButton b = new JButton("Send");
        b.setPreferredSize(new Dimension(150, 100));





        inputPanel.add(t);
        inputPanel.add(b);


        inputPanel.setLayout(inputLayout);

        frame.add(inputPanel);


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
