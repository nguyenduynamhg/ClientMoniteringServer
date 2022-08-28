package views;

import Remote.RemoteFileSync;
import constants.CoreConstants;
import network.FileServerForSlave;
import network.SlaveRegister;
import tasks.PreInitializer;
import tasks.Tracker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class clientLogin extends JFrame {
    private JPanel loginPanel;
    private JTextField txt_ip;
    private JTextField txt_port;
    private JButton btn_connect;

    public clientLogin(String title) {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.setFocusableWindowState(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setSize(500, 300);
        setVisible(false);
        JFrame thisFrame = this;
        JOptionPane dialog = new JOptionPane();

        btn_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String port = txt_port.getText();
                String ip = txt_ip.getText();
                CoreConstants.modelLogs.setColumnIdentifiers(CoreConstants.LOG_HEADER_COLUMNS);
                String[] args = new String[]{CoreConstants.SLAVE, port, CoreConstants.DEFAULT_WATCH_FOLDER, ip};
                Tracker trk = new Tracker();
                CoreConstants.ipHost = ip;
                CoreConstants.serverPort = port;
                RemoteFileSync drive = new RemoteFileSync();
                try {
                    trk.readDataFromFile();
                    // Check Args:
                    System.out.println(CoreConstants.START_REMOTE);
                    // make sure all arguments are good
                    drive.checkArgs(args);
                    // make sure system has proper software installed to start
                    new PreInitializer().execute();
                    // Index Directory
                    CoreConstants.setFileIndex(drive.path);

                    System.out.println(String.format(CoreConstants.START_SLAVE_MODE, port));
                    SlaveRegister.handshake();

                    System.out.println(String.format(CoreConstants.MASTER_HANDSHAKE_SUCCESS,
                            drive.masterURL, drive.port));
                    SlaveRegister.passWathForder();

                    SlaveRegister.passDirToServer();
                    FileServerForSlave.startThread(Integer.parseInt(drive.port), drive.path);
                    System.out.println(CoreConstants.REMOTE_FILE_SUCCESS);
                    dialog.showMessageDialog(thisFrame, CoreConstants.MASTER_CONNECT_SUCCESS,
                            CoreConstants.CLIENT_NOTIFY, JOptionPane.INFORMATION_MESSAGE);
                    CoreConstants.actionState = CoreConstants.MASTER_CONNECT_SUCCESS;
                    App frame = new App(CoreConstants.APP_TITLE);
                    frame.setVisible(true);
                    thisFrame.dispose();
                } catch (Exception ex) {
                    dialog.showMessageDialog(thisFrame, ex.getMessage(), CoreConstants.CLIENT_NOTIFY,
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println(String.format(CoreConstants.ERROR_STARTING, ex.getMessage()));
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new clientLogin(CoreConstants.START_CLIENT_TITLE);
        frame.setVisible(true);
    }
}
