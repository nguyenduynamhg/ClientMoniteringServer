package constants;

import util.LogItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class CoreConstants {

    // app
    public final static double MIN_JAVA_VERSION = 1.7D;
    public final static int MIN_PORT = 1024;

    public final static int DEFAULT_PORT = 3000;
    public final static boolean APPEND_LOG_FILE = true;

    public static final String SEPARATOR = "~";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String EMPTY = "";
    public static final String SLASH = "/";
    public static final String LOG_FILE = "log.txt";
    public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    public static final String LOG_MESSAGE_PATTERN = "%d "+ SEPARATOR +" %s "+ SEPARATOR +" %s "+ SEPARATOR +" %s "+ SEPARATOR +" %s";
    public static final int MAX_LINE_READ = 10;
    public static int noLog = 1;
    public static final String MASTER = "master";
    public static String DEFAULT_WATCH_FOLDER = "/home/namnd/Desktop/java/";
    public static final Object[] LOG_HEADER_COLUMNS = {"No", "Action", "IP Client", "Description", "Created At"};
    public static final Object[] CLIENT_HEADER_COLUMNS = {"No", "IP Client"};
    public static ArrayList<LogItem> logs = new ArrayList<LogItem>();
    public static DefaultListModel clientIps = new DefaultListModel();
    public static DefaultTableModel modelLogs = new DefaultTableModel(){};
    public static DefaultTableModel modelClients = new DefaultTableModel(){};
    public static List<String> foldersInWatch = new ArrayList<String>(){};

    // action
    public static final String ACTION_CREATE_FOLDER_COMMAND = "MKDIR";
    public static final String ACTION_CREATE_FILE_COMMAND = "MKFILE";
    public static final String ACTION_MODIFY_FILE_COMMAND = "MDFILE";
    public static final String ACTION_MODIFY_FOLDER_COMMAND = "MDDIR";
    public static final String ACTION_DELETE_FILE_COMMAND = "DLFILE";
    public static final String ACTION_DELETE_FOLDER_COMMAND = "DLDIR";
    public static final String ACTION_CLIENT_REGISTER = "REGCLIENT";
    public static final String ACTION_CLIENT_DISCONNECT = "DISCLIENT";
    public static final String ACTION_CLIENT_SEND_DIR = "SENDDIRCLIENT";
    public static final String ACTION_SEND_WATCH_DIR = "SETWATCHDIR";
    public static final String ACTION_LIST_DIR = "LDIR";

    // client action
    public static final String ACTION_CLIENT_CREATE_FILE = "Client create file %s on %s";
    public static final String ACTION_CLIENT_MODIFY_FILE = "Client modify file %s on %s";
    public static final String ACTION_CLIENT_DELETE_FILE = "Client delete file %s on %s";
    public static final String ACTION_CLIENT_CREATE_FOLDER = "Client create folder %s on %s";
    public static final String ACTION_CLIENT_MODIFY_FOLDER = "Client modify folder %s on %s";
    public static final String ACTION_CLIENT_DELETE_FOLDER = "Client delete folder %s on %s";

    // name action
    public static final String ACTION_CLIENT_JOIN = "Client Join";
    public static final String ACTION_CLIENT_JOIN_DESC = "Client has Join To Server with ip: %s";
    public static final String ACTION_CLIENT_SEND_WATCH_DIR = "Client send watch directory ";
    public static final String ACTION_CLIENT_SEND_WATCH_DIR_DESC = "Client has send watch directory To Server with ip: %s ";
    public static final String WATCH_DIR = "Client has send watch directory %s To Server with ip: %s and port: %s";
    public static final String ACTION_LOG_CREATE_FILE = "CREATE FILE %s";
    public static final String ACTION_LOG_CREATE_FOLDER = "CREATE FOLDER %s";
    public static final String ACTION_LOG_DELETE_FILE = "DELETE FILE %s";
    public static final String ACTION_LOG_DELETE_FOLDER = "DELETE FOLDER %s";
    public static final String ACTION_LOG_MODIFY_FILE = "MODIFY FILE %s";
    public static final String ACTION_LOG_MODIFY_FOLDER = "MODIFY FOLDER %s";

    public static final String CLIENT_SEND_DIR = "Client send files in directories";
    public static final String ACTION_SEND_DIR = "Client has send files from folder To Server with ip: %s";
    public static final String ACTION_SEND_DIR_DESC = "Client has send files from folder To Server with ip: %s";

    // text

    public static final String IP_LABEL = "Ip";
    public static final String ACTION_LABEL = "Action";
    public static final String TIME_LABEL = "Time";
    public static final String APP_TITLE = "Client Remote Monitor App";
    public static final String PARAMETERS_EXCEPTION = "Run with required parameters. Example: java RemoteFileSync master 8888 c:\\test\\";
    public static final String FIRST_PARAMETERS_EXCEPTION = "First argument should be 'master' or 'slave' ";
    public static final String PORT_EXCEPTION = "Please use port greater than 1024 inorder to avoid protocol conflicts.";
    public static final String INVALID_FOLDER_EXCEPTION = "File system path given does not exists or not a valid directory. Please create a folder:  %s";
    public static final String PARAMETERS_SUCCESS = "Parameter check successful.";
    public static final String SERVER_STARTING = "Command Server Starting at port: %s";
    public static final String COMMAND_FROM = "Command from : %s";
    public static final String NEW_CLIENT = "Got a new client %s: %s" ;

    public static final String UNKNOWN_COMMAND_FROM = "Unknown Command %s";
    public static final String THREAD_UNABLE_START = "Unable to start command thread";
    public static final String JAVA_VER_PROPERTY = "java.version";
    public static final String JAVA_UPGRADE_JRE = "Please upgrade your JRE. Minimum Required version : %s";
    public static final String JAVA_CHECK_VER = "JRE Version[%s] check successful.";
    public static final String START_INDEX_FILE = "Start Indexing %s" ;
    public static final String PATH_INVALID = "Path does not exists or not a valid directory " ;
    public static final String INDEXING_FILE = "......Indexing %s";
    public static final String ACTION_START_SERVER = "Start Server";
    public static final String START_SERVER_TITLE = "Server Login";
    public static final String SERVER_NOTIFY_TITLE = "Server Notify";
    public static final String START_SERVER_SUCCESS = "Start Server Successfully !!";
    public static final String START_SERVER_FAIL = "Start Server Fail !!";
    public static final String START_SERVER_SUCCESS_DESC = "Server start success on ip: %s";
    public static final String START_SERVER_FAIL_DESC = "Server start fail on ip: %s";
    public static final String START_REMOTE = "Starting Remote File Sync.";
    public static final String START_MASTER_MODE = "Starting in MASTER mode. port: %s";
    public static final String ERROR_WRITE_FILE = "Error in write file !!!";

    public static final String CLIENT_DISCONNECT = "client disconnected. Socket closing...";
    public static final String ACTION_CLIENT_OUT = "Client disconnected !!";
    public static final String ACTION_CLIENT_OUT_DESC = "Client has disconnected To Server with ip: %s ";
    public static final String CLIENT_SELECT_NULL = "Undefined client to sync !!";
    public static final String CLIENT_SELECT_NULL_TITLE = "Error client to sync !!";
    public static final String SYNC_SUCCESS = "Syncing successfully !!";
    public static final String SYNC_SUCCESS_TITLE = "Syncing success !!";

    // File Index

    private static Map<String, String> clients = new HashMap<String, String>();

    public static Map<String, String> getClients() {
        return clients;
    }

    public static void addClient(String ip, String port) {
        clients.put(ip, port);
    }
}
