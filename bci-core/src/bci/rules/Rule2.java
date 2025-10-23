package bci.rules;

import bci.user.User;
import bci.work.Work;

public class Rule2 extends BorrowingRule {

    public Rule2() {
        super(2);
    }

    @Override
    public boolean canBorrow(User user, Work work, int date) {
        return user.checkActive(date);
    }   
}
