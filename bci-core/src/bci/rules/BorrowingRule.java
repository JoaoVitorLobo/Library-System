package bci.rules;

import java.io.Serializable;

import bci.user.User;
import bci.work.Work;

public abstract class BorrowingRule implements Serializable{

    private int _ruleFaild;
    private int _ruleId;

    public BorrowingRule(int ruleId) {
        _ruleId = ruleId;
    }
    
    public abstract boolean canBorrow(User user, Work work, int date);
    
    public int getRuleId() {
        return _ruleId;
    }

    public void setRuleFaild(int ruleFaild) {
        _ruleFaild = ruleFaild;
    }

    public int getRuleFaild() {
        return _ruleFaild;
    }
}
