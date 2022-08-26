package network;

import constants.CoreConstants;
import thread.MasterFileWatchThread;

import java.net.InetAddress;
import java.net.Socket;

public class FileServerForSlave implements Runnable {

    static boolean isStarted = false;

    int port;
    String dir;

    private FileServerForSlave(int port, String dir) {
        this.port = port;
        this.dir = dir;
    }

    /**
     * Start a new thread
     */
    public static void startThread(int port, String dir) {
        if (!isStarted) {
            new Thread(new FileServerForSlave(port, dir)).start();

        }
    }

    @Override
    public void run() {

        try {
            System.out.println(CoreConstants.LISTEN_FILES);
            System.out.println(String.format(CoreConstants.CONNECTION_MASTER,
                    CoreConstants.clientSocket.getInetAddress().getHostAddress()));
            MasterFileWatchThread.startThread(CoreConstants.DEFAULT_WATCH_FOLDER);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(CoreConstants.UNABLE_LISTEN_FILES);
        }
    }

}