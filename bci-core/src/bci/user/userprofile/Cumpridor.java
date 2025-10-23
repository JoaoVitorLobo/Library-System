package bci.user.userprofile;

import bci.user.User;
import bci.user.UserProfile;
import java.util.List;

public class Cumpridor extends UserProfile{
    public Cumpridor(User user){
        super(user);
    }

    @Override
    public void up(){}

    @Override
	public void down(){
        getUser().setUserProfile(new Normal(getUser()));
    }

    @Override
    public boolean isNormal(){ return false;}

    @Override
    public boolean isFaltoso(){ return false;}

    @Override
    public boolean isCumpridor(){ return true;}

    @Override
    public String toString() { return "CUMPRIDOR"; } 
    
    @Override
    public int maxRequests(){return 5; }

    @Override
    public int deadline(int n_available){
        if (n_available <= 1) return 8;
        if (n_available <= 5) return 15;
        return 30;
    }

    @Override
    public boolean canRequestByPrice(int price){ return true; }

    public void checkCumpridorDown(){
        List<Boolean> _history = getUser().getHistoryManager().getHistory();
        if (_history.isEmpty()) return;
        if (_history.get(0)) {
            return;
        }
        down();
    }

    @Override
    public void checkStateChange(){
        checkCumpridorDown();
    }

}
