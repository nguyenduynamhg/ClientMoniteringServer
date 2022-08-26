package network;


import constants.CoreConstants;

import java.io.*;
import java.net.Socket;


public class SlaveRegister {

    /**
     * Contact Master and Register Client
     *
     * @throws IOException
     */
    public static void handshake() throws IOException {

        CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));

        Writer writer = new BufferedWriter(new OutputStreamWriter(
               CoreConstants.clientSocket.getOutputStream()));
        String mes = CoreConstants.ACTION_CLIENT_REGISTER + CoreConstants.SPACE + CoreConstants.serverPort;
        writer.write(mes + "\n");
        writer.flush();
        CoreConstants.clientSocket.getOutputStream().flush();
        CoreConstants.clientSocket.close();
    }

    public static void passWathForder() throws IOException {
        CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                CoreConstants.clientSocket.getOutputStream()));
        String mes = String.format(CoreConstants.ACTION_SEND_WATCH_TEMPLATE , CoreConstants.DEFAULT_WATCH_FOLDER, CoreConstants.serverPort);
        writer.write(mes + "\n");
        writer.flush();
        CoreConstants.clientSocket.getOutputStream().flush();
        CoreConstants.clientSocket.close();
    }

    public static void passDirToServer() throws IOException  {
        CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                CoreConstants.clientSocket.getOutputStream()));
        String mes = String.format(CoreConstants.ACTION_LIST_DIR , CoreConstants.DEFAULT_WATCH_FOLDER, CoreConstants.serverPort);
        writer.write(mes + "\n");
        writer.flush();
        CoreConstants.clientSocket.getOutputStream().flush();
        CoreConstants.clientSocket.close();
    }

    public static void disconnect() throws IOException {
        CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                CoreConstants.clientSocket.getOutputStream()));
        String mes = CoreConstants.ACTION_CLIENT_DISCONNECT + CoreConstants.SPACE + CoreConstants.serverPort;
        writer.write(mes + "\n");
        writer.flush();
        CoreConstants.clientSocket.getOutputStream().flush();
        CoreConstants.clientSocket.shutdownOutput();
        CoreConstants.clientSocket.close();
    }

}