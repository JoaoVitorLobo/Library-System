package bci.rules;

import bci.user.User;
import bci.work.Work;

public class Rule6 extends BorrowingRule {

    public Rule6() {
        super(6);
    }

    @Override
    public boolean canBorrow(User user, Work work, int date) {
        return user.canRequestByPrice(work.getPrice());
    }
    
}
