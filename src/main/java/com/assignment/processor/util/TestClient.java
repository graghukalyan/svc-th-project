package com.assignment.processor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {

    private final static Logger logger = LoggerFactory.getLogger(TestClient.class);

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
        String resp = in.readLine();
        return resp;
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
        TestClient client1 = new TestClient();
        client1.startConnection("127.0.0.1", 5555);
        try {
            String msg1 = client1.sendMessage("testing");
            String msg2 = client1.sendMessage("client 1");
            String terminate = client1.sendMessage(".");

            System.out.println( "msg1 :" +msg1 + " terminate :: "+terminate);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}