package bci.rules;

import bci.user.User;
import bci.work.Work;

public class Rule3 extends BorrowingRule {

    public Rule3() {
        super(3);
    }

    @Override
    public boolean canBorrow(User user, Work work, int date) {
        return work.stillAvailable() > 0;
    }  
}
