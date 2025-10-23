package bci.rules;

import java.util.ArrayList;
import java.util.List;

import bci.user.User;
import bci.work.Work;

public class OrRules extends BorrowingRule {
    private List<BorrowingRule> _rules = new ArrayList<>();

    public OrRules(List<BorrowingRule> rules) {
        super(-1);
        _rules.addAll(rules);
    }

    @Override
    public boolean canBorrow(User user, Work work, int date) {
        for (BorrowingRule rule : _rules) {
            if (!rule.canBorrow(user, work, date)) {
                setRuleFaild(rule.getRuleId());
                return false;
            }    
        }
        return true;
    }
}
