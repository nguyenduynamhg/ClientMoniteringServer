package network;

import tasks.SocketHandler;

public class CommandServerMaster implements Runnable {

    static boolean isStarted = false;
    int port = 0;

    private CommandServerMaster(int port) {
        this.port = port;
    }

    /**
     * Start a new thread
     */
    public static void startThread(int port) {
        if (!isStarted) {
            new Thread(new CommandServerMaster(port)).start();
        }
    }

    @Override
    public void run() {
        SocketHandler.startThread(this.port);
    }
}