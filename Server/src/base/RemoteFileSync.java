package base;

import constants.CoreConstants;
import util.RFSyncUtil;

import java.io.File;

public class RemoteFileSync {
    String processType = null;
    public String port = null;
    public String path = null;

    public void checkArgs(String args[]) throws Exception {

        if (args.length < 3) {
            throw new Exception(CoreConstants.PARAMETERS_EXCEPTION);
        }

        if (RFSyncUtil.isMaster(args[0])) {
            processType = args[0];
        } else {
            throw new Exception(CoreConstants.FIRST_PARAMETERS_EXCEPTION);
        }

        port = args[1];
        path = args[2];

        if (Integer.parseInt(port) < CoreConstants.MIN_PORT) {
            throw new Exception(CoreConstants.PORT_EXCEPTION);
        }

        File f = new File(path);
        if (!f.exists() || !f.isDirectory()) {
            throw new Exception(String.format(CoreConstants.INVALID_FOLDER_EXCEPTION, path));
        }

        System.out.println(CoreConstants.PARAMETERS_SUCCESS);
    }
}
