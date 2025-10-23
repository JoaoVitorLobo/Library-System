package bci.user.userprofile;

import bci.user.User;
import bci.user.UserProfile;
import java.util.List;

public class Normal extends UserProfile{
    public Normal(User user){
        super(user);
    }

    @Override
    public void up(){
        getUser().setUserProfile(new Cumpridor(getUser()));
    }

    @Override
	public void down(){
        getUser().setUserProfile(new Faltoso(getUser()));
    }

    @Override
    public boolean isNormal(){ return true;}

    @Override
    public boolean isFaltoso(){ return false;}

    @Override
    public boolean isCumpridor(){ return false;}

    @Override
    public String toString() { return "NORMAL"; }

    @Override
    public int maxRequests(){return 3; }

    @Override
    public int deadline(int n_available){
        if (n_available <= 1) return 3;
        if (n_available <= 5) return 8;
        return 15;
    }

    @Override
    public boolean canRequestByPrice(int price){ return price <= 25; }

    public void checkNormalUp(){
        List<Boolean> _history = getUser().getHistoryManager().getHistory();
        if (_history.size() < 5) return;
        for (Boolean b : _history) {
            if (!b) {
                return;
            }
        }
        up();
    }
    
    public void checkNormalDown(){
        List<Boolean> _history = getUser().getHistoryManager().getHistory();
        if (_history.size() < 3) return;
        for (int i = 0; i < 3; i++) {
            Boolean b = _history.get(i);
            if (b) {
                return; 
            }
        }
        down();
    }

    @Override
    public void checkStateChange(){
        checkNormalUp();
        checkNormalDown();
    }
}
