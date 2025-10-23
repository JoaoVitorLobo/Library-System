package bci.rules;

import bci.request.Request;
import bci.user.User;
import bci.work.Work;

public class Rule1 extends BorrowingRule {

    public Rule1() {
        super(1);
    }

    @Override
    public boolean canBorrow(User user, Work work, int date) {
        for (Request req : user.getRequests()) {
            if (req.getWork().equals(work)) {
                return false;
            }
        }
        return true;
    } 
}
