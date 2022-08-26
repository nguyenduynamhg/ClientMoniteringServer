package tasks;

import constants.CoreConstants;
import thread.MasterFileWatchThread;

public class FileSystemWatcher implements FileTask {

    @Override
    public void execute(String path) throws Exception {
        MasterFileWatchThread.startThread(path);
        System.out.println(CoreConstants.MASTER_THREAD_INITIAL);
    }

}
