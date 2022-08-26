package util;

import constants.CoreConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    /**
     * Get List of file and last modified date
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static Map<String, Long> generateFileIndexMap(String filePath)
            throws FileNotFoundException {
        System.out.println(String.format(CoreConstants.START_INDEX_FILE, filePath));
        Map<String, Long> list = new HashMap<String, Long>();
        File parentFile = new File(filePath);
        if (parentFile.exists() && parentFile.isDirectory()) {

            generateRecursiveFileList(parentFile, list);
        } else {
            throw new FileNotFoundException(CoreConstants.PATH_INVALID);
        }
        return list;
    }

    // File all files in a folder (recursive) and
    private static void generateRecursiveFileList(File f, Map<String, Long> list) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File subDirOrFile : files) {
                generateRecursiveFileList(subDirOrFile, list);
            }

        } else {
            System.out.println(String.format(CoreConstants.INDEXING_FILE, f.getAbsolutePath()));
            list.put(f.getAbsolutePath(), f.lastModified());
        }
    }

}
