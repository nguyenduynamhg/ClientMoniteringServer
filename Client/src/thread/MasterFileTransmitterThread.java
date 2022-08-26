package thread;

import constants.CoreConstants;
import constants.FileEvent;
import network.FileSenderForSlave;
import tasks.Tracker;

import java.io.*;
import java.net.Socket;

/**
 * This is a thread to send a file to slave
 *
 * @author MIBRLK0
 *
 */
public class MasterFileTransmitterThread implements Runnable {

    String action = null;
    String file;
    String rootPath;

    Tracker trk = new Tracker();


    public static void start(String action, String file, String rootPath) {
        new Thread(new MasterFileTransmitterThread(action, file, rootPath)).start();
    }

    public MasterFileTransmitterThread(String action, String file,
                                       String rootPath) {
        this.action = action;
        this.file = file;
        this.rootPath = rootPath;
    }

    @Override
    public void run() {
        try {
            System.out.println(String.format(CoreConstants.START_PROCESS, action, file));
            this.processFile(action, file, rootPath);
            // reindex it
            System.out.println(CoreConstants.REINDEX_FILES);
            CoreConstants.setFileIndex(rootPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processFile(String action, String file, String rootPath) {
        if (FileEvent.ENTRY_DELETE.name().equals(action)) {
            this.delete(file);
        } else if (FileEvent.ENTRY_CREATE.name().equals(action)) {
            this.create(file);
        } else if (FileEvent.ENTRY_MODIFY.name().equals(action)) {
            this.update(file);
        }
    }

    private void delete(String file) {
        try {
            CoreConstants.clientSocket = null;
            String path =  rootPath + CoreConstants.BACK_SLASH + file;
            CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    CoreConstants.clientSocket.getOutputStream()));
            String mes = "";
            if (!file.contains(CoreConstants.DOT)) {
                mes = String.format(CoreConstants.ACTION_DELETE_FOLDER_COMMAND, path);
                writer.write(mes + CoreConstants.NEW_LINE);
                writer.flush();
                CoreConstants.clientSocket.getOutputStream().flush();
                CoreConstants.clientSocket.close();

                trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_DELETE_FOLDER, path), CoreConstants.ipLocal,
                        String.format(CoreConstants.ACTION_CLIENT_DELETE_FOLDER, path));
                System.out.println(String.format(CoreConstants.ACTION_CLIENT_DELETE_FOLDER, path));
                CoreConstants.actionState = String.format(CoreConstants.ACTION_CLIENT_DELETE_FOLDER, path);
            }
            else {
                mes = String.format(CoreConstants.ACTION_DELETE_FILE_COMMAND, file);
                writer.write(mes + CoreConstants.NEW_LINE);
                writer.flush();
                CoreConstants.clientSocket.getOutputStream().flush();
                CoreConstants.clientSocket.close();

                trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_DELETE_FILE, file), CoreConstants.ipLocal,
                        String.format(CoreConstants.ACTION_CLIENT_DELETE_FILE, file));
                System.out.println(String.format(CoreConstants.ACTION_CLIENT_DELETE_FILE, file));
                CoreConstants.actionState = String.format(CoreConstants.ACTION_CLIENT_DELETE_FILE, file);
            }
            FileSenderForSlave.delete(rootPath, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create file
     *
     * @param file
     */
    private void create(String file) {

        try {
            CoreConstants.clientSocket = null;
            CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    CoreConstants.clientSocket.getOutputStream()));
            String mes = "";
            File path =  new File(rootPath + CoreConstants.FORWARD_SLASH + file);
            if (path.isDirectory()) {

                mes = String.format(CoreConstants.ACTION_CREATE_FOLDER_COMMAND, file);
                writer.write(mes + CoreConstants.NEW_LINE);
                writer.flush();
                CoreConstants.clientSocket.getOutputStream().flush();
                CoreConstants.clientSocket.close();

                trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_CREATE_FOLDER, file), CoreConstants.ipLocal,
                        String.format(CoreConstants.ACTION_CLIENT_CREATE_FOLDER, file));
                CoreConstants.actionState = String.format(CoreConstants.ACTION_CLIENT_CREATE_FOLDER, file);

                FileSenderForSlave.sendDir(rootPath, file, CoreConstants.ACTION_CREATE_DIR);
            } else {

                mes = String.format(CoreConstants.ACTION_CREATE_FILE_COMMAND, path);
                writer.write(mes + CoreConstants.NEW_LINE);
                writer.flush();
                CoreConstants.clientSocket.getOutputStream().flush();
                CoreConstants.clientSocket.close();

                trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_CREATE_FILE, file), CoreConstants.ipLocal,
                        String.format(CoreConstants.ACTION_CLIENT_CREATE_FILE, file));
                CoreConstants.actionState = String.format(CoreConstants.ACTION_CLIENT_CREATE_FILE, file);

                FileSenderForSlave.sendFile(rootPath, file, CoreConstants.ACTION_TRANSMITTING_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update(String file) {
        try {
            CoreConstants.clientSocket = null;
            CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    CoreConstants.clientSocket.getOutputStream()));
            String mes = "";
            File path =  new File(rootPath + CoreConstants.FORWARD_SLASH + file);
            if (path.isDirectory()) {

                mes = String.format(CoreConstants.ACTION_MODIFY_FOLDER_COMMAND, file);
                writer.write(mes + CoreConstants.NEW_LINE);
                writer.flush();
                CoreConstants.clientSocket.getOutputStream().flush();
                CoreConstants.clientSocket.close();

                trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_MODIFY_FOLDER, file), CoreConstants.ipLocal,
                        String.format(CoreConstants.ACTION_CLIENT_MODIFY_FOLDER, file));
                CoreConstants.actionState = String.format(CoreConstants.ACTION_CLIENT_MODIFY_FOLDER, file);

                FileSenderForSlave.sendDirRecursive(rootPath, file, CoreConstants.ACTION_CREATE_DIR);

            } else {

                mes = String.format(CoreConstants.ACTION_MODIFY_FILE_COMMAND, file);
                writer.write(mes + CoreConstants.NEW_LINE);
                writer.flush();
                CoreConstants.clientSocket.getOutputStream().flush();
                CoreConstants.clientSocket.close();

                trk.writeLogFile(String.format(CoreConstants.ACTION_LOG_MODIFY_FILE, file), CoreConstants.ipLocal,
                        String.format(CoreConstants.ACTION_CLIENT_MODIFY_FILE, file));
                CoreConstants.actionState = String.format(CoreConstants.ACTION_CLIENT_MODIFY_FILE, file);

                FileSenderForSlave.sendFile(rootPath, file, CoreConstants.ACTION_MODIFY_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listFile(String file) {
        if (new File(rootPath + file).isDirectory()) {
            FileSenderForSlave.sendDir(rootPath, file, CoreConstants.ACTION_LIST_DIR);
        } else {
            FileSenderForSlave.sendFile(rootPath, file, CoreConstants.ACTION_LIST_DIR);
        }
    }

}
