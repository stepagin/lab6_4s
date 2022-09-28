package ru.stepagin.testung;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws IOException {
        // UDP
        DatagramSocket server = new DatagramSocket(8080);
        InetSocketAddress address = new InetSocketAddress("localhost", 8080);
        DatagramPacket dp;
        int i = 0;
        while (true) {
            dp = new DatagramPacket(new byte[5], 5);
            server.receive(dp);
            System.out.println(new String(dp.getData()) + " " + i);
            i++;
        }
    }
}
