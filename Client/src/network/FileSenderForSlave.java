package network;

import constants.CoreConstants;
import tasks.Tracker;

import java.io.*;
import java.net.Socket;

public class FileSenderForSlave {

    private static Tracker trk = new Tracker();
    /**
     * Send new to all clients
     *
//     * @param fileAbsolutePath
     * @return
     */
    public static boolean sendFile(String path, String file, String action) {

        try {
            System.out.println(String.format(CoreConstants.ACTION_TRANSMITTING_FILE,
                    file, CoreConstants.ipHost, CoreConstants.serverPort));
            CoreConstants.clientSocket = null;
            CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
            File f = new File(path + CoreConstants.BACK_SLASH + file);
            if (!f.isDirectory()) {
                FileInputStream fileInputSteam = new FileInputStream(f);
                byte[] mybytearray = new byte[(int) f.length()];
                BufferedInputStream bis = new BufferedInputStream(
                        fileInputSteam);
                bis.read(mybytearray, 0, mybytearray.length);

                OutputStream os = CoreConstants.clientSocket.getOutputStream();
                os.write(mybytearray, 0, mybytearray.length);
                os.flush();
                os.close();
                bis.close();
            }
            CoreConstants.clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * Send new to all clients
     *
//     * @param fileAbsolutePath
     * @return
     */
    public static boolean sendDir(String path, String dir, String action) {

        try {
            System.out.println(String.format(CoreConstants.ACTION_TRANSMITTING_FOLDER,
                    dir, CoreConstants.ipHost, CoreConstants.serverPort));
            CoreConstants.clientSocket = null;
            CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
            File f = new File(path + dir);
            if (f.exists()) {
                String command = dir;
                OutputStream os = CoreConstants.clientSocket.getOutputStream();
                os.write(command.getBytes(), 0, command.length());
                os.flush();
                os.close();

            } else {
                return false;
            }
            CoreConstants.clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * Send new to all clients
     *
     * @return
     */
    public static boolean sendDirRecursive(String root, String path,
                                           String action) {

        try {
            System.out.println(String.format(CoreConstants.ACTION_RECURSE_SEND, path));
            File fPath = new File(root + CoreConstants.FORWARD_SLASH + path);
            if (fPath.isDirectory()) {
                sendDir(root, path, CoreConstants.ACTION_CREATE_DIR);
                // Now check all childs
                File[] childs = fPath.listFiles();
                if (childs == null)
                    return true;
                for (File child : childs) {
                    if (child.isDirectory()) {
                        sendDirRecursive(root, path + CoreConstants.FORWARD_SLASH + child.getName(),
                                null);
                    } else {
                        sendFile(root, path + CoreConstants.FORWARD_SLASH + child.getName(),
                                CoreConstants.ACTION_CREATE_FILE);
                    }

                }
                // then call again
            } else {
                sendFile(fPath.getPath(), fPath.getName(), CoreConstants.ACTION_CREATE_FILE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public static boolean delete(String path, String file) {

        try {
            CoreConstants.clientSocket = null;
            CoreConstants.clientSocket = new Socket(CoreConstants.ipHost, Integer.parseInt(CoreConstants.serverPort));
            OutputStream os = CoreConstants.clientSocket.getOutputStream();
            String command = file;
            os.write(command.getBytes(), 0, command.length());
            os.flush();
            os.close();
            CoreConstants.clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
