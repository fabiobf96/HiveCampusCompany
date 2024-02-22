package it.hivecampuscompany.hivecampus.logic.model;


import java.io.File;

public class Lease extends Subject{
    int id;
    private final Room room;
    private final Account tenant;
    private File file;
    private final boolean active;
    public Lease(Room room, Account tenant, File file, boolean active) {
        this.room = room;
        this.tenant = tenant;
        this.file = file;
        this.active = active;
    }

    public Lease(int storedLeaseId, Room room, Account account, Boolean storedActive, File storedContract) {
        this.id = storedLeaseId;
        this.room = room;
        this.tenant = account;
        this.active = storedActive;
        this.file = storedContract;
    }

    public Room getRoom() {
        return room;
    }

    public Account getTenant() {
        return tenant;
    }

    public File getFile() {
        return file;
    }
    public void setSignedContract(File file){
        this.file = file;
        notifyObservers();
        for (Observer observer : observers){
            removeObserver(observer);
        }
    }

    public boolean isActive() {
        return active;
    }
}
