package thread;

import constants.CoreConstants;

import javax.swing.*;

public class UpdateGuiThread implements Runnable{
    JLabel lb_binding;
    public static void start(JLabel lb_binding) {
        new Thread(new UpdateGuiThread(lb_binding)).start();
    }

    public UpdateGuiThread(JLabel lb_binding) {
        this.lb_binding = lb_binding;
    }

    @Override
    public void run() {
        while(true) {
            lb_binding.setText(CoreConstants.SERVER_LAST_ACTION_LABEL + CoreConstants.SPACE + CoreConstants.actionState);
        }
    }
}
