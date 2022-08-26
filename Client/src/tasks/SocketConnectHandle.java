package tasks;

import constants.CoreConstants;

import java.io.*;
import java.net.Socket;

public class SocketConnectHandle implements Runnable {

    Socket client = null;

    public SocketConnectHandle(Socket client) {
        this.client = client;
    }

    public void keepConnectAlive() {
        try {
            while (true) {
                Writer writer = new BufferedWriter(new OutputStreamWriter(
                        client.getOutputStream()));
                String mes = CoreConstants.ACTION_KEEP_CONNECTION;
                writer.write(mes + "\n");
                writer.flush();
                client.getOutputStream().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        keepConnectAlive();
    }
}
