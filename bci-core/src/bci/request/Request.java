package bci.request;

import bci.user.User;
import bci.work.Work;
import java.io.Serializable;

public class Request implements Serializable{
    private User _user;
    private Work _work;
    private int _entrieDay;
    private int _returnDay;



    public Request(User user, Work work, int entrieDay) {
        _user = user;
        _work = work;
        _entrieDay = entrieDay;
        _returnDay = _user.deadline(_work.getQty()) + entrieDay;
    }
    
    public User getUser() { return _user; }
    public Work getWork() { return _work; }
    
    public boolean expired(int currentDay) {
        if (_returnDay < currentDay) {
            return true;
        }
        return false;
    }

    public int getFine(int currentDay) {
        if (currentDay > _returnDay) {
            return (currentDay - _returnDay) * 5; //5 euros por dia de atraso
        }
        return 0;
    }

    public int getReturnDay() {
        return _returnDay;
    }
}
