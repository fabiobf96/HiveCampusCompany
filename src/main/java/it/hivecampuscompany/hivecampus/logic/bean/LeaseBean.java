package it.hivecampuscompany.hivecampus.logic.bean;

import java.io.File;

public class LeaseBean {
    private File file;
    private final AccountBean tenant;
    private final PreviewRoomBean previewRoomBean;
    private final boolean active;
    public LeaseBean(PreviewRoomBean previewRoomBean, AccountBean tenant, boolean active) {
        this.previewRoomBean = previewRoomBean;
        this.tenant = tenant;
        this.active = active;
    }

    public PreviewRoomBean getPreviewRoomBean() {
        return previewRoomBean;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public AccountBean getTenant() {
        return tenant;
    }

    public boolean isActive() {
        return active;
    }
}
