package bci.rules;

import bci.user.User;
import bci.work.Work;

public class Rule5 extends BorrowingRule {

    public Rule5() {
        super(5);
    }

    @Override
    public boolean canBorrow(User user, Work work, int date) {
        return work.getCategory().canBeBorrowed();
    }   
}
