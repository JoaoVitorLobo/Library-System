package bci.notification;

import java.io.Serializable;

public class Notification implements Serializable{
    private NotificationType _type;
    private String _work;

    public Notification(NotificationType type, String work) {
        _type = type;
        _work = work;
    }


    @Override
    public String toString() {
        return _type + ": " + _work;
    }
}
