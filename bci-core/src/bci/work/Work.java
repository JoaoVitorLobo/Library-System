package bci.work;

import bci.creator.Creator;
import bci.user.User;
import bci.work.category.*;
import java.io.Serializable;
import java.util.List;

public abstract class Work implements Serializable, Comparable<Work> {
    private int _id;
    private String _title;
    private int _price;
    private int _qty;
    private int _requests;
    private Category _category;
    private WaitListManager _waitListManager = new WaitListManager();
    private boolean _exists = true;


    public Work(int id, String title, String category, String price, String qty) {
        _id = id;
        _title = title;
        _price = Integer.parseInt(price);
        switch(category){
            case "FICTION":
                _category = new Fiction();
                break;
            case "SCITECH":
                _category = new SciTech();
                break;
            case "REFERENCE":
                _category = new Reference();
                break;
            default: //FIXME throw exception
        }
        _qty = Integer.parseInt(qty);
        _requests = 0;
    }

    public int getId() { return _id;}
    public String getTitle() { return _title;}
    public int getPrice() { return _price;}
    public int getQty() { return _qty;}
    public int getNumRequests() { return _requests;}
    public Category getCategory() { return _category;}
    public int stillAvailable() {return _qty - _requests; }
    public WaitListManager getWaitListManager() { return _waitListManager; }

    public abstract List<Creator> getCreators();
    public abstract void setCreator(Creator creator);
    
    public void addInventory(int addQty) { 
        _qty += addQty; 
        if (addQty > 0 && stillAvailable() - addQty == 0) { _waitListManager.addDisponibilityNotificationForEachUser(toString()); }// if it becomes available
    }
    
    public void addRequestToWork() {
        _requests++;
        _waitListManager.addRequestNotificationForEachUser(toString());
    }

    public void removeRequestFromWork() {
            _requests--;
            if (stillAvailable() - 1 == 0) _waitListManager.addDisponibilityNotificationForEachUser(toString()); // if it becomes available
    }

    public void removeUserFromDisponibilityWaitList(User user) {
        _waitListManager.removeUserFromDisponibilityWaitList(user);
    }

    public void addUserToDisponibilityWaitList(User user) {
        _waitListManager.addUserToDisponibilityWaitList(user);
    }
    
    public abstract String creatorToString();

    @Override
    public int compareTo(Work w) {
        return this._title.compareToIgnoreCase(w._title);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Work) {
            Work w = (Work) o;
            return this._id == w._id;
        }
        return false;
    }
}
