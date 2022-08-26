package constants;

import util.FileUtil;
import util.LogItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoreConstants {

    // app
    public static final double MIN_JAVA_VERSION = 1.7D;
    public static final int MIN_PORT = 1024;
    public static final String SLAVE = "slave";
    public static final int MAX_LINE_READ = 10;
    public static final String NEW_LINE = "\n";
    public static final String BACK_SLASH = "\\";
    public static final String FORWARD_SLASH = "/";
    public static final String SPACE = " ";
    public static final String DOT = ".";

    // config
    public static final String APP_TITLE = "Client Remote File";
    public static final String ipLocal = "Localhost";
    public static final String SEPARATOR = "~";
    public static final String LOG_FILE = "log.txt";
    public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    public static final String LOG_MESSAGE_PATTERN = "%d "+ SEPARATOR +" %s "+ SEPARATOR +" %s "+ SEPARATOR +" %s "+ SEPARATOR +" %s";
    public static final Object[] LOG_HEADER_COLUMNS = {"No", "Action", "IP Client", "Description", "Created At"};
    public static final String SERVER_IP_LABEL = "Server IP: ";
    public static final String SERVER_PORT_LABEL = "Server Port: ";
    public static final String SERVER_CONNECTION_STATE_LABEL = "Connection State: ";
    public static final String SERVER_FOLDER_WATCH_LABEL = "Folder In Watch: ";
    public static final String SERVER_LAST_ACTION_LABEL = "Last Action: ";

    // app static
    public static int noLog = 1;
    public static String serverPort = "";
    public static String ipHost = "";
    public static Socket clientSocket = null;
    public static String DEFAULT_WATCH_FOLDER = "/home/namnd/Desktop/java/";
    public static String actionState = "";
    public static DefaultTableModel modelLogs = new DefaultTableModel(){};
    public static ArrayList<LogItem> logs = new ArrayList<LogItem>();

    // action
    public static final String ACTION_COMMAND = "%s %s ~~";
    public static final String ACTION_SEND_DIR = "SENDDIRCLIENT ~%s~%s";
    public static final String ACTION_KEEP_CONNECTION = "CONNECTSTAY";
    public static final String ACTION_LIST_DIR = "LDIR";
    public static final String ACTION_CREATE_DIR = "MKDIR";
    public static final String ACTION_CREATE_FILE = "MKFILE";
    public static final String ACTION_MODIFY_FILE = "MDFILE";
    public static final String ACTION_CREATE_FOLDER_COMMAND = "MKDIR %s ~~";
    public static final String ACTION_CREATE_FILE_COMMAND = "MKFILE %s ~~";
    public static final String ACTION_MODIFY_FILE_COMMAND = "MDFILE %s ~~";
    public static final String ACTION_MODIFY_FOLDER_COMMAND = "MDDIR %s ~~";
    public static final String ACTION_DELETE_FILE_COMMAND = "DLFILE %s ~~";
    public static final String ACTION_DELETE_FOLDER_COMMAND = "DLDIR %s ~~";
    public static final String ACTION_SEND_WATCH_TEMPLATE = "SETWATCHDIR: ~%s~%s";
    public static final String ACTION_CLIENT_REGISTER = "REGCLIENT";
    public static final String ACTION_CLIENT_DISCONNECT = "DISCLIENT";

    public static final String ACTION_LOG_CREATE_FILE = "CREATE FILE %s";
    public static final String ACTION_LOG_CREATE_FOLDER = "CREATE FOLDER %s";
    public static final String ACTION_LOG_DELETE_FILE = "DELETE FILE %s";
    public static final String ACTION_LOG_DELETE_FOLDER = "DELETE FOLDER %s";
    public static final String ACTION_LOG_MODIFY_FILE = "MODIFY FILE %s";
    public static final String ACTION_LOG_MODIFY_FOLDER = "MODIFY FOLDER %s";

    public static final String ACTION_CLIENT_CREATE_FILE = "Client create file %s on Localhost";
    public static final String ACTION_CLIENT_MODIFY_FILE = "Client modify file %s on Localhost";
    public static final String ACTION_CLIENT_DELETE_FILE = "Client delete file %s on Localhost";
    public static final String ACTION_CLIENT_CREATE_FOLDER = "Client create folder %s on Localhost";
    public static final String ACTION_CLIENT_MODIFY_FOLDER = "Client modify folder %s on Localhost";
    public static final String ACTION_CLIENT_DELETE_FOLDER = "Client delete folder %s on Localhost";

    public static final String ACTION_TRANSMITTING_FILE = "Transmitting FILE [%s] TO %s: %s";
    public static final String ACTION_TRANSMITTING_FOLDER = "Transmitting DIR [%s] TO %s: %s";
    public static final String ACTION_RECURSE_SEND = "Recurse Send %s";

    // status text
    public static final String PARAMETERS_EXCEPTION = "Run with required parameters. Example: java RemoteFileSync master 8888 /home/namnd/Desktop/java/";
    public static final String SLAVE_PARAMETERS_EXCEPTION = "Run with required parameters. Example: java RemoteFileSync slave 8080 /home/namnd/Desktop/java/ localhost";
    public static final String FIRST_PARAMETERS_EXCEPTION = "First argument should be 'master' or 'slave' ";
    public static final String PORT_EXCEPTION = "Please use port greater than 1024 inorder to avoid protocol conflicts.";
    public static final String INVALID_FOLDER_EXCEPTION = "File system path given does not exists or not a valid directory. Please create a folder:  %s";
    public static final String PARAMETERS_SUCCESS = "Parameter check successful.";
    public static final String MASTER_THREAD_INITIAL = "Master thread initialized.";
    public static final String START_PROCESS = "Start Processing: %s %s";
    public static final String REINDEX_FILES = "Reindex FileSystem";
    public static final String JAVA_VER_PROPERTY = "java.version";
    public static final String JAVA_UPGRADE_JRE = "Please upgrade your JRE. Minimum Required version : %s";
    public static final String JAVA_CHECK_VER = "JRE Version[%s] check successful.";
    public static final String START_POLL = "Started polling %s";
    public static final String PATH_DELETED = "Path deleted";
    public static final String START_INDEX_FILE = "Start Indexing %s" ;
    public static final String PATH_INVALID = "Path does not exists or not a valid directory " ;
    public static final String INDEXING_FILE = "......Indexing %s";
    public static final String START_CLIENT_TITLE = "Server Login";
    public static final String START_REMOTE = "Starting Remote File Sync.";
    public static final String START_SLAVE_MODE = "Starting in SLAVE mode. port: %s";
    public static final String REMOTE_FILE_SUCCESS = "Remote File Sync Started successfully.";
    public static final String ERROR_WRITE_FILE = "Error in write file !!!";
    public static final String MASTER_HANDSHAKE_SUCCESS = "Master handshake completed master = %s : %s";
    public static final String MASTER_CONNECT_SUCCESS = "Connect to Server Successfully !!";
    public static final String CLIENT_NOTIFY = "Client Notify";
    public static final String ERROR_STARTING = "Error while starting: %s";
    public static final String SLAVE_INITIAL = "Slave thread initialized.";
    public static final String LISTEN_MASTER = "Listening for command from master.";
    public static final String LISTEN_FILES = "Listening for files...";
    public static final String CONNECTION_MASTER = "Got a connection from MASTER: %s";
    public static final String UNABLE_LISTEN_FILES = "Unable to listen for files coming from client .. restart for fixing !!";

    // File Index
    private static Map<String, Long> fileIndex = null;

    private static Map<String, String> clients = new HashMap<String, String>();

    public static void addClient(String ip, String port) {
        clients.put(ip, port);
    }

    public static Map<String, Long> getFileIndex() {
        return fileIndex;
    }

    public static void setFileIndex(Map<String, Long> fileIndex) {
        fileIndex = fileIndex;
    }

    public static void setFileIndex(String path) throws FileNotFoundException {
        fileIndex = FileUtil.generateFileIndexMap(path);
    }

}
