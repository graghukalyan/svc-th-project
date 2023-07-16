package com.assignment.processor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClient {

    private final static Logger logger = LoggerFactory.getLogger(ServerClient.class);

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServerClient client1 = new ServerClient();
        client1.startConnection("127.0.0.1", 5555);
        try {

            while(true) {
                Scanner input = new Scanner(System.in);
                // Prompt for input & Read as a String
                String command = input.nextLine();
                System.out.println(client1.sendMessage(command));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}