package tasks;

//import com.rfsync.core.thread.MasterSocketThread;

import constants.CoreConstants;

public class SlaveCommandListener implements FileTask {

    @Override
    public void execute(String port) throws Exception {
        // MasterSocketThread.startThread(Integer.parseInt(port));
        System.out.println(CoreConstants.SLAVE_INITIAL);
        System.out.println(CoreConstants.LISTEN_MASTER);
    }

}
