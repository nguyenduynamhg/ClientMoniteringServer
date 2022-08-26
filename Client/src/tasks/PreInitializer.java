package tasks;

import constants.CoreConstants;

import java.util.Properties;

/**
 * Pre initialize tasks goes here Min Software hardware check etc
 *
 * @author MIBRLK0
 *
 */
public class PreInitializer implements SimpleTask {

    @Override
    public void execute() throws Exception {
        checkJavaVersion();
    }

    /**
     * Check java version
     *
     * @throws Exception
     */
    private void checkJavaVersion() throws Exception {
        Properties properties = System.getProperties();
        String sVersion = properties.getProperty(CoreConstants.JAVA_VER_PROPERTY)
                .substring(0, 3);
        Float f = Float.valueOf(sVersion);
        if (f.floatValue() < (float) CoreConstants.MIN_JAVA_VERSION) {
            throw new Exception(String.format(CoreConstants.JAVA_UPGRADE_JRE, CoreConstants.MIN_JAVA_VERSION));
        }
        System.out.println(String.format(CoreConstants.JAVA_CHECK_VER, sVersion));
    }

}
