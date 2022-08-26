package thread;

import com.sun.nio.file.ExtendedWatchEventModifier;
import constants.CoreConstants;
import network.FileSenderForSlave;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * This thread will keep an eye on file system changes and initiate a sync with
 * all slaves
 *
 * @author MIBRLK0
 *
 */
public class MasterFileWatchThread implements Runnable {

    String path = null;

    private MasterFileWatchThread(String p) {
        this.path = p;
    }

    /**
     * Start a new thread
     */
    public static void startThread(String path) {
        new Thread(new MasterFileWatchThread(path)).start();
    }

    @Override
    public void run() {

        try {

            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = new File(path).toPath();
            WatchEvent.Kind<?>[] standardEventsArray = {StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY};

            dir.register(watcher, standardEventsArray, ExtendedWatchEventModifier.FILE_TREE);
            System.out.println(String.format(CoreConstants.START_POLL, path));

            while (true) {

                // wait for key to be signaled
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException x) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    // The filename is the
                    // context of the event.
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();
                    MasterFileTransmitterThread.start(kind.name(), filename
                            .getFileName().toString(), path);
                }

                // Reset the key -- this step is critical if you want to
                // receive further watch events. If the key is no longer valid,
                // the directory is inaccessible so exit the loop.
                boolean valid = key.reset();
                if (!valid) {
                    System.out.println(CoreConstants.PATH_DELETED);
                    break;
                }
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
