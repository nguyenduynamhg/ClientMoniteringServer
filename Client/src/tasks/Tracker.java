package tasks;

import constants.CoreConstants;
import org.apache.commons.io.input.ReversedLinesFileReader;
import utilitis.BindingSouceHelper;
import utilitis.LogItem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Tracker {

    public void readDataFromFile()  {
        try {
            ReversedLinesFileReader object = new ReversedLinesFileReader(new File(CoreConstants.LOG_FILE),
                    StandardCharsets.UTF_8 );
            List<String> lines = object.readLines(CoreConstants.MAX_LINE_READ);
            lines.forEach(line -> {
                String[] row = line.split(CoreConstants.SEPARATOR);
                if(row.length > 0) {
                    LogItem lData = new LogItem(row[1], row[2], row[3], row[4]);
                    CoreConstants.logs.add(lData);
                    int index = Integer.parseInt(row[0].trim());
                    CoreConstants.noLog = index > CoreConstants.noLog ? index : CoreConstants.noLog ;
                }
            });
            CoreConstants.noLog++;
            object.close();
            BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTimeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CoreConstants.DATE_TIME_PATTERN);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void writeLogFile(String action, String ipClient, String desc) {
        String now = getTimeNow();
        LogItem log = new LogItem(ipClient, action, desc, now);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(CoreConstants.LOG_FILE, true);
            String data = String.format(CoreConstants.LOG_MESSAGE_PATTERN, CoreConstants.noLog, ipClient, action, desc, now);
            fileWriter.append(data);
            fileWriter.append(CoreConstants.NEW_LINE);
            CoreConstants.logs.add(log);
            CoreConstants.noLog++;
            BindingSouceHelper.mapLogsModel(CoreConstants.modelLogs, CoreConstants.logs);
        } catch (IOException e) {
            System.out.println(CoreConstants.ERROR_WRITE_FILE);
            e.printStackTrace();
        } finally {
            if(fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
