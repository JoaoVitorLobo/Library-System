package bci.work;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bci.user.User;

public class WaitListManager implements Serializable{
    private List<User> _disponibilityWaitList = new ArrayList<>(); 
    private List<User> _requestWaitList = new ArrayList<>(); 

    public void addUserToDisponibilityWaitList(User user) {
        if (!_disponibilityWaitList.contains(user)) {
            _disponibilityWaitList.add(user);
        }
    }

    public void addUserToRequestWaitList(User user) {
        if (!_requestWaitList.contains(user)) {
            _requestWaitList.add(user);
        }
    }

    public void removeUserFromDisponibilityWaitList(User user) {
        _disponibilityWaitList.remove(user);
    }

    public void removeUserFromRequestWaitList(User user) {
        _requestWaitList.remove(user);
    }

    public List<User> getDisponibilitWaitList() {
        return _disponibilityWaitList;
    }

    public List<User> getRequestDisponibilitWaitList() {
        return _requestWaitList;
    }

    public void addDisponibilityNotificationForEachUser(String work){
        _disponibilityWaitList.forEach(user -> user.addDisponibilityNotification(work));
    }

    public void addRequestNotificationForEachUser(String work){
        _requestWaitList.forEach(user -> user.addRequestNotification(work));
    }
}
