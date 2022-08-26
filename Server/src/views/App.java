package views;

import constants.CoreConstants;
import tasks.Tracker;
import util.FileNode;
import util.TreeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel appPanel;
    private JPanel LogsPanel;
    private JTable tbl_logs;
    private JTree tree_dir;
    private JList l_clients;
    private JButton btn_sync;
    private JLabel lb_watching_dir;
    private JTextField txt_ip_search;
    private JButton btn_search_ip;
    private JTextField txt_log_ip;
    private JTextField txt_log_action;
    private JSpinner sp_log_time;
    private JButton btn_filter;
    private JComboBox cb_type_filter;

    private Tracker tracker = new Tracker();
    private TreeModel treeModel = null;

    private Object clientSelect = null;
    private TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(CoreConstants.modelLogs);

    public void initTableBingdingSource() {
        tbl_logs.setModel(CoreConstants.modelLogs);
    }

    public App(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(tabbedPane1);
        this.pack();
        this.setLocationRelativeTo(null);
        JFrame thisFrame = this;
        JOptionPane dialog = new JOptionPane();

        initTableBingdingSource();

        String watchFolder = CoreConstants.foldersInWatch.size() > 0 ? CoreConstants.foldersInWatch.get(0) : "";

        l_clients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        l_clients.setLayoutOrientation(JList.VERTICAL);
        lb_watching_dir.setText(watchFolder);
        l_clients.setModel(CoreConstants.clientIps);

        tbl_logs.setRowSorter(sorter);

        sp_log_time.setModel(new SpinnerDateModel());
        sp_log_time.setEditor(new JSpinner.DateEditor(sp_log_time, CoreConstants.DATE_TIME_PATTERN));
        sp_log_time.setValue(new Date()); // will show the current datetime

        btn_sync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientSelect == null) {
                    dialog.showMessageDialog(thisFrame, CoreConstants.CLIENT_SELECT_NULL,
                            CoreConstants.CLIENT_SELECT_NULL_TITLE, JOptionPane.ERROR_MESSAGE);
                } else {
                    int index = CoreConstants.clientIps.indexOf(clientSelect);
                    String watchFolder = "";
                    if (index != -1) {
                        watchFolder = CoreConstants.foldersInWatch.get(index);
                        lb_watching_dir.setText(watchFolder);
                        File fileRoot = new File(watchFolder);
                        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileNode(fileRoot));
                        TreeView ccn =
                                new TreeView(fileRoot, root);
                        new Thread(ccn).start();
                        treeModel = new DefaultTreeModel(root);
                        tree_dir.setModel(treeModel);
                        tree_dir.expandRow(0);


                    }
                    dialog.showMessageDialog(thisFrame, CoreConstants.SYNC_SUCCESS,
                            CoreConstants.SYNC_SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        l_clients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 1) {
                    JList target = (JList)e.getSource();
                    int index = target.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Object item = target.getModel().getElementAt(index);
                        clientSelect = item;
                    }
                }
            }
        });

        btn_search_ip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = txt_ip_search.getText();
                if(query.equals("")) {
                    l_clients.setModel(CoreConstants.clientIps);
                }
                else {
                    DefaultListModel cloneModel = new DefaultListModel();
                    Object[] cloneModelArray = CoreConstants.clientIps.toArray();
                    for (Object item : cloneModelArray) {
                        if (item.toString().contains(query)) {
                            cloneModel.addElement(item);
                        }
                    }
                    l_clients.setModel(cloneModel);
                }
            }
        });
        btn_filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RowFilter<DefaultTableModel, Object> rf = null;
                String type = (String) cb_type_filter.getSelectedItem();
                //If current expression doesn't parse, don't update.
                try {
                    switch (type)
                    {
                        case CoreConstants.IP_LABEL:
                            rf = RowFilter.regexFilter(txt_ip_search.getText(),2);
                            break;
                        case CoreConstants.ACTION_LABEL:
                            rf = RowFilter.regexFilter(txt_log_action.getText(),1);
                            break;
                        case CoreConstants.TIME_LABEL:
                            sp_log_time.commitEdit();
                            String dateTimeVal = new SimpleDateFormat(CoreConstants.DATE_TIME_PATTERN)
                                    .format(sp_log_time.getValue());
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
