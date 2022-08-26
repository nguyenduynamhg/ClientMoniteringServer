package util;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BindingSouceHelper {

    public static DefaultTableModel mapLogsModel(DefaultTableModel model, ArrayList<LogItem> data) {
        model.setRowCount(data.size());
        int row = 0;
        int counter = 1;
        for(LogItem item: data)
        {
            model.setValueAt(counter, row, 0);
            model.setValueAt(item.getAction(), row, 1);
            model.setValueAt(item.getIpClient(), row, 2);
            model.setValueAt(item.getDesc(), row, 3);
            model.setValueAt(item.getCreatedAt(), row, 4);
            row++;
            counter++;
        }
        return model;
    }
}
