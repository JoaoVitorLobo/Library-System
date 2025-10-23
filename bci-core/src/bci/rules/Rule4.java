package bci.rules;

import bci.user.User;
import bci.work.Work;

public class Rule4 extends BorrowingRule {

    public Rule4() {
        super(4);
    }

    @Override
    public boolean canBorrow(User user, Work work, int date) {
        return user.getRequests().size() < user.maxRequests();
    }
    
}
