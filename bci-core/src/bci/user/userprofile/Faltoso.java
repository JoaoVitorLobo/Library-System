package bci.user.userprofile;

import bci.user.User;
import bci.user.UserProfile;
import java.util.List;

public class Faltoso extends UserProfile{
    public Faltoso(User user){
        super(user);
    }

    @Override
    public void up(){
        getUser().setUserProfile(new Normal(getUser()));
    }

    @Override
	public void down(){}

    @Override
    public boolean isNormal(){ return false;}

    @Override
    public boolean isFaltoso(){ return true;}

    @Override
    public boolean isCumpridor(){ return false;}

    @Override
    public String toString() { return "FALTOSO"; }   
    
    @Override
    public int maxRequests(){return 1; }

    @Override
    public int deadline(int n_available){
        return 2;
    }

    @Override
    public boolean canRequestByPrice(int price){ return price <= 25; }

    public void checkFaltosoUp(){
        List<Boolean> _history = getUser().getHistoryManager().getHistory();
        if (_history.size() < 3) return;
        for (int i = 0; i < 3; i++) {
            Boolean b = _history.get(i);
            if (!b) {
                return; 
            }
        }
        up();
    }

    @Override
    public void checkStateChange(){
        checkFaltosoUp();
    }
}
