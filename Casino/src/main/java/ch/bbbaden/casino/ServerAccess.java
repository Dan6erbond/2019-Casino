/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author aless
 */
public class ServerAccess extends Observable{
        private Socket socket;
        private OutputStream outputStream;
        private static ServerAccess sa= new ServerAccess();
        private String message;
        public static ServerAccess getInstance()
        {
            return sa;
        }

        /** Create socket, and receiving thread */
        public void InitSocket(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String line;
                        line = reader.readLine().trim();
                        while (line != null){
                                message = line;
                        }
                    } catch (IOException ex) {
                        close();
                    }
                }
            };
            receivingThread.start();
        }

        private static final String CRLF = "\r\n"; 
        
        public String getmessage()
        {
            return message;
        }
        
        /** Send a line of text */
        public void send(String text) {
            try {
                outputStream.write((text + CRLF).getBytes());
                outputStream.flush();
            } catch (IOException ex) {
            }
        }

        /** Close the socket */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

