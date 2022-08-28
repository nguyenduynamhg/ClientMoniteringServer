package tasks;

import constants.CoreConstants;
import utilitis.BindingSouceHelper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler implements Runnable {

    private static ServerSocket serverSocket = null;
    private static Tracker trk = new Tracker();
    private static boolean isStarted = false;
    int port = 0;

    public SocketHandler(int port) {
        this.port = port;
    }

    public static void startThread(int port) {
        if (!isStarted) {
            new Thread(new SocketHandler(port)).start();
        }
    }

    @Override
    public void run() {

        try {
            System.out.println(String.format(CoreConstants.SERVER_STARTING, port));
            ServerSocket serverSocket = new ServerSocket(port);
            isStarted = true;
            while (true) {

                Socket clientSocket = serverSocket.accept();
                String clientIP = clientSocket.getInetAddress()
                        .getHostAddress();
                String clientPort = String.valueOf(clientSocket.getPort());
                System.out.println(String.format(CoreConstants.COMMAND_FROM, clientIP));
                clientSocket.setKeepAlive(true);
//                BufferedReader reader = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));

//                DataInputStream reader = new DataInputStream(
//                        new BufferedInputStream(clientSocket.getInputStream()));
                String command = "";
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                while (command != null) {
                    try{
                        command = in.readLine();
                        if (command == null) {
                            break;
                        }
                        else if (command.startsWith(CoreConstants.ACTION_CLIENT_REGISTER)) {
                            registorClient(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_SEND_WATCH_DIR)) {
                            clientSendWatchDirectory(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_CLIENT_DISCONNECT)) {
                            clientDisconnect(clientIP);
                            clientSocket.close();
                            break;
                        }
                        else if (command.startsWith(CoreConstants.ACTION_CREATE_FOLDER_COMMAND)) {
                            clientCreateFolder(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_CREATE_FILE_COMMAND)) {
                            clientCreateFile(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_MODIFY_FILE_COMMAND)) {
                            clientModifyFile(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_MODIFY_FOLDER_COMMAND)) {
                            clientModifyFolder(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_DELETE_FILE_COMMAND)) {
                            clientDeleteFile(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_DELETE_FOLDER_COMMAND)) {
                            clientDeleteFolder(command, clientIP, clientPort);
                        }
                        else if (command.startsWith(CoreConstants.ACTION_LIST_DIR)) {
//                            clientListDir(in);
                        }
                        else {
                            System.out.println(String.format(CoreConstants.UNKNOWN_COMMAND_FROM, command));
                        }
                    }
                    catch (IOException e) {
//                        clientDisconnect(clientIP);
                        break;
                    }
                };
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(CoreConstants.THREAD_UNABLE_START);
        }

    }

    private static void registorClient(String command, String clientIP, String clientPort) {
        System.out.println(String.format(CoreConstants.NEW_CLIENT, clientIP, clientPort));
        trk.writeLogFile(CoreConstants.ACTION_CLIENT_JOIN, clientIP,
                String.format(CoreConstants.ACTION_CLIENT_JOIN_DESC, clientIP));
        CoreConstants.clientIps.addElement(clientIP);
        CoreConstants.addClient(clientIP, clientPort);
    }

    private static void clientSendWatchDirectory(String command, String clientIP, String clientPort) {
        String dir = command.split(CoreConstants.SEPARATOR)[1];
        System.out.println(String.format(CoreConstants.WATCH_DIR, dir, clientIP, clientPort));
        trk.writeLogFile(CoreConstants.ACTION_CLIENT_SEND_WATCH_DIR, clientIP,
                String.format(CoreConstants.ACTION_CLIENT_SEND_WATCH_DIR_DESC, clientIP));
        CoreConstants.foldersInWatch.add(dir);
    }

    private static void clientDisconnect(String clientIP) {
        System.out.println(CoreConstants.CLIENT_DISCONNECT);

        trk.writeLogFile(CoreConstants.ACTION_CLIENT_OUT, clientIP,
                String.format(CoreConstants.ACTION_CLIENT_OUT_DESC, clientIP));

        int index = CoreConstants.clientIps.indexOf(clientIP);
        if (index != -1) {
            CoreConstants.clientIps.remove(index);
        }
    }

    private static void clientCreateFile(String command, String clientIP, String clientPort) {
        String file = command.split(CoreConstants.SPACE)[1];
        System.out.println(String.format(CoreConstants.ACTION_CLIENT_CREATE_FILE, file, clientIP));
        trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_CREATE_FILE, file), clientIP,
                String.format(CoreConstants.ACTION_CLIENT_CREATE_FILE, file, clientIP));
        BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
    }
    private static void clientCreateFolder(String command, String clientIP, String clientPort) {
        String dir = command.split(CoreConstants.SPACE)[1];
        System.out.println(String.format(CoreConstants.ACTION_CLIENT_CREATE_FOLDER, dir, clientIP));
        trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_CREATE_FOLDER, dir), clientIP,
                String.format(CoreConstants.ACTION_CLIENT_CREATE_FOLDER, dir, clientIP));
        BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
    }
    private static void clientModifyFile(String command, String clientIP, String clientPort) {
        String file = command.split(CoreConstants.SPACE)[1];
        System.out.println(String.format(CoreConstants.ACTION_CLIENT_MODIFY_FILE, file, clientIP));
        trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_MODIFY_FILE, file), clientIP,
                String.format(CoreConstants.ACTION_CLIENT_MODIFY_FILE, file, clientIP));
        BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
    }
    private static void clientModifyFolder(String command, String clientIP, String clientPort) {
        String dir = command.split(CoreConstants.SPACE)[1];
        System.out.println(String.format(CoreConstants.ACTION_CLIENT_MODIFY_FOLDER, dir, clientIP));
        trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_MODIFY_FOLDER, dir), clientIP,
                String.format(CoreConstants.ACTION_CLIENT_MODIFY_FOLDER, dir, clientIP));
        BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
    }
    private static void clientDeleteFile(String command, String clientIP, String clientPort) {
        String file = command.split(CoreConstants.SPACE)[1];
        System.out.println(String.format(CoreConstants.ACTION_CLIENT_DELETE_FILE, file, clientIP));
        trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_DELETE_FILE, file), clientIP,
                String.format(CoreConstants.ACTION_CLIENT_DELETE_FILE, file, clientIP));
        BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
    }
    private static void clientDeleteFolder(String command, String clientIP, String clientPort) {
        String dir = command.split(CoreConstants.SPACE)[1];
        System.out.println(String.format(CoreConstants.ACTION_CLIENT_DELETE_FOLDER, dir, clientIP));
        trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_DELETE_FOLDER, dir), clientIP,
                String.format(CoreConstants.ACTION_CLIENT_DELETE_FOLDER, dir, clientIP));
        BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
    }
}
