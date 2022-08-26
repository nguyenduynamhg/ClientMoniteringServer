package util;

import constants.CoreConstants;

public class RFSyncUtil {

    public static boolean isMaster(String type) {
        return CoreConstants.MASTER.equals(type);
    }

}
