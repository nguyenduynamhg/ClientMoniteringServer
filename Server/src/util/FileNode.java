package util;

import constants.CoreConstants;

import java.io.File;

public class FileNode {

    private File file;

    public FileNode(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        String name = file.getName();
        if (name.equals(CoreConstants.EMPTY)) {
            return file.getAbsolutePath();
        } else {
            return name;
        }
    }
}