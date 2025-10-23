package bci.user;

import java.io.Serializable;

public abstract class UserProfile implements Serializable{
    private User _user;

    public UserProfile(User user){
        _user = user;
    }

    public User getUser(){ 
        return _user; 
    }

    public abstract boolean isNormal();

    public abstract boolean isFaltoso();

    public abstract boolean isCumpridor();
    	
    public abstract void up();

	public abstract void down();

    public abstract int maxRequests();

    public abstract int deadline(int n_available);

    public abstract boolean canRequestByPrice(int price);

    public abstract void checkStateChange();

}
