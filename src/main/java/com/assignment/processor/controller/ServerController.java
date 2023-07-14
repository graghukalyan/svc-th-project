package com.assignment.processor.controller;

import com.assignment.processor.service.RequestServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    private final static Logger logger = LoggerFactory.getLogger(ServerController.class);

    @Value("${spring.application.server.port}")
    private Integer serverPort;

    private ServerSocket serverSocket;

    @Autowired
    static RequestServiceHandler requestServiceHandler;

    public void start(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);
        while (true)
            new ServerHandler(serverSocket.accept()).start();
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static class ServerHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ServerHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputCommand;
                while ((inputCommand = in.readLine()) != null) {

                    requestServiceHandler.parseCommand(inputCommand);


//                    if (".".equals(inputLine)) {
//                        out.println("bye");
//                        break;
//                    }
                    out.println(inputCommand);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static void main(String[] args) {
        ServerController server=new ServerController();
        try {
            server.start(5555);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}