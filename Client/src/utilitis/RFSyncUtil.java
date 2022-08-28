package utilitis;

import constants.CoreConstants;

public class RFSyncUtil {
    public static boolean isSlave(String type) { return CoreConstants.SLAVE.equals(type); }
}
