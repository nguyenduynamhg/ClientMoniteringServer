package viewsClient;

import Remote.RemoteFileSync;
import constants.CoreConstants;
import network.CommandServerMaster;
import tasks.PreInitializer;
import tasks.Tracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerLogin extends JFrame {
    private JPanel loginPanel;
    private JLabel lb_ip;
    private JTextField txt_port;
    private JButton btn_start;

    public ServerLogin(String title) {

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.setFocusableWindowState(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setSize(500, 300);
        setVisible(false);
        JFrame thisFrame = this;
        JOptionPane dialog = new JOptionPane();

        CoreConstants.modelLogs.setColumnIdentifiers(CoreConstants.LOG_HEADER_COLUMNS);
        CoreConstants.modelClients.setColumnIdentifiers(CoreConstants.CLIENT_HEADER_COLUMNS);

        String ipHost = "";
        try {
            ipHost = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException u) {
            dialog.showMessageDialog(thisFrame, u.getMessage(),
                    CoreConstants.START_SERVER_FAIL, JOptionPane.ERROR_MESSAGE);
        }
        lb_ip.setText(ipHost);
        txt_port.setText(String.valueOf(CoreConstants.DEFAULT_PORT));


        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String port = txt_port.getText();
                String[] args = new String[]{CoreConstants.MASTER, port, CoreConstants.DEFAULT_WATCH_FOLDER};
                Tracker trk = new Tracker();
                try{
                    RemoteFileSync drive = new RemoteFileSync();
                    trk.readDataFromFile();

                    // Check Args:
                    System.out.println(CoreConstants.START_REMOTE);
                    // make sure all arguments are good
                    drive.checkArgs(args);
                    // make sure system has proper software installed to start
                    new PreInitializer().execute();

                    System.out.println(String.format(CoreConstants.START_MASTER_MODE, port));
                    CommandServerMaster.startThread(Integer.parseInt(port));

                    dialog.showMessageDialog(thisFrame, CoreConstants.START_SERVER_SUCCESS,
                            CoreConstants.SERVER_NOTIFY_TITLE, JOptionPane.INFORMATION_MESSAGE);
                    trk.writeLogFile(CoreConstants.ACTION_START_SERVER, lb_ip.getText(),
                            String.format(CoreConstants.START_SERVER_SUCCESS_DESC, lb_ip.getText()));

                    App frame = new App(CoreConstants.APP_TITLE);
                    frame.setVisible(true);
                    thisFrame.dispose();
                }
                catch (Exception ex) {
                    dialog.showMessageDialog(thisFrame, ex.getMessage(),
                            CoreConstants.SERVER_NOTIFY_TITLE, JOptionPane.ERROR_MESSAGE);
                    trk.writeLogFile(CoreConstants.ACTION_START_SERVER, lb_ip.getText(),
                            String.format(CoreConstants.START_SERVER_FAIL_DESC, lb_ip.getText()));
                    System.out.println(ex.fillInStackTrace());
                }
            }
        });
    }


    public static void main(String[] args) {
        final ImageIcon icon = new ImageIcon("photo-1592853285454-34691b7b74c4.jpeg");
        JTextArea text = new JTextArea()
        {
            Image img = icon.getImage();
            // instance initializer
            {setOpaque(false);}
            public void paintComponent(Graphics graphics)
            {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };
        JScrollPane pane = new JScrollPane(text);
        JFrame frame = new ServerLogin(CoreConstants.START_SERVER_TITLE);
        frame.setVisible(true);

    }

}
