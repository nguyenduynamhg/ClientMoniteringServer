package util;

public class LogItem {
    private String ipClient;
    private String action;
    private String desc;
    private String createdAt;

    public String getDesc() {
        return desc;
    }

    public String getIpClient() {
        return ipClient;
    }

    public String getAction() {
        return action;
    }

    public String getCreatedAt() {
        return createdAt;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public LogItem(String ipClient, String action, String desc, String createdAt) {
        this.ipClient = ipClient;
        this.action = action;
        this.desc = desc;
        this.createdAt = createdAt;
    }

}