package models;

import java.util.ArrayList;
import java.util.List;

public class Passenger {
    private String username;
    private String password;
    private int charge;
    private int notifyUser;
    public Passenger() {
        notifyUser = 0;
        charge = 0;
    }

    public int getNotifyUser() {
        return notifyUser;
    }

    public void setNotifyUser(int notifyUser) {
        this.notifyUser = notifyUser;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%20s%20s%20d%20d", username, password, charge, notifyUser);
    }
}
