package bci.user;

import bci.notification.Notification;
import bci.notification.notificationtype.*;
import bci.request.Request;
import bci.user.situation.Active;
import bci.user.situation.Suspended;
import bci.user.userprofile.Normal;
import bci.work.Work;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable, Comparable<User> {
    private int _id;
    private String _name;
    private String _email; 
    private Situation _status;
    private UserProfile _userProfile;
    private int _fine;
    private List<Request> _requests = new ArrayList<>();
    private List<Notification> _notifications = new ArrayList<>();
    private HistoryManager _historyManager = new HistoryManager();

    public User(int id, String name, String email) {
        _id = id;
        _name = name;
        _email = email;
        _status = new Active();
        _userProfile = new Normal(this);
        _fine = 0;
    }

    public int getId() {return _id;}

    public String getName() {return _name;}

    public String getEmail() {return _email;}

    public Situation getStatus() {return _status;}

    public UserProfile getUserProfile() {return _userProfile;}

    public int getFine() {return _fine;}

    public List<Request> getRequests() {return _requests;}

    public boolean checkActive(int currDate) { 
        for (Request r : _requests) {
            if (r.expired(currDate) || getFine() != 0) {
                setStatus(new Suspended());
            }
        }
        return _status.isActive();
    }

    public void addRequestToUser(Request r) {
        _requests.add(r);
    }
 
    public void removeRequest(Request r, int currDate) {
        _requests.remove(r);
    }

    public Request getRequestByWork (Work w) {
        for (Request r : _requests) {
            if (r.getWork().equals(w)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public int compareTo(User u) {
        return this._name.compareTo(u._name);
    }

    @Override
    public String toString() {
        if (_status.isActive()) {
            return _id + " - " + _name + " - " + _email + " - " + _userProfile + " - " + _status;
        }
        return _id + " - " + _name + " - " + _email + " - " + _userProfile + " - " + _status + " - " +  " EUR " + _fine;
    }

    public void reactivateUser(){
        clearDebt();
        setStatus(new Active());
    }

    public void setFine(int fine){
        _fine = fine;
    }

    public void addFine(int fine){
        setFine(_fine + fine);
    }    

    public void clearDebt(){
        setFine(0);
    }

    public void setStatus(Situation new_situation){
        _status = new_situation;
    }

    public void setUserProfile(UserProfile new_profile){
        _userProfile = new_profile;
    }

    public void addRequest(Request r){
        _requests.add(r);
    }

    public int deadline(int available) {
        return _userProfile.deadline(available);
    }

    public int maxRequests() {
        return _userProfile.maxRequests();
    }


    public boolean canRequestByPrice(int price){
        return _userProfile.canRequestByPrice(price);
    }

    public void addDisponibilityNotification(String work){
        _notifications.add(new Notification(new DisponibilityNotification(), work));
    }

    public void addRequestNotification(String work){
        _notifications.add(new Notification(new RequestNotification(), work));
    }

    public void clearNotifications(){
        _notifications.clear();
    }


    public List<Notification> getNotifications(){
        return _notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            return this._id == u._id;
        }
        return false;
    }

    public HistoryManager getHistoryManager(){
        return _historyManager;
    }

    public void addAndCheckHistory(boolean onTime){
        _historyManager.addToHistory(onTime);
        _userProfile.checkStateChange();
    }

}