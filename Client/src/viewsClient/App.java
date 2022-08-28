package viewsClient;

import constants.CoreConstants;
import network.SlaveRegister;
import thread.UpdateGuiThread;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends JFrame{
    private JPanel viewPanel;
    private JLabel lb_action;
    private JLabel lb_ip;
    private JLabel lb_port;
    private JLabel lb_connect_state;
    private JLabel lb_folder_watch;
    private JComboBox cb_type_filter;
    private JSpinner sp_time;
    private JTextField txt_action;
    private JTextField txt_desc;
    private JButton btn_search;
    private JTable tbl_logs;

    private TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(CoreConstants.modelLogs);

    public void initTableBingdingSource() {
        tbl_logs.setModel(CoreConstants.modelLogs);
    }

    public App(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        JFrame thisFrame = this;

        initTableBingdingSource();
        tbl_logs.setRowSorter(sorter);

        sp_time.setModel(new SpinnerDateModel());
        sp_time.setEditor(new JSpinner.DateEditor(sp_time, CoreConstants.DATE_TIME_PATTERN));
        sp_time.setValue(new Date()); // will show the current datetime

        thisFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    SlaveRegister.disconnect();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        lb_ip.setText(CoreConstants.SERVER_IP_LABEL + CoreConstants.SPACE + CoreConstants.ipHost);
        lb_port.setText(CoreConstants.SERVER_PORT_LABEL + CoreConstants.SPACE + CoreConstants.serverPort);

        lb_connect_state.setText(CoreConstants.SERVER_CONNECTION_STATE_LABEL + CoreConstants.SPACE + CoreConstants.MASTER_CONNECT_SUCCESS);
        lb_folder_watch.setText(CoreConstants.SERVER_FOLDER_WATCH_LABEL + CoreConstants.SPACE + CoreConstants.DEFAULT_WATCH_FOLDER);
        UpdateGuiThread.start(lb_action);
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RowFilter<DefaultTableModel, Object> rf = null;
                String type = (String) cb_type_filter.getSelectedItem();
                //If current expression doesn't parse, don't update.
                try {
                    switch (type)
                    {
                        case "Content":
                            rf = RowFilter.regexFilter(txt_desc.getText(),3);
                            break;
                        case "Action":
                            rf = RowFilter.regexFilter(txt_action.getText(),1);
                            break;
                        case "Time":
                            sp_time.commitEdit();
                            String dateTimeVal = new SimpleDateFormat(CoreConstants.DATE_TIME_PATTERN)
                                    .format(sp_time.getValue());
                            rf = RowFilter.regexFilter(dateTimeVal,4);
                            break;
                    }

                } catch (Exception ex) {
                    return;
                }
                sorter.setRowFilter(rf);
            }
        });
    }
}
