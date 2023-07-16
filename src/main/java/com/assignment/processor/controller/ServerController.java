package com.assignment.processor.controller;

import com.assignment.processor.service.RequestServiceHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@ComponentScan(basePackages = "com.assignment.processor")
public class ServerController {

    private final static Logger logger = LoggerFactory.getLogger(ServerController.class);

    protected ServerSocket serverSocket;

    @Autowired
    protected RequestServiceHandler requestServiceHandler;

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

    public class ServerHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ServerHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                ObjectMapper mapper = new ObjectMapper();
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputCommand;
                while ((inputCommand = in.readLine()) != null) {

                    if (".".equals(inputCommand)) {
                        out.println("bye");
                        break;
                    }
                    requestServiceHandler.parseCommand(inputCommand).ifPresentOrElse(
                            response -> {
                                try {
                                    String json = mapper.writeValueAsString(response);
                                    out.println(json);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            },
                            () -> {out.println("done"); }
                    );
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

        ApplicationContext context
                = new AnnotationConfigApplicationContext(ServerController.class);
        ServerController server = context.getBean(ServerController.class);

//       ServerController server=new ServerController();
        try {
            server.start(5555);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}